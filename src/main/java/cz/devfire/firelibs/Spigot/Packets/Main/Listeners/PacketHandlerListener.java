package cz.devfire.firelibs.Spigot.Packets.Main.Listeners;

import cz.devfire.firelibs.Spigot.FireLibs;
import cz.devfire.firelibs.Spigot.Packets.Main.PacketHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PacketHandlerListener implements Listener {
    private final FireLibs plugin;
    private final PacketHandler packetHandler;

    public PacketHandlerListener(FireLibs plugin, PacketHandler packetHandler) {
        this.plugin = plugin;
        this.packetHandler = packetHandler;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        packetHandler.injectPlayer(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLeave(PlayerQuitEvent event) {
        packetHandler.extractPlayer(event.getPlayer());
    }
}
