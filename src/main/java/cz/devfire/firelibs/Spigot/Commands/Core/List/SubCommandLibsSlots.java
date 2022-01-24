package cz.devfire.firelibs.Spigot.Commands.Core.List;

import com.google.common.collect.Lists;
import cz.devfire.firelibs.Spigot.Commands.Objects.ICommand;
import cz.devfire.firelibs.Spigot.Commands.Objects.ISubCommand;
import cz.devfire.firelibs.Spigot.FireLibs;
import cz.devfire.firelibs.Shared.Utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class SubCommandLibsSlots implements ISubCommand {
    private final FireLibs pluginCore;

    public SubCommandLibsSlots(FireLibs pluginCore) {
        this.pluginCore = pluginCore;
    }

    @Override
    public String getLabel() {
        return "slots";
    }

    @Override
    public String getUsage() {
        return "firelibs slots";
    }

    @Override
    public String getPermission() {
        return "firelibs.slots";
    }

    @Override
    public String getDescription() {
        return "Shows slots numbers of gui";
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
        FireLibs.getPacketGUIHandler().slots(player);
        player.sendMessage(StringUtils.cc(StringUtils.parseArgs(pluginCore.getConfig().getString("Language.Slots"))));
    }

    @Override
    public List<String> tabComplete(ICommand command, CommandSender sender, String[] args) {
        return Lists.newArrayList();
    }
}
