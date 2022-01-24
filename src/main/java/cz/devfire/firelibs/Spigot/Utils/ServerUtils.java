package cz.devfire.firelibs.Spigot.Utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ServerUtils {
    private ServerUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     *
     * @return
     */
    public static String getServerVersion() {
        String version = null;

        try {
            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        } catch (Exception e) {
            try {
                version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[1];
            } catch (Exception e2) { /* */ }
        }

        return version;
    }

    /**
     *
     * @return
     */
    public static Integer getServerVersionID() {
        return Integer.parseInt(getServerVersion().split("_")[1]);
    }

    /**
     *
     * @return
     */
    public static String getServerName() {
        String name = Bukkit.getServer().getName();

        if (name == null || name.equalsIgnoreCase("null") || name.isEmpty()) {
            name = Bukkit.getServer().getMotd();

            if (name == null || name.equalsIgnoreCase("null") || name.isEmpty()) {
                return "NULL";
            } else {
                return name;
            }
        }

        return name;
    }

    /**
     *
     * @param plugin
     * @param player
     * @param server
     */
    public static void sendPlayerToServer(Plugin plugin, Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);

        player.sendPluginMessage(plugin,"BungeeCord",out.toByteArray());
    }

}
