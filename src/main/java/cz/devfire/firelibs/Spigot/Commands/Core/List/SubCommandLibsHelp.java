package cz.devfire.firelibs.Spigot.Commands.Core.List;

import com.google.common.collect.Lists;
import cz.devfire.firelibs.Spigot.Commands.Objects.ICommand;
import cz.devfire.firelibs.Spigot.Commands.Objects.ISubCommand;
import cz.devfire.firelibs.Spigot.FireLibs;
import cz.devfire.firelibs.Shared.Utils.StringUtils;
import org.bukkit.command.CommandSender;

import java.util.List;

public final class SubCommandLibsHelp implements ISubCommand {
    private final FireLibs pluginCore;

    public SubCommandLibsHelp(FireLibs pluginCore) {
        this.pluginCore = pluginCore;
    }

    @Override
    public String getLabel() {
        return "help";
    }

    @Override
    public String getUsage() {
        return "firelibs help";
    }

    @Override
    public String getPermission() {
        return "firelibs.help";
    }

    @Override
    public String getDescription() {
        return "Shows this message";
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
        sender.sendMessage(StringUtils.cc(pluginCore.getConfig().getString("Language.Help")));

        for (ISubCommand subCommand : command.getSubCommands()) {
            if (subCommand.getDescription() == null) continue;

            sender.sendMessage(StringUtils.cc(StringUtils.parseArgs(pluginCore.getConfig().getString("Language.HelpCommand"),subCommand.getUsage(), subCommand.getDescription())));
        }
    }

    @Override
    public List<String> tabComplete(ICommand command, CommandSender sender, String[] args) {
        return Lists.newArrayList();
    }
}
