package cz.devfire.firelibs.Spigot.Placeholders.Core;

import cz.devfire.firelibs.Spigot.FireLibs;
import cz.devfire.firelibs.Spigot.Placeholders.Core.List.SubPlaceholderLibsVersion;
import cz.devfire.firelibs.Spigot.Placeholders.Objects.BasePlaceholder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class LibsPlaceholder extends BasePlaceholder {

    public LibsPlaceholder(FireLibs pluginCore) {
        super(pluginCore,"firelibs");

        subPlaceholders.add(new SubPlaceholderLibsVersion(pluginCore));
    }

    @Override
    public String parse(Plugin plugin, Player player, String[] args) {
        return getUnknownPlaceholder();
    }

}
