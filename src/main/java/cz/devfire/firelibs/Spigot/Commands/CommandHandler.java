package cz.devfire.firelibs.Spigot.Commands;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cz.devfire.firelibs.Spigot.FireLibs;
import cz.devfire.firelibs.Spigot.Utils.Reflections.Ref;
import cz.devfire.firelibs.Spigot.Utils.Reflections.RefUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Map;

public class CommandHandler {
    private final FireLibs plugin;

    private final CommandMap commandMap;
    private final Map<String, Command> knownCommands;

    public CommandHandler(FireLibs plugin) {
        this.plugin = plugin;

        this.commandMap = (CommandMap) Ref.get(Bukkit.getPluginManager(),"commandMap");
        this.knownCommands = (Map<String, Command>) Ref.get(commandMap,"knownCommands");
    }

    public void createAndRegisterCommand(Plugin plugin, CommandExecutor commandExecutor, String permission, String commandName, String... aliases) {
        registerCommand(createCommand(plugin,commandExecutor,permission,commandName,aliases));
    }

    public PluginCommand createCommand(Plugin plugin, CommandExecutor commandExecutor, String permission, String commandName, String... aliases) {
        PluginCommand cmd = RefUtils.createCommand(commandName.toLowerCase(),plugin);

        if (permission != null) {
            Ref.set(cmd,"permission",permission.toLowerCase());
        }

        if (aliases != null) {
            List<String> aliasList = Lists.newArrayList();

            for (String alias : aliases) {
                aliasList.add(alias.toLowerCase());
            }

            cmd.setAliases(aliasList);
        } else {
            cmd.setAliases(Lists.newArrayList());
        }

        Ref.set(cmd,"executor",commandExecutor);

        return cmd;
    }

    public void registerCommand(PluginCommand pluginCommand) {
        String label = pluginCommand.getName().toLowerCase().trim();
        String sd = pluginCommand.getPlugin().getName().toLowerCase().trim();

        pluginCommand.setLabel(sd + ":" + label);
        pluginCommand.register(commandMap);

        if (pluginCommand.getTabCompleter() == null) {
            if (pluginCommand.getExecutor() instanceof TabCompleter) {
                pluginCommand.setTabCompleter((TabCompleter) pluginCommand.getExecutor());
            } else {
                pluginCommand.setTabCompleter((arg0, arg1, arg2, arg3) -> null);
            }
        }

        if (pluginCommand.getExecutor() == null) {
            if (pluginCommand.getTabCompleter() instanceof CommandExecutor) {
                pluginCommand.setExecutor((CommandExecutor) pluginCommand.getTabCompleter());
            } else {
                return;
            }
        }

        List<String> commands = Lists.newArrayList(pluginCommand.getAliases());
        if (!commands.contains(label)) {
            commands.add(label.toLowerCase());
        }

        for (String cmd : commands) {
            knownCommands.put(cmd, pluginCommand);
        }
    }

    public void unregisterCommand(PluginCommand command) {
        unregisterCommand(command.getName());
    }

    public void unregisterCommand(String commandName) {
        for (Map.Entry<String, Command> e : Maps.newHashMap(knownCommands).entrySet()) {
            if (e.getValue().getName().equalsIgnoreCase(commandName)) {
                knownCommands.remove(e.getKey());
            }
        }
    }
}
