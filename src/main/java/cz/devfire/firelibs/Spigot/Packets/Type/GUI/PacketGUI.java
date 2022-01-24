package cz.devfire.firelibs.Spigot.Packets.Type.GUI;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cz.devfire.firelibs.Spigot.FireLibs;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Enums.PacketGUIEventType;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Enums.PacketGUIType;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Events.PacketGUIEvent;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Items.PacketGUIItem;
import cz.devfire.firelibs.Spigot.Utils.PacketUtils;
import cz.devfire.firelibs.Spigot.Utils.Reflections.RefUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class PacketGUI implements Cloneable {
    private final PacketGUIType type;
    private String title;
    private int updateDelay = -1;

    private final Inventory inventory;
    private final HashMap<Player, Integer> containers = Maps.newHashMap();
    private final HashMap<Integer, PacketGUIItem> items = Maps.newHashMap();
    private final HashMap<PacketGUIEventType, PacketGUIEvent> events = Maps.newHashMap();

    public PacketGUI(PacketGUIType type) {
        this(type,"Unnamed Packet GUI");
    }
    public PacketGUI(PacketGUIType type, String title) {
        this.type = type;
        this.title = title;

        int size = type.getSize();
        if (size < 9) {
            size = 9;
        } else if (size > 9 && size < 18) {
            size = 18;
        } else if (size > 18 && size < 27) {
            size = 27;
        } else if (size > 27 && size < 36) {
            size = 36;
        } else if (size > 36 && size < 45) {
            size = 45;
        } else if (size > 45 && size < 54) {
            size = 54;
        } else if (size > 54) {
            size = 54;
        }

        inventory = Bukkit.createInventory(null,size);
    }
    public PacketGUIType getType() {
        return type;
    }

    public PacketGUI setTitle(String title) {
        this.title = title;
        return this;
    }
    public String getTitle() {
        return title;
    }

    public PacketGUI setItem(PacketGUIItem item, Integer slot) {
        inventory.setItem(slot,item.getStack());
        items.put(slot, item);
        return this;
    }
    public PacketGUIItem getItem(Integer slot) {
        return items.get(slot);
    }
    public Collection<PacketGUIItem> getItems() {
        return items.values();
    }

    public PacketGUI setEvent(PacketGUIEvent event) {
        events.put(event.getType(),event);
        return this;
    }
    public PacketGUIEvent getEvent(PacketGUIEventType type) {
        return events.get(type);
    }
    public Collection<PacketGUIEvent> getEvents() {
        return events.values();
    }

    public PacketGUI setUpdateDelay(Integer delay) {
        this.updateDelay = delay;
        return this;
    }
    public Integer getUpdateDelay() {
        return updateDelay;
    }

    public void open(Player player) {
        // Check for old gui - Memory saver
        PacketGUI lastGUI = FireLibs.getPacketGUIHandler().getOpenedGUI(player);
        if (lastGUI != null && lastGUI != this) {
            lastGUI.containers.remove(player);
        }

        // Increase player container ID
        Integer containerID = RefUtils.increasePlayerContainerCounter(player);
        containers.put(player, containerID);

        // Send open packet
        Object packet = PacketUtils.getPacketPlayOutOpenWindow(containerID,type,title);
        RefUtils.sendPacket(packet,player);

        // Save gui to memory
        FireLibs.getPacketGUIHandler().getData().put(player, this);

        // Update inventory
        update(player);
    }

    public void close() {
        for (Player player : Lists.newArrayList(getViewers())) {
            if (player == null || !player.isOnline()) {
                containers.remove(player);
                continue;
            }

            close(player);
        }
    }
    public void close(Player player) {
        // Send close packet
        if (containers.containsKey(player)) {
            Object packet = PacketUtils.getPacketPlayOutCloseWindow(containers.get(player));
            RefUtils.sendPacket(packet,player);
        }

        // Remove gui from memory
        containers.remove(player);
        FireLibs.getPacketGUIHandler().getData().remove(player);
    }

    public void update() {
        for (Player player : Lists.newArrayList(getViewers())) {
            if (player == null || !player.isOnline()) {
                containers.remove(player);
                continue;
            }

            update(player);
        }
    }
    public void update(Player player) {
        // Get items to fill up
        List<Object> itemList = Lists.newArrayList();
        for (Integer x = 0; x < type.getSize(); x++) {
            if (items.containsKey(x)) {
                itemList.add(RefUtils.getCraftStack(items.get(x).getStack()));
            } else {
                itemList.add(RefUtils.getCraftStack(new ItemStack(Material.AIR)));
            }
        }

        // Get player gui ID
        int containerID = containers.getOrDefault(player,-1);
        if (containerID == -1) return;

        // Fill gui with items
        Object packet = PacketUtils.getPacketPlayOutWindowItems(containerID,itemList);
        RefUtils.sendPacket(packet,player);
    }

    // -------------------------------

    public int getContainerID(Player player) {
        return containers.get(player);
    }
    public Collection<Player> getViewers() {
        return containers.keySet();
    }

    @Override
    public PacketGUI clone() {
        try {
            super.clone();

            PacketGUI clone = new PacketGUI(this.type)
                    .setTitle(this.title)
                    .setUpdateDelay(this.updateDelay);

            for (int slot : this.items.keySet()) {
                clone.setItem(this.items.get(slot),slot);
            }

            if (events.containsKey(PacketGUIEventType.CLICK)) {
                clone.setEvent(this.getEvent(PacketGUIEventType.CLICK));
            }

            if (events.containsKey(PacketGUIEventType.CLOSE)) {
                clone.setEvent(this.getEvent(PacketGUIEventType.CLOSE));
            }

            if (events.containsKey(PacketGUIEventType.OPEN)) {
                clone.setEvent(this.getEvent(PacketGUIEventType.OPEN));
            }

            return clone;
        } catch (Exception e) {
            return null;
        }
    }
}
