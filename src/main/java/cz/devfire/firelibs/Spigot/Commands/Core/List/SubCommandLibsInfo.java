package cz.devfire.firelibs.Spigot.Commands.Core.List;

import com.google.common.collect.Lists;
import cz.devfire.firelibs.Spigot.Commands.Objects.ICommand;
import cz.devfire.firelibs.Spigot.Commands.Objects.ISubCommand;
import cz.devfire.firelibs.Spigot.FireLibs;
import cz.devfire.firelibs.Shared.Utils.StringUtils;
import org.bukkit.command.CommandSender;

import java.util.List;

public final class SubCommandLibsInfo implements ISubCommand {
    private final FireLibs pluginCore;

    public SubCommandLibsInfo(FireLibs pluginCore) {
        this.pluginCore = pluginCore;
    }

    @Override
    public String getLabel() {
        return "info";
    }

    @Override
    public String getUsage() {
        return "firelibs info";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
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
    public void perform(ICommand command, CommandSender sender, String[] args) {
        sender.sendMessage(StringUtils.cc("&c&lServer &8&l» &7Libs plugin made by &eFirestone82 &7version &6"+ pluginCore.getDescription().getVersion()));
    }

    @Override
    public List<String> tabComplete(ICommand command, CommandSender sender, String[] args) {
        return Lists.newArrayList();
    }
}
