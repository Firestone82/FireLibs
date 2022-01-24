package cz.devfire.firelibs.Spigot.Utils;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.List;

public class LocationUtils {
    private LocationUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     *
     * @param loc
     * @param pos1
     * @param pos2
     * @return
     */
    public static boolean isInArea(Location loc, Vector pos1, Vector pos2) {
        return isInArea(loc,pos1.toLocation(loc.getWorld()),pos2.toLocation(loc.getWorld()));
    }

    /**
     *
     * @param loc
     * @param pos1
     * @param pos2
     * @return
     */
    public static boolean isInArea(Location loc, Location pos1, Location pos2) {
        if (pos1.getWorld().getName().equals(pos2.getWorld().getName()) && loc.getWorld().getName().equals(pos1.getWorld().getName()) && ((
                loc.getBlockX() >= pos1.getBlockX() && loc.getBlockX() <= pos2.getBlockX()) || (loc.getBlockX() <= pos1.getBlockX() && loc.getBlockX() >= pos2.getBlockX())) && ((
                loc.getBlockZ() >= pos1.getBlockZ() && loc.getBlockZ() <= pos2.getBlockZ()) || (loc.getBlockZ() <= pos1.getBlockZ() && loc.getBlockZ() >= pos2.getBlockZ())))
            return ((loc.getBlockY() >= pos1.getBlockY() && loc.getBlockY() <= pos2.getBlockY()) || (loc.getBlockY() <= pos1.getBlockY() && loc.getBlockY() >= pos2.getBlockY()));
        return false;
    }

    /**
     *
     * @param string
     * @return
     */
    public static Location getLocationFromString(String string) {
        String[] stringArgs = string.split("[/:,|]");

        World world = Bukkit.getWorld(stringArgs[0]);
        Double x = Double.parseDouble(stringArgs[1]);
        Double y = Double.parseDouble(stringArgs[2]);
        Double z = Double.parseDouble(stringArgs[3]);
        Float yaw = 0F;
        Float pitch = 0F;

        if (stringArgs.length > 4) {
            yaw = Float.parseFloat(stringArgs[4]);
        }

        if (stringArgs.length > 5) {
            pitch = Float.parseFloat(stringArgs[5]);
        }

        return new Location(world,x,y,z,yaw,pitch);
    }

    /**
     *
     * @param world
     * @param string
     * @return
     */
    public static Location getLocationFromString(World world, String string) {
        String[] stringArgs = string.split("[/:,|]");

        Double x = Double.parseDouble(stringArgs[0]);
        Double y = Double.parseDouble(stringArgs[1]);
        Double z = Double.parseDouble(stringArgs[2]);
        Float yaw = 0F;
        Float pitch = 0F;

        if (stringArgs.length > 3) {
            yaw = Float.parseFloat(stringArgs[3]);
        }

        if (stringArgs.length > 4) {
            pitch = Float.parseFloat(stringArgs[4]);
        }

        return new Location(world,x,y,z,yaw,pitch);
    }

    /**
     *
     * @param location
     * @return
     */
    public static String getStringFromLocation(Location location) {
        return getStringFromLocation(location,false);
    }

    /**
     *
     * @param location
     * @return
     */
    public static String getStringFromLocation(Location location, boolean full) {
        return getStringFromLocation(location,"/",full);
    }

    /**
     *
     * @param location
     * @param splitter
     * @return
     */
    public static String getStringFromLocation(Location location, String splitter, boolean full) {
        String string = "";
        string += location.getWorld().getName() + splitter;
        string += (full ? ((double) Math.round(location.getX() * 100) / 100) : location.getX()) + splitter;
        string += (full ? ((double) Math.round(location.getY() * 100) / 100) : location.getY()) + splitter;
        string += (full ? ((double) Math.round(location.getZ() * 100) / 100) : location.getZ()) + splitter;
        string += (full ? ((double) Math.round(location.getYaw() * 100) / 100) : location.getYaw()) + splitter;
        string += full ? ((double) Math.round(location.getPitch() * 100) / 100) : location.getPitch();

        return string;
    }

