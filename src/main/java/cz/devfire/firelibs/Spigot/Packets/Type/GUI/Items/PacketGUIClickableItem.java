package cz.devfire.firelibs.Spigot.Packets.Type.GUI.Items;

import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Enums.PacketGUIClickType;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.PacketGUI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class PacketGUIClickableItem extends PacketGUISolidItem implements PacketGUIItem {
    public PacketGUIClickableItem(ItemStack stack) {
        super(stack);
    }

    @Override
    public String getType() {
        return "Clickable";
    }

    public abstract boolean onClick(Player player, PacketGUI gui, PacketGUIClickType click);

}
