package cz.devfire.firelibs.Spigot.Packets.Main.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PacketPlayOutEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final Object packet;

    private Boolean cancelled = false;

    public PacketPlayOutEvent(Player player, Object packet) {
        super(true);

        this.player = player;
        this.packet = packet;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Object getPacket() {
        return packet;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancel) {
        cancelled = cancel;
    }
}
