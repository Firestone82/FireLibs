package cz.devfire.firelibs.Spigot.Commands.Core.List;

import com.google.common.collect.Lists;
import cz.devfire.firelibs.Spigot.Commands.Objects.ICommand;
import cz.devfire.firelibs.Spigot.Commands.Objects.ISubCommand;
import cz.devfire.firelibs.Spigot.FireLibs;
import cz.devfire.firelibs.Shared.Utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class SubCommandLibsDebug implements ISubCommand {
    private final FireLibs pluginCore;

    public SubCommandLibsDebug(FireLibs pluginCore) {
        this.pluginCore = pluginCore;
    }

    @Override
    public String getLabel() {
        return "debug";
    }

    @Override
    public String getUsage() {
        return "firelibs debug";
    }

    @Override
    public String getPermission() {
        return "firelibs.debug";
    }

    @Override
    public String getDescription() {
        return "Debugs plugin";
    }

    @Override
    public int getMinArgs() {
        return 1;
    }

    @Override
    public int getMaxArgs() {
        return 3;
    }

    @Override
    public void perform(ICommand command, CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(StringUtils.cc(pluginCore.getConfig().getString("Language.PlayerOnly")));
            return;
        }

        Player player = (Player) sender;

        if (args.length == 1) {
            player.sendMessage(StringUtils.cc(pluginCore.getConfig().getString("Language.Debug")));
            return;
        }

        switch (args[1].toLowerCase()) {
            case "gui": {
                FireLibs.getPacketGUIHandler().debug(player);
                player.sendMessage(StringUtils.cc(StringUtils.parseArgs(pluginCore.getConfig().getString("Language.DebugStarted"),"gui")));
                break;
            }
        }
    }

    @Override
    public List<String> tabComplete(ICommand command, CommandSender sender, String[] args) {
        ArrayList<String> arguemnts = Lists.newArrayList();

        if (args.length > 2) {
            switch (args[2].toLowerCase()) {
                case "gui": {
                    break;
                }
            }
        } else if (args.length > 1) {
            arguemnts.add("gui");
        }

        return arguemnts;
    }
}
