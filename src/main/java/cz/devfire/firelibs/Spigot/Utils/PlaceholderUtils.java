package cz.devfire.firelibs.Spigot.Utils;

import com.google.common.collect.Lists;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class PlaceholderUtils {
    private PlaceholderUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     *
     * @param string
     * @param player
     * @return
     */
    public static String parsePlaceholders(String string, Player player) {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(player,string);
        } else {
            return string;
        }
    }

    /**
     *
     * @param stringList
     * @param player
     * @return
     */
    public static List<String> parsePlaceholders(List<String> stringList, Player player) {
        List<String> newList = Lists.newArrayList();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            for (String line : stringList) {
                newList.add(PlaceholderAPI.setPlaceholders(player,line));
            }

            return newList;
        } else {
            return stringList;
        }
    }
}
