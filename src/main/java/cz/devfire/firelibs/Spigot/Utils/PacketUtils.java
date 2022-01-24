package cz.devfire.firelibs.Spigot.Utils;

import com.google.common.collect.Lists;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Enums.PacketGUIType;
import cz.devfire.firelibs.Spigot.Utils.Reflections.Ref;
import cz.devfire.firelibs.Spigot.Utils.Reflections.RefUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.util.List;

public class PacketUtils {
    private PacketUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Constructor<?> openWindow, closeWindow, setSlot, windowItems;
    static {
        if (ServerUtils.getServerVersionID() < 14) {
            openWindow = Ref.constructor(Ref.nms("PacketPlayOutOpenWindow"),int.class, String.class, Ref.nms("IChatBaseComponent"), int.class);
        } else {
            openWindow = Ref.constructor(Ref.nms("network.protocol.game.PacketPlayOutOpenWindow","PacketPlayOutOpenWindow"),int.class, Ref.nms("world.inventory.Containers","Containers"), Ref.nms("network.chat.IChatBaseComponent","IChatBaseComponent"));
        }

        closeWindow = Ref.constructor(Ref.nms("network.protocol.game.PacketPlayOutCloseWindow","PacketPlayOutCloseWindow"),int.class);

        if (ServerUtils.getServerVersionID() < 17) {
            setSlot = Ref.constructor(Ref.nms("PacketPlayOutSetSlot"),int.class, int.class, Ref.nms("ItemStack"));
        } else {
            setSlot = Ref.constructor(Ref.nms("network.protocol.game.PacketPlayOutSetSlot",null),int.class, int.class, int.class, Ref.nms("world.item.ItemStack",null));
        }

        if (ServerUtils.getServerVersionID() < 8) {
            windowItems = Ref.constructor(Ref.nms("PacketPlayOutWindowItems"),int.class, List.class);
        } else if (ServerUtils.getServerVersionID() < 17) {
            windowItems = Ref.constructor(Ref.nms("PacketPlayOutWindowItems"),int.class, Ref.nms("NonNullList"));
        } else {
            windowItems = Ref.constructor(Ref.nms("network.protocol.game.PacketPlayOutWindowItems",null),int.class, int.class, Ref.nms("core.NonNullList",null), Ref.nms("world.item.ItemStack",null));
        }
    }

    public static Object getPacketPlayOutOpenWindow(int containerID, PacketGUIType windowType, String title) {
        if (ServerUtils.getServerVersionID() < 14) {
            return Ref.newInstance(openWindow,containerID, windowType.getString(), RefUtils.getIChatBaseComponent(title), windowType.getSize());
        } else {
            return Ref.newInstance(openWindow, containerID, windowType.getNMS(), RefUtils.getIChatBaseComponent(title));
        }
    }

    public static Object getPacketPlayOutCloseWindow(int containerID) {
        return Ref.newInstance(closeWindow,containerID);
    }

    public static Object getPacketPlayOutSetSlot(int containerID, int slot, ItemStack itemStack) {
        return getPacketPlayOutSetSlot(containerID,slot,RefUtils.getCraftStack(itemStack));
    }
    public static Object getPacketPlayOutSetSlot(int containerID, int slot, Object itemStack) {
        if (ServerUtils.getServerVersionID() < 17) {
            return Ref.newInstance(setSlot,containerID, slot, itemStack);
        } else {
            return Ref.newInstance(setSlot,containerID, 0, slot, itemStack);
        }
    }

    public static Object getPacketPlayOutWindowItems(int containerID, List<Object> items) {
        List<Object> craftItems = Lists.newArrayList();

        for (Object itemStack : items) {
            if (itemStack instanceof ItemStack) {
                craftItems.add(RefUtils.getCraftStack((ItemStack) itemStack));
            } else {
                craftItems.add(itemStack);
            }
        }

        if (ServerUtils.getServerVersionID() < 8) {
            return Ref.newInstance(windowItems,containerID, craftItems);
        } else if (ServerUtils.getServerVersionID() < 17) {
            Object packet = Ref.newInstance(windowItems,containerID, Ref.invokeNulled(Ref.nms("NonNullList"),"a"));
            Ref.set(packet,"b",craftItems);

            return packet;
        } else {
            Object packet = Ref.newInstance(windowItems,containerID, 0, Ref.invokeNulled(Ref.nms("core.NonNullList",null),"a"), RefUtils.getCraftAir());
            Ref.set(packet,"c",craftItems);

            return packet;
        }
    }

    // ----------------------------------------------------------------
    // ----------------------------------------------------------------
    // ----------------------------------------------------------------
    // ----------------------------------------------------------------
    // ----------------------------------------------------------------

    /**
     *
     * @param player
     * @param packet
     */
    public static void sendPacket(Player player, Object packet) {
        RefUtils.sendPacket(packet,player);
    }

}
