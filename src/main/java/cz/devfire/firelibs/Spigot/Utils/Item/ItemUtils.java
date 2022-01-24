package cz.devfire.firelibs.Spigot.Utils.Item;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import cz.devfire.firelibs.Shared.Utils.StringUtils;
import cz.devfire.firelibs.Spigot.Utils.Reflections.Ref;
import cz.devfire.firelibs.Spigot.Utils.ServerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemUtils {
    private ItemUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     *
     * @param player
     * @param items
     * @return
     */
    public static List<ItemStack> addItemsOrDrop(Player player, List<ItemStack> items) {
        if (items == null || items.size() == 0) {
            return Lists.newArrayList();
        }

        List<ItemStack> leftOvers = addItems(player.getInventory(),items);
        World world = player.getWorld();
        Location location = player.getLocation();

        for (ItemStack drop : leftOvers) {
            world.dropItemNaturally(location,drop);
        }

        return leftOvers;
    }

    /**
     *
     * @param player
     * @param items
     * @return
     */
    public static List<ItemStack> addItemsOrDrop(Player player, ItemStack... items) {
        if (items == null || items.length == 0) {
            return Lists.newArrayList();
        }

        List<ItemStack> leftOvers = addItems(player.getInventory(),items);
        World world = player.getWorld();
        Location location = player.getLocation();

        for (ItemStack drop : leftOvers) {
            world.dropItemNaturally(location,drop);
        }

        return leftOvers;
    }

    /**
     *
     * @param inventory
     * @param items
     * @return
     */
    public static List<ItemStack> addItems(Inventory inventory, List<ItemStack> items) {
        List<ItemStack> left = Lists.newArrayList();

        if (items == null || items.size() == 0) {
            return left;
        }

        for (ItemStack item : items) {
            if (inventory.firstEmpty() == -1) {
                left.add(item);
            } else {
                inventory.addItem(item);
            }
        }

        return left;
    }

    /**
     *
     * @param inventory
     * @param items
     * @return
     */
    public static List<ItemStack> addItems(Inventory inventory, ItemStack... items) {
        List<ItemStack> left = Lists.newArrayList();

        if (items == null || items.length == 0) {
            return left;
        }

        for (ItemStack item : items) {
            if (inventory.firstEmpty() == -1) {
                left.add(item);
            } else {
                inventory.addItem(item);
            }
        }

        return left;
    }

    /**
     *
     * @param base64
     * @return
     */
    public static ItemStack getBase64Head(String base64) {
        if (ServerUtils.getServerVersionID() <= 12) {
            ItemStack stack = new ItemStack(Material.valueOf("SKULL_ITEM"),1,(byte) 3);
            ItemMeta stackMeta = stack.getItemMeta();
            SkullMeta skullMeta = (SkullMeta) stackMeta;

            try {
                GameProfile profile = new GameProfile(UUID.randomUUID(),"");
                profile.getProperties().put("textures", new Property("textures",base64));

                Ref.set(skullMeta,"profile",profile);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return stack;
        } else {
            ItemStack stack = new ItemStack(Material.valueOf("PLAYER_HEAD"));
            Bukkit.getUnsafe().modifyItemStack(stack,"{SkullOwner:{Id:\"" + new UUID(base64.hashCode(), base64.hashCode()) + "\",Properties:{textures:[{Value:\"" + base64 + "\"}]}}}");
            return stack;
        }
    }

    /**
     *
     * @param playerName
     * @return
     */
    public static ItemStack getPlayerHead(String playerName) {
        ItemStack stack = null;

        if (ServerUtils.getServerVersionID() <= 12) {
            stack = new ItemStack(Material.valueOf("SKULL_ITEM"),1,(byte) 3);
        } else {
            stack = new ItemStack(Material.valueOf("PLAYER_HEAD"));
        }

        SkullMeta skullMeta = (SkullMeta) stack.getItemMeta();
        skullMeta.setOwner(playerName);
        stack.setItemMeta(skullMeta);

        return stack;
    }

    /**
     *
     * @param section
     * @return
     */
    public static ItemStack getItemFromConfig(ConfigurationSection section) {
        ItemStack itemStack = new ItemStack(Material.AIR);
        itemStack.setType(Material.valueOf(section.getString("Material","STONE")));
        itemStack.setAmount(section.getInt("Amount",1));
        itemStack.setDurability(Short.parseShort(section.getString("Durability","0")));

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(StringUtils.cc(section.getString("CustomName")));
        itemMeta.setLore(section.isList("Lore") ? StringUtils.ccl(section.getStringList("Lore")) : Lists.newArrayList());

        for (String flag : section.getStringList("Flags")) {
            itemMeta.addItemFlags(ItemFlag.valueOf(flag));
        }

        for (String enchant : section.getStringList("Enchants")) {
            String[] enchantArgs = enchant.split(":");

            itemMeta.addEnchant(Enchantment.getByName(enchantArgs[0]),Integer.parseInt(enchantArgs[1]),true);
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     *
     * @param section
     * @param itemStack
     */
    public static void setItemToConfig(ConfigurationSection section, ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        section.set("Material", itemStack.getType().name());
        section.set("Amount", itemStack.getAmount());
        section.set("Durability", itemStack.getDurability());

        if (itemMeta.hasDisplayName()) {
            section.set("CustomName", itemMeta.getDisplayName().replace("§","&"));
        }

        if (itemMeta.hasLore()) {
            section.set("Lore", StringUtils.replaceList(itemMeta.getLore(), "§", "&"));
        }

        if (itemMeta.getItemFlags().size() != 0) {
            ArrayList<String> flags = Lists.newArrayList();

            for (ItemFlag flag : itemMeta.getItemFlags()) {
                flags.add(flag.name());
            }

            section.set("Flags", flags);
        }

        if (itemMeta.getItemFlags().size() != 0) {
            ArrayList<String> enchantments = Lists.newArrayList();

            for (Enchantment enchantment : itemMeta.getEnchants().keySet()) {
                enchantments.add(enchantment.getName() +"-"+ itemMeta.getEnchants().get(enchantment));
            }

            section.set("Enchants", enchantments);
        }
    }
}
