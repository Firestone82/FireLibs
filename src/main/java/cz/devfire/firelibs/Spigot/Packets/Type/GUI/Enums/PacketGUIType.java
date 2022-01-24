package cz.devfire.firelibs.Spigot.Packets.Type.GUI.Enums;

import cz.devfire.firelibs.Spigot.Utils.Reflections.Ref;
import cz.devfire.firelibs.Spigot.Utils.ServerUtils;

public enum PacketGUIType {
    CHEST9X1("a","GENERIC_9X1","CHEST",9),
    CHEST9X2("b","GENERIC_9X2","CHEST",18),
    CHEST9X3("c","GENERIC_9X3","CHEST",27),
    CHEST9X4("d","GENERIC_9X4","CHEST",36),
    CHEST9X5("e","GENERIC_9X5","CHEST",45),
    CHEST9X6("f","GENERIC_9X6","CHEST",54),
    DISPENSER("g","GENERIC_3X3","CHEST",9),
    DROPPER("g","GENERIC_3X3","CHEST",9),
    ANVIL("h","ANVIL","ANVIL",2),
    BEACON("i","BEACON","BEACON",1),
    BLAST_FURNACE("j","BLAST_FURNCE",null,3),
    BREWING_STAND("k","BREWING_STAND","BREWING_STAND",5),
    CRAFTING("l","CRAFTING","CRAFTING_TABLE",10),
    ENCHANT_TABLE("m","ENCHANTMENT","ENCHANTING_TABLE",1),
    FURNACE("n","FURNACE","FURNACE",3),
    GRINDSTONE("o","GRINDSTONE",null,2),
    HOPPER("p","HOPPER","HOPPER",5),
    LECTERN("q","LECTERN",null,1),
    LOOM("r","LOOM",null,3),
    MERCHANT("s","MERCHANT","VILLAGER",3),
    SHULKER_BOX("t","SHULKER_BOX","CHEST",27),
    SMITHING("u","SMITHING",null,2),
    SMOKER("v","SMOKER",null,3),
    CARTOGRAPHY("w","CARTOGRAPHY_TABLE",null,3),
    STONE_CUTTER("x","STONECUTTER",null,3);

    private final String legacy;
    private final String value;
    private final String old;
    private final Integer size;

    PacketGUIType() {
        this.legacy = null;
        this.value = this.name();
        this.old = this.name();
        this.size = 9;
    }

    PacketGUIType(String legacy) {
        this.legacy = legacy;
        this.value = this.name();
        this.old = this.name();
        this.size = 9;
    }

    PacketGUIType(String legacy, String value, String old, Integer size) {
        this.legacy = legacy;
        this.value = value == null ? this.name() : value;
        this.old = old == null ? this.name() : old;
        this.size = size;
    }

    public Object getNMS() {
        return Ref.getNulled(Ref.nms("world.inventory.Containers","Containers"),ServerUtils.getServerVersionID() > 16 ? legacy : value);
    }

    public String getString() {
        return "minecraft:"+ old.toLowerCase();
    }

    public Integer getSize() {
        return size;
    }
}
