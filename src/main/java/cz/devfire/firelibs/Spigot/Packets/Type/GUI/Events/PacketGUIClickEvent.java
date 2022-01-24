package cz.devfire.firelibs.Spigot.Packets.Type.GUI.Events;

import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Enums.PacketGUIClickType;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Enums.PacketGUIEventType;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Items.PacketGUIItem;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.PacketGUI;
import org.bukkit.entity.Player;

public abstract class PacketGUIClickEvent implements PacketGUIEvent {

    public PacketGUIEventType getType() {
        return PacketGUIEventType.CLICK;
    }

    public abstract boolean onClick(Player player, PacketGUI gui, PacketGUIItem item, Integer slot, PacketGUIClickType click);

}
