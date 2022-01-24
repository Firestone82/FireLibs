package cz.devfire.firelibs.Spigot.Packets.Type.GUI.Events;

import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Enums.PacketGUIEventType;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.PacketGUI;
import org.bukkit.entity.Player;

public abstract class PacketGUIOpenEvent implements PacketGUIEvent {

    public PacketGUIEventType getType() {
        return PacketGUIEventType.OPEN;
    }

    public abstract boolean onOpen(Player player, PacketGUI gui);
    
}
