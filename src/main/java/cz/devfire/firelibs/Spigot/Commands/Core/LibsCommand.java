package cz.devfire.firelibs.Spigot.Commands.Core;

import cz.devfire.firelibs.Shared.Utils.StringUtils;
import cz.devfire.firelibs.Spigot.Commands.Core.List.*;
import cz.devfire.firelibs.Spigot.Commands.Objects.BaseCommand;
import cz.devfire.firelibs.Spigot.Commands.Objects.ICommand;
import cz.devfire.firelibs.Spigot.FireLibs;
import org.bukkit.command.CommandSender;

public final class LibsCommand extends BaseCommand implements ICommand {

    public LibsCommand(FireLibs pluginCore) {
        super(pluginCore,"firelibs",null,"flibs", "fl");

        subCommands.add(new SubCommandLibsDebug(pluginCore));
        subCommands.add(new SubCommandLibsHelp(pluginCore));
        subCommands.add(new SubCommandLibsInfo(pluginCore));
        subCommands.add(new SubCommandLibsReload(pluginCore));
        subCommands.add(new SubCommandLibsSlots(pluginCore));
    }

    @Override
    public String getNoPermissionMessage() {
        return plugin.getConfig().getString("Language.NoPermissions");
    }

    @Override
    public String getNoCommandMessage() {
        return plugin.getConfig().getString("Language.UnknownCommand");
    }

    @Override
    public String getUsageCommand() {
        return plugin.getConfig().getString("Language.Usage");
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        sender.sendMessage(StringUtils.cc(getNoCommandMessage()));
    }

}
