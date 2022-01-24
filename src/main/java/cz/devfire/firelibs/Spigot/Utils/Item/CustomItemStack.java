package cz.devfire.firelibs.Spigot.Utils.Item;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cz.devfire.firelibs.Shared.Utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class CustomItemStack implements Cloneable {
    private Material material;
    private String materialName;
    private int amount = 1;
    private short data = 0;
    private short durability = 0;
    private String customName = null;
    private List<String> lore = null;
    private String owner = null;
    private String base64 = null;
    private Map<Enchantment, Integer> enchantmnets = null;
    private List<ItemFlag> flags = null;

    public CustomItemStack(Material material) {
        this.material = material;
        this.materialName = material.name();
    }

    public CustomItemStack setType(Material material) {
        this.material = material;
        this.materialName = material.name();

        return this;
    }

    public CustomItemStack setAmount(int amount) {
        this.amount = amount;

        return this;
    }

    public CustomItemStack setData(short data) {
        this.data = data;

        return this;
    }

    public CustomItemStack setDurability(short durability) {
        this.durability = durability;

        return this;
    }

    public CustomItemStack setCustomName(String customName) {
        this.customName = customName;

        return this;
    }

    public CustomItemStack setLore(List<String> lore) {
        this.lore = lore;

        return this;
    }

    public CustomItemStack setOwner(String owner) {
        this.owner = owner;
        this.base64 = null;
        this.material = XMaterial.PLAYER_HEAD.parseMaterial();
        this.materialName = material.name();
        this.data = 3;

        return this;
    }

    public CustomItemStack setBase64(String base64) {
        this.base64 = base64;
        this.owner = null;
        this.material = XMaterial.PLAYER_HEAD.parseMaterial();
        this.materialName = material.name();
        this.data = 3;

        return this;
    }

    public CustomItemStack addEnchantment(Enchantment enchantment, int value) {
        if (enchantmnets == null) enchantmnets = Maps.newHashMap();
        enchantmnets.put(enchantment, value);

        return this;
    }

    public CustomItemStack addItemFlag(ItemFlag itemFlag) {
        if (flags == null) flags = Lists.newArrayList();
        flags.add(itemFlag);

        return this;
    }

    public ItemStack get(String... args) {
        ItemStack itemStack = null;

        if (base64 != null || owner != null) {
            if (owner != null) {
                itemStack = ItemUtils.getPlayerHead(owner);
            }

            if (base64 != null) {
                itemStack = ItemUtils.getBase64Head(base64);
            }
        } else {
            itemStack = new ItemStack(material,amount,data);
        }

        itemStack.setAmount(amount);
        itemStack.setDurability(durability);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(customName == null ? materialName : StringUtils.parseArgs(StringUtils.cc(customName),args));
        itemMeta.setLore(lore == null ? Lists.newArrayList() : StringUtils.parseArgs(StringUtils.ccl(lore),args));
        if (enchantmnets != null) enchantmnets.forEach((enchantment, integer) -> itemMeta.addEnchant(enchantment,integer,true));
        if (flags != null) flags.forEach(itemMeta::addItemFlags);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public CustomItemStack clone() {
        try {
            super.clone();

            CustomItemStack clone = new CustomItemStack(this.material);
            clone.setAmount(this.amount);
            clone.setData(this.data);
            clone.setDurability(this.durability);
            clone.setCustomName(this.customName);
            clone.setLore(this.lore);
            if (this.owner != null) clone.setOwner(this.owner);
            if (this.base64 != null) clone.setBase64(this.base64);

            for (Enchantment enchantment : this.enchantmnets.keySet()) {
                clone.addEnchantment(enchantment,this.enchantmnets.get(enchantment));
            }

            for (ItemFlag itemFlag : this.flags) {
                clone.addItemFlag(itemFlag);
            }

            return clone;
        } catch (CloneNotSupportedException var2) {
            throw new Error(var2);
        }
    }
}
