package cz.devfire.firelibs.Spigot.Placeholders.Objects;

import org.bukkit.entity.Player;

public interface ISubPlaceholder {

    String getLabel();

    int getMinArgs();

    int getMaxArgs();

    String parse(IPlaceholder placeholder, Player player, String[] args);

}
