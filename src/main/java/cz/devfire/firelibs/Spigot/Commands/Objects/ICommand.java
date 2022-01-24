package cz.devfire.firelibs.Spigot.Commands.Objects;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public interface ICommand extends CommandExecutor, TabCompleter {

    String getName();

    void register();

    void unregister();

    void perform(CommandSender sender, String[] args);

    boolean onCommand(CommandSender sender, Command command, String label, String[] args);

    List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args);

    List<String> getAliases();

    List<ISubCommand> getSubCommands();

    String getNoPermissionMessage();

    String getNoCommandMessage();

    String getUsageCommand();

}
