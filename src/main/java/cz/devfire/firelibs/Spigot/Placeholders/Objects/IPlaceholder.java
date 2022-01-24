package cz.devfire.firelibs.Spigot.Placeholders.Objects;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public interface IPlaceholder {

    String getIdentifier();

    String getAuthor();

    String getVersion();

    boolean register();

    boolean unregister();

    String parse(Plugin plugin, Player player, String[] args);

    String onPlaceholderRequest(Player player, String request);

    List<ISubPlaceholder> getSubPlaceholders();

    String getUnknownPlaceholder();

}
