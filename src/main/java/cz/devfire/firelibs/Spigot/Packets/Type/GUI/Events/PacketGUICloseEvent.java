package cz.devfire.firelibs.Spigot.Packets.Type.GUI.Events;

import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Enums.PacketGUIEventType;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.PacketGUI;
import org.bukkit.entity.Player;

public abstract class PacketGUICloseEvent implements PacketGUIEvent {

    public PacketGUIEventType getType() {
        return PacketGUIEventType.CLOSE;
    }

    public abstract boolean onClose(Player player, PacketGUI gui);
    
}
