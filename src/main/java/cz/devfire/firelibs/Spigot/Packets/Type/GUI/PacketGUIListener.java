package cz.devfire.firelibs.Spigot.Packets.Type.GUI;

import cz.devfire.firelibs.Spigot.Packets.Main.Events.PacketPlayInEvent;
import cz.devfire.firelibs.Spigot.Packets.Main.Events.PacketPlayOutEvent;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Enums.PacketGUIClickType;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Enums.PacketGUIEventType;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Events.PacketGUIClickEvent;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Events.PacketGUICloseEvent;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Events.PacketGUIOpenEvent;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Items.PacketGUIClickableItem;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.Items.PacketGUIItem;
import cz.devfire.firelibs.Spigot.Utils.PacketUtils;
import cz.devfire.firelibs.Spigot.Utils.Reflections.Ref;
import cz.devfire.firelibs.Spigot.Utils.Reflections.RefUtils;
import cz.devfire.firelibs.Spigot.Utils.ServerUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class PacketGUIListener implements Listener {
    private final PacketGUIHandler packetGuiHandler;

    public PacketGUIListener(PacketGUIHandler packetGuiHandler) {
        this.packetGuiHandler = packetGuiHandler;
    }

    @EventHandler
    public void playIn(PacketPlayInEvent event) {
        Object packet = event.getPacket();
        Player player = event.getPlayer();

        if (packet.getClass().getName().endsWith("PacketPlayInWindowClick")) {
            PacketGUI gui = packetGuiHandler.getOpenedGUI(player);

            if (gui != null) {
                event.setCancelled(true);

                int id = -1;
                int mode = -1;
                int button = -1;
                int slot = -1;
                Object item = null;

                if (ServerUtils.getServerVersionID() >= 17) {
                    id = (int) Ref.get(packet,"b");
                    mode = (int) Ref.invoke(Ref.get(packet,"f"),"ordinal");
                    button = (int) Ref.get(packet,"e");
                    slot = (int) Ref.get(packet,"d");
                    item = Ref.get(packet,"g");
                } else if (ServerUtils.getServerVersionID() >= 12) {
                    id = (int) Ref.get(packet,"a");
                    mode = (int) Ref.invoke(Ref.get(packet,"shift"),"ordinal");
                    button = (int) Ref.get(packet,"button");
                    slot = (int) Ref.get(packet,"slot");
                    item = Ref.get(packet,"item");
                } else {
                    id = (int) Ref.get(packet,"a");
                    mode = (int) Ref.get(packet,"shift");
                    button = (int) Ref.get(packet,"button");
                    slot = (int) Ref.get(packet,"slot");
                    item = Ref.get(packet,"item");
                }

                PacketGUIClickType clickType = PacketGUIClickType.parse(mode,button,slot);
                PacketGUIItem packetItem = gui.getItem(slot);

                if (item != null) {
                    Object p = PacketUtils.getPacketPlayOutSetSlot(-1,-1,new ItemStack(Material.AIR));
                    RefUtils.sendPacket(p, player);

                    if (slot > gui.getType().getSize()) {
                        player.updateInventory();

                        if (clickType == PacketGUIClickType.SHIFT_LEFT_CLICK || clickType == PacketGUIClickType.SHIFT_RIGHT_CLICK) {
                            gui.update(player);
                        }
                    } else {
                        if (packetItem != null) {
                            Object p2 = PacketUtils.getPacketPlayOutSetSlot(id,slot,packetItem.getStack());
                            RefUtils.sendPacket(p2,player);
                        }

                        if (clickType == PacketGUIClickType.DOUBLE_CLICK) {
                            gui.update(player);
                        }

                        if (clickType == PacketGUIClickType.SHIFT_LEFT_CLICK || clickType == PacketGUIClickType.SHIFT_RIGHT_CLICK) {
                            player.updateInventory();
                        }
                    }
                }

                if (packetItem instanceof PacketGUIClickableItem) {
                    if (((PacketGUIClickableItem) packetItem).onClick(player,gui,clickType)) {
                        gui.close(player);
                        return;
                    }
                }

                if (gui.getEvent(PacketGUIEventType.CLICK) != null) {
                    if (((PacketGUIClickEvent) gui.getEvent(PacketGUIEventType.CLICK)).onClick(player,gui,packetItem,slot,clickType)) {
                        gui.close(player);
                        return;
                    }
                }
            }
        }

        if (packet.getClass().getName().endsWith("PacketPlayInCloseWindow")) {
            PacketGUI gui = packetGuiHandler.getOpenedGUI(player);

            if (gui != null) {
                if (gui.getEvent(PacketGUIEventType.CLOSE) != null) {
                    ((PacketGUICloseEvent) gui.getEvent(PacketGUIEventType.CLOSE)).onClose(player,gui);
                }

                gui.close(player);
            }
        }
    }

    @EventHandler
    public void playOut(PacketPlayOutEvent event) {
        Object packet = event.getPacket();
        Player player = event.getPlayer();

        if (packet.getClass().getName().endsWith("PacketPlayOutCollect") && packetGuiHandler.hasOpenedGUI(player)) {
            player.updateInventory();
        }

        if (packet.getClass().getName().endsWith("PacketPlayOutOpenWindow")) {
            PacketGUI gui = packetGuiHandler.getOpenedGUI(player);

            if (gui != null) {
                if (gui.getEvent(PacketGUIEventType.OPEN) != null) {
                    ((PacketGUIOpenEvent) gui.getEvent(PacketGUIEventType.OPEN)).onOpen(player, gui);
                }
            }
        }
    }
}
