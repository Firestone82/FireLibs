package cz.devfire.firelibs.Spigot.Placeholders.Objects;

import com.google.common.collect.Lists;
import cz.devfire.firelibs.Shared.Utils.StringUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public abstract class BasePlaceholder extends PlaceholderExpansion implements IPlaceholder {
    public final Plugin plugin;

    private final String placeholder;
    public final List<ISubPlaceholder> subPlaceholders = Lists.newArrayList();

    public BasePlaceholder(Plugin plugin, String placeholder) {
        this.plugin = plugin;
        this.placeholder = placeholder;

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            this.register();
        }
    }

    @Override
    public String getIdentifier() {
        return placeholder;
    }

    @Override
    public String getAuthor() {
        return StringUtils.stripBrackets(plugin.getDescription().getAuthors().toString());
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public final boolean register() {
        return super.register();
    }

    public String onPlaceholderRequest(Player player, String request) {
        String[] args = request.split("_");

        if (args.length > 0) {
            for (ISubPlaceholder subPlaceholder : subPlaceholders) {
                if (subPlaceholder.getLabel().equalsIgnoreCase(args[0])) {
                    if (args.length < subPlaceholder.getMinArgs() || args.length > subPlaceholder.getMaxArgs()) {
                        return getUnknownPlaceholder();
                    }

                    return subPlaceholder.parse(this,player,args);
                }
            }
        }

        if (args.length == 0) {
            return this.parse(plugin,player,args);
        } else {
            return getUnknownPlaceholder();
        }
    }

    public List<ISubPlaceholder> getSubPlaceholders() {
        return subPlaceholders;
    }

    public String getUnknownPlaceholder() {
        return getIdentifier() +"-UnknownPlaceholder";
    }

}
