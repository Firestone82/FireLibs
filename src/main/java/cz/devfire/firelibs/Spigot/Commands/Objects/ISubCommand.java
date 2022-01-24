package cz.devfire.firelibs.Spigot.Commands.Objects;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface ISubCommand {

    String getLabel();

    String getUsage();

    String getPermission();

    String getDescription();

    int getMinArgs();

    int getMaxArgs();

    void perform(ICommand command, CommandSender sender, String[] args);

    List<String> tabComplete(ICommand command, CommandSender sender, String[] args);

}
