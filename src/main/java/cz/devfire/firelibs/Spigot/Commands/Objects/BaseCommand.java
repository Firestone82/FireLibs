package cz.devfire.firelibs.Spigot.Commands.Objects;

import com.google.common.collect.Lists;
import cz.devfire.firelibs.Shared.Utils.StringUtils;
import cz.devfire.firelibs.Spigot.FireLibs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseCommand implements ICommand {
    public final Plugin plugin;

    public final String command;
    private final String permission;
    public final List<String> aliases = Lists.newArrayList();
    public final List<ISubCommand> subCommands = Lists.newArrayList();

    public BaseCommand(Plugin plugin, String command, String permission, String... aliases) {
        this.plugin = plugin;
        this.command = command;
        this.permission = permission;
        this.aliases.addAll(Arrays.asList(aliases));
        this.register();
    }

    public void register() {
        FireLibs.getCommandHandler().createAndRegisterCommand(plugin,this,null,command,aliases.toArray(String[]::new));
    }

    public void unregister() {
        FireLibs.getCommandHandler().unregisterCommand(command);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (permission != null && !sender.hasPermission(permission)) {
            sender.sendMessage(StringUtils.cc(getNoPermissionMessage()));
            return true;
        }

        if (args.length == 0 || subCommands.isEmpty()) {
            perform(sender,args);
            return true;
        } else {
            for (ISubCommand subCommand : subCommands) {
                if (subCommand.getLabel().equalsIgnoreCase(args[0])) {
                    if ((subCommand.getPermission().isEmpty() || subCommand.getPermission() != null) && !sender.hasPermission(subCommand.getPermission())) {
                        sender.sendMessage(StringUtils.cc(getNoPermissionMessage()));
                        return true;
                    }

                    if (args.length < subCommand.getMinArgs() || args.length > subCommand.getMaxArgs()) {
                        sender.sendMessage(StringUtils.cc(StringUtils.parseArgs(getUsageCommand(),subCommand.getUsage())));
                        return true;
                    }

                    subCommand.perform(this,sender,args);
                    return true;
                }
            }

            perform(sender,args);
            return true;
        }
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            for (ISubCommand subCommand : subCommands) {
                if (subCommand.getLabel().equalsIgnoreCase(args[0])) {
                    if (subCommand.getPermission() != null && !sender.hasPermission(subCommand.getPermission())) {
                        return new ArrayList<>();
                    }

                    return subCommand.tabComplete(this,sender,args);
                }
            }
        }

        List<String> list = Lists.newArrayList();

        for (ISubCommand subCommand : subCommands) {
            if (subCommand.getPermission() == null || sender.hasPermission(subCommand.getPermission())) {
                if (subCommand.getLabel().startsWith(args[0])) {
                    list.add(subCommand.getLabel());
                }
            }
        }

        return list;
    }

    public String getName() { return command; }

    public List<String> getAliases() { return Lists.newArrayList(); }

    public List<ISubCommand> getSubCommands() { return Lists.newArrayList(); }

}
