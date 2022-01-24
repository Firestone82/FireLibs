package cz.devfire.firelibs.Spigot.Packets.Type.GUI.Items;

import org.bukkit.inventory.ItemStack;

public interface PacketGUIItem {

    ItemStack getStack();

    String getType();

}
