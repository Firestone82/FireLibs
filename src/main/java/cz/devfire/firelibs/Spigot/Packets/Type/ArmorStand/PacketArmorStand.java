package cz.devfire.firelibs.Packets.Type.ArmorStand;

import com.google.common.collect.Lists;
import cz.devfire.firelibs.Spigot.Utils.Vectors.Vector3f;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class PacketArmorStand {
    private Location location;
    private String customName;
    private boolean customNameVisible;
    private boolean armsVisible = false;
    private boolean small = false;
    private boolean basePlate = true;
    private boolean invisible = false;
    private boolean marker = false;
    private boolean glow = false;
    private Vector3f headPose = Vector3f.ZERO;
    private Vector3f bodyPose = Vector3f.ZERO;
    private Vector3f leftArmPose = Vector3f.ZERO;
    private Vector3f rightArmPose = Vector3f.ZERO;
    private Vector3f leftLegPose = Vector3f.ZERO;
    private Vector3f rightLegPose = Vector3f.ZERO;
    private ItemStack hand = new ItemStack(Material.AIR);
    private ItemStack offHand = new ItemStack(Material.AIR);
    private ItemStack helmet = new ItemStack(Material.AIR);
    private ItemStack chestplate = new ItemStack(Material.AIR);
    private ItemStack leggings = new ItemStack(Material.AIR);
    private ItemStack boots = new ItemStack(Material.AIR);

    private Object stand;
    private Integer standID;

    private final ArrayList<String> update = Lists.newArrayList();
    private final ArrayList<Player> players = Lists.newArrayList();

    public PacketArmorStand(Location location) {
        this.location = location;
    }

    public PacketArmorStand setLocation(Location location) {
        this.location = location;
//        if (stand != null) move();

        return this;
    }
    public Location getLocation() {
        return location;
    }

    public PacketArmorStand setCustomName(String name) {
        this.customName = name;
//        Ref.invoke(stand, PacketArmorStandHandler.setCustomNameVisible,customNameVisible);
        return this;
    }

}