    /**
     *
     * @param location
     * @param radius
     * @return
     */
    public static Collection<Player> getNearbyPlayers(Location location, Integer radius) {
        List<Player> nearby = Lists.newArrayList();

        for (Player player : location.getWorld().getPlayers()) {
            if (player.isDead()) continue;

            if (Math.abs(player.getLocation().getX() - location.getX()) < radius &&
                    Math.abs(player.getLocation().getY() - location.getY()) < radius &&
                    Math.abs(player.getLocation().getZ() - location.getZ()) < radius) {

                nearby.add(player);
            }
        }

        return nearby;
    }

    /**
     *
     * @param location
     * @param radius
     * @return
     */
    public static Collection<Entity> getNearbyEntities(Location location, Integer radius) {
        List<Entity> nearby = Lists.newArrayList();

        Integer chunkRadius = (radius < 16) ? 1 : ((radius - radius % 16) / 16);

        for (Integer chunkX = -chunkRadius; chunkX <= chunkRadius; chunkX++) {
            for (Integer chunkZ = -chunkRadius; chunkZ <= chunkRadius; chunkZ++) {
                Integer x = location.getBlockX();
                Integer y = location.getBlockY();
                Integer z = location.getBlockZ();

                for (Entity e : getChunkEntities(new Location(location.getWorld(),(x + chunkX * 16),y,(z + chunkZ * 16)))) {
                    if (e.getLocation().distance(location) <= radius) {
                        nearby.add(e);
                    }
                }
            }
        }

        return nearby;
    }

    /**
     *
     * @param location
     * @param radius
     * @return
     */
    public static Collection<BlockState> getNearbyTileEntities(Location location, Integer radius) {
        List<BlockState> nearby = Lists.newArrayList();

        Integer chunkRadius = (radius < 16) ? 1 : ((radius - radius % 16) / 16);

        for (Integer chunkX = -chunkRadius; chunkX <= chunkRadius; chunkX++) {
            for (Integer chunkZ = -chunkRadius; chunkZ <= chunkRadius; chunkZ++) {
                Integer x = location.getBlockX();
                Integer y = location.getBlockY();
                Integer z = location.getBlockZ();

                for (BlockState b : getChunkTileEntities(new Location(location.getWorld(),(x + chunkX * 16),y,(z + chunkZ * 16)))) {
                    if (b.getLocation().distance(location) <= radius) {
                        nearby.add(b);
                    }
                }
            }
        }

        return nearby;
    }

    /**
     *
     * @param location
     * @return
     */
    public static Collection<Entity> getChunkEntities(Location location) {
        return Lists.newArrayList(location.getChunk().getEntities());
    }

    /**
     *
     * @param location
     * @return
     */
    public static Collection<BlockState> getChunkTileEntities(Location location) {
        return Lists.newArrayList(location.getChunk().getTileEntities());
    }

    /**
     *
     * @param center
     * @param radius
     * @param angleRad
     * @return
     */
    public static Location getLocationAroundCircle(Location center, float radius, double angleRad) {
        double x = center.getX() + radius * Math.cos(angleRad);
        double z = center.getZ() + radius * Math.sin(angleRad);
        double y = center.getY();

        Location loc = new Location(center.getWorld(),x,y,z);

        Vector difference = center.toVector().clone().subtract(loc.toVector());
        Vector lookDir = new Vector(difference.getZ(),0.0,-difference.getX());
        loc.setDirection(lookDir);

        Vector dir = new Vector(center.getX() - loc.getX(),0,center.getZ() - loc.getZ());
        loc.setYaw((float) Math.toDegrees(Math.atan2(dir.getX(), dir.getZ())));

        return loc;
    }
}
