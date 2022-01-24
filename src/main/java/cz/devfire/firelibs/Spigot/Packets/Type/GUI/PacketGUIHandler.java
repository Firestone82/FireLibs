package cz.devfire.firelibs.Spigot.Packets.Type.GUI;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cz.devfire.firelibs.Spigot.FireLibs;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Enums.PacketGUIClickType;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Enums.PacketGUIType;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Events.PacketGUIClickEvent;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Events.PacketGUICloseEvent;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Events.PacketGUIOpenEvent;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Items.PacketGUIClickableItem;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Items.PacketGUIItem;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Items.PacketGUISolidItem;
import cz.devfire.firelibs.Spigot.Utils.ServerUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class PacketGUIHandler {
    private FireLibs plugin;

    protected final HashMap<Player, PacketGUI> data = Maps.newHashMap();

    public PacketGUIHandler(FireLibs plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents( new PacketGUIListener(this),plugin);
    }

    public HashMap<Player, PacketGUI> getData() {
        return data;
    }

    public boolean hasOpenedGUI(Player player) {
        return data.containsKey(player);
    }

    public PacketGUI getOpenedGUI(Player player) {
        return data.getOrDefault(player,null);
    }

    public void openGUI(PacketGUI gui, Player player) {
        gui.open(player);
    }

    public void closeGUI(Player player) {
        if (hasOpenedGUI(player)) {
            getOpenedGUI(player).close(player);
        }
    }

    public void closeAllGUIs() {
        for (PacketGUI gui : getGUIs()) {
            for(Player player : Lists.newArrayList(gui.getViewers())) {
                gui.close(player);
            }
        }
    }

    public Collection<PacketGUI> getGUIs() {
        ArrayList<PacketGUI> guiList = Lists.newArrayList();

        for (PacketGUI gui : data.values()) {
            if (guiList.contains(gui)) continue;

            guiList.add(gui);
        }

        return guiList;
    }

    // -----------------------------------------------------

    private static PacketGUI slotsGUI;
    static {
        slotsGUI = new PacketGUI(PacketGUIType.CHEST9X6,"Inventory Slot Numbers");
        ItemStack itemStack = null;

        if (ServerUtils.getServerVersionID() > 12) {
            itemStack = new ItemStack(Material.valueOf("BLACK_STAINED_GLASS_PANE"));
        } else {
            itemStack = new ItemStack(Material.valueOf("STAINED_GLASS_PANE"),0,(short) 15);
        }

        for (Integer i = 1; i < 54; i++) {
            itemStack.setAmount(i);
            slotsGUI.setItem(new PacketGUISolidItem(itemStack.clone()), i);
        }
    }

    private static PacketGUI debugGUI1;
    private static PacketGUI debugGUI2;
    static {
        debugGUI1 = new PacketGUI(PacketGUIType.CHEST9X1)
                .setItem(new PacketGUIClickableItem(new ItemStack(Material.DIAMOND_BLOCK)) {
                    @Override
                    public boolean onClick(Player player, PacketGUI GUI, PacketGUIClickType click) {
                        player.sendMessage("ClickableItem - Open second gui");
                        debugGUI2.open(player);
                        return false;
                    }
                },2)
                .setItem(new PacketGUISolidItem(new ItemStack(Material.BARRIER)),6)
                .setEvent(new PacketGUIClickEvent() {
                    @Override
                    public boolean onClick(Player player, PacketGUI gui, PacketGUIItem item, Integer slot, PacketGUIClickType click) {
                        if (item != null) {
                            player.sendMessage("Event - onClick - ItemType: "+ item.getType() +" - Slot "+ slot);
                        } else {
                            player.sendMessage("Event - onClick - Slot "+ slot);
                        }

                        if (item != null && item.getStack().getType() == Material.BARRIER) {
                            player.sendMessage("Event - onClick - Close");
                            return true;
                        }

                        return false;
                    }
                })
                .setEvent(new PacketGUIOpenEvent() {
                    @Override
                    public boolean onOpen(Player player, PacketGUI gui) {
                        player.sendMessage("Event - onOpen");
                        return true;
                    }
                });

        debugGUI2 = new PacketGUI(PacketGUIType.CHEST9X1)
                .setItem(new PacketGUIClickableItem(new ItemStack(Material.GOLD_BLOCK)) {
                    @Override
                    public boolean onClick(Player player, PacketGUI GUI, PacketGUIClickType click) {
                        player.sendMessage("ClickableItem - Open first gui");
                        debugGUI1.open(player);
                        return false;
                    }
                },4)
                .setItem(new PacketGUIClickableItem(new ItemStack(Material.BARRIER)) {
                    @Override
                    public boolean onClick(Player player, PacketGUI GUI, PacketGUIClickType click) {
                        player.sendMessage("ClickableItem - onClick - Close");
                        return true;
                    }
                },6)
                .setEvent(new PacketGUICloseEvent() {
                    @Override
                    public boolean onClose(Player player, PacketGUI gui) {
                        player.sendMessage("Event - onClose");
                        return true;
                    }
                });
    }

    public void slots(Player player) {
        slotsGUI.open(player);
    }

    public void debug(Player player) {
        debugGUI1.open(player);
    }
}
