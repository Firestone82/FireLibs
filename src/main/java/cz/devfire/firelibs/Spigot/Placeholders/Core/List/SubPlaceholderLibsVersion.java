package cz.devfire.firelibs.Spigot.Placeholders.Core.List;

import cz.devfire.firelibs.Spigot.FireLibs;
import cz.devfire.firelibs.Spigot.Placeholders.Objects.IPlaceholder;
import cz.devfire.firelibs.Spigot.Placeholders.Objects.ISubPlaceholder;
import org.bukkit.entity.Player;

public final class SubPlaceholderLibsVersion implements ISubPlaceholder {
    private final FireLibs pluginCore;

    public SubPlaceholderLibsVersion(FireLibs pluginCore) {
        this.pluginCore = pluginCore;
    }

    @Override
    public String getLabel() {
        return "version";
    }

    @Override
    public int getMinArgs() {
        return 1;
    }

    @Override
    public int getMaxArgs() {
        return 1;
    }

    @Override
    public String parse(IPlaceholder placeholder, Player player, String[] args) {
        return pluginCore.getDescription().getVersion();
    }

}
