package cz.devfire.firelibs.Spigot;

import cz.devfire.firelibs.Spigot.Commands.CommandHandler;
import cz.devfire.firelibs.Spigot.Commands.Core.LibsCommand;
import cz.devfire.firelibs.Spigot.Packets.Main.PacketHandler;
import cz.devfire.firelibs.Spigot.Packets.Type.GUI.PacketGUIHandler;
import cz.devfire.firelibs.Spigot.Placeholders.Core.LibsPlaceholder;
import cz.devfire.firelibs.Spigot.Utils.Files.Config;
import cz.devfire.firelibs.Shared.Utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class FireLibs extends JavaPlugin {
    private FireLibs plugin;
    private Config config;

    private LibsCommand libsCommand;
    private LibsPlaceholder libsPlaceholder;

    private static PacketHandler packetHandler;
    private static PacketGUIHandler packetGUIHandler;
    private static CommandHandler commandHandler;

    @Override
    public void onEnable() {
        plugin = this;

        Bukkit.getConsoleSender().sendMessage("  ______ _          _      _ _         ");
        Bukkit.getConsoleSender().sendMessage(" |  ____(_)        | |    (_) |        ");
        Bukkit.getConsoleSender().sendMessage(" | |__   _ _ __ ___| |     _| |__  ___ ");
        Bukkit.getConsoleSender().sendMessage(" |  __| | | '__/ _ \\ |    | | '_ \\/ __|");
        Bukkit.getConsoleSender().sendMessage(" | |    | | | |  __/ |____| | |_) \\__ \\");
        Bukkit.getConsoleSender().sendMessage(" |_|    |_|_|  \\___|______|_|_.__/|___/");
        Bukkit.getConsoleSender().sendMessage("§2Loading..§r");

        config = Config.loadConfig(this,"config.yml","config.yml");

        commandHandler = new CommandHandler(this);
        libsCommand = new LibsCommand(this);
        libsPlaceholder = new LibsPlaceholder(this);

        packetHandler = new PacketHandler(this);
        packetHandler.injectPlayers();

        packetGUIHandler = new PacketGUIHandler(this);

        plugin.getLogger().info("Enabled plugin with version "+ plugin.getDescription().getVersion() +" by "+ StringUtils.stripBrackets(plugin.getDescription().getAuthors().toString()));
    }

    @Override
    public void onDisable() {
        if (libsCommand != null) libsCommand.unregister();
        if (libsPlaceholder != null) libsPlaceholder.unregister();

        packetHandler.extractPlayers();
        packetGUIHandler.closeAllGUIs();

        plugin.getLogger().info("Disabled plugin with version "+ plugin.getDescription().getVersion() +" by "+ StringUtils.stripBrackets(plugin.getDescription().getAuthors().toString()));
    }

    // -----------------------------------

    public Long reload() {
        long time = System.currentTimeMillis();

        Bukkit.getConsoleSender().sendMessage("[FireLibs] Re-Loading..");

        this.config = config.reload();

        Bukkit.getConsoleSender().sendMessage("[FireLibs] Plugin reloaded in.. "+ (System.currentTimeMillis() - time) +"ms");

        return System.currentTimeMillis() - time;
    }

    @Override
    public Config getConfig() {
        return config;
    }

    public LibsCommand getCoreCommand() {
        return libsCommand;
    }

    public LibsPlaceholder getCorePlaceholder() {
        return libsPlaceholder;
    }

    // -----------------------------------

    public static CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public static PacketHandler getPacketHandler() {
        return packetHandler;
    }

    public static PacketGUIHandler getPacketGUIHandler() {
        return packetGUIHandler;
    }
}
