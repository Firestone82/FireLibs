package cz.devfire.firelibs.Spigot.Utils.Reflections;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class RefUtils {
    private RefUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static final Field playerConnection;
    static final Field networkManager;
    static final Field channel;
    static final Field containerCounter;

    static final Constructor<?> blockPosition;
    static final Constructor<?> vector3f;
    static final Constructor<?> pluginCommand;

    static Method iChatBaseComponent;
    static final Method nextContainerCounter;
    static final Method asNMSCopy;
    static final Method sendPacket;

    static {
        playerConnection = Ref.field(Ref.nms("server.level.EntityPlayer","EntityPlayer"),versionID() > 16 ? "b" : "playerConnection");
        networkManager =  Ref.field(Ref.nms("server.network.PlayerConnection","PlayerConnection"),versionID() > 16 ? "a" : "networkManager");
        channel = Ref.field(Ref.nms("network.NetworkManager","NetworkManager"),versionID() > 12 ? "k" : "channel");
        containerCounter = Ref.field(Ref.nms("server.level.EntityPlayer","EntityPlayer"), versionID() > 16 ? "cY" : "containerCounter");

        blockPosition = Ref.constructor(Ref.nms("core.BlockPosition","BlockPosition"),double.class, double.class, double.class);
        vector3f = Ref.constructor(Ref.nms("core.Vector3f","Vector3f"),float.class, float.class, float.class);
        pluginCommand =  Ref.constructor(PluginCommand.class,String.class, Plugin.class);

        iChatBaseComponent = Ref.method(Ref.nms("network.chat.IChatBaseComponent$ChatSerializer","IChatBaseComponent$ChatSerializer"),"a",String.class);
        if (iChatBaseComponent == null) {
            iChatBaseComponent = Ref.method(Ref.nms("ChatSerializer"),"a",String.class);
        }
        nextContainerCounter = Ref.method(Ref.nms("server.level.EntityPlayer","EntityPlayer"),"nextContainerCounter");
        asNMSCopy = Ref.method(Ref.craft("inventory.CraftItemStack"),"asNMSCopy",ItemStack.class);
        sendPacket = Ref.method(Ref.nms("server.network.PlayerConnection","PlayerConnection"),versionID() > 16 ? "a" : "sendPacket",Ref.nms("network.protocol.Packet","Packet"));
    }

    public static void sendPacket(Object packet, Player player) {
        Ref.invoke(getPlayerConnection(player),sendPacket,packet);
    }

    public static void sendPacket(Object packet, Player... players) {
        sendPacket(packet,Lists.newArrayList(players));
    }

    public static void sendPacket(Object packet, List<Player> players) {
        for (Player player : players) {
            Ref.invoke(getPlayerConnection(player),sendPacket,packet);
        }
    }

    public static Object getCraftStack(ItemStack itemStack) {
        Object craftStack = Ref.get(itemStack,"handle");

        if (craftStack == null) {
            Ref.set(itemStack,"handle",craftStack = Ref.invokeNulled(asNMSCopy,itemStack));
            return craftStack;
        } else {
            return craftStack;
        }
    }

    public static Object getCraftAir() {
        return getCraftStack(new ItemStack(Material.AIR));
    }

    public static Object getCraftServer() {
        return Ref.invoke(Ref.cast(Ref.craft("CraftServer"),Bukkit.getServer()),"getServer");
    }

    public static Object getCraftPlayer(Player player) {
        return Ref.handle(Ref.cast(Ref.craft("entity.CraftPlayer"),player));
    }

    public static Object getCraftWorld(World world) {
        return Ref.handle(Ref.cast(Ref.craft("CraftWorld"),world));
    }

    public static Object getPlayerConnection(Player player) {
        return Ref.get(getCraftPlayer(player),playerConnection);
    }

    public static Object getBlockPosition(double x, double y, double z) {
        return Ref.newInstance(blockPosition,x, y, z);
    }

    public static Object getVector3f(float x, float y, float z) {
        return Ref.newInstance(vector3f,x, y, z);
    }

    public static PluginCommand createCommand(String name, Plugin plugin) {
        return (PluginCommand) Ref.newInstance(pluginCommand,name, plugin);
    }

    public static int increasePlayerContainerCounter(Player player) {
        Object craftPlayer = getCraftPlayer(player);

        try {
            return (int) Ref.invoke(craftPlayer,nextContainerCounter);
        } catch (Exception ex) {
            int count = (int) Ref.get(craftPlayer,containerCounter);

            Ref.set(craftPlayer,containerCounter,(int) (count = count % 100 + 1));
            return count;
        }
    }

    public static Object getNetworkManager(Object playerConnection) {
        return Ref.get(playerConnection,networkManager);
    }

    public static Object getChannel(Object networkManager) {
        return Ref.get(networkManager,channel);
    }

    public static Object getIChatBaseComponent(String text) {
        return Ref.invokeNulled(iChatBaseComponent,"{\"text\":\"" + text + "\"}");
    }

    public static Object getIChatBaseComponentJSON(String text) {
        return Ref.invokeNulled(iChatBaseComponent,text);
    }

    // ---

    private static String version() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    private static Integer versionID() {
        return Integer.parseInt(version().split("_")[1]);
    }

}
