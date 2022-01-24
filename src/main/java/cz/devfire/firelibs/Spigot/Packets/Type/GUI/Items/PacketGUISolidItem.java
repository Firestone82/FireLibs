package cz.devfire.firelibs.Spigot.Packets.Type.GUI.Items;

import org.bukkit.inventory.ItemStack;

public class PacketGUISolidItem implements PacketGUIItem {
    private final ItemStack stack;

    public PacketGUISolidItem(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public ItemStack getStack() {
        return stack;
    }

    @Override
    public String getType() {
        return "Solid";
    }
}
