package cz.devfire.firelibs.Spigot.Database.Types;

import cz.devfire.firelibs.Spigot.Database.Objects.DatabaseType;
import cz.devfire.firelibs.Spigot.Database.Objects.IDatabase;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseSqlite implements IDatabase {
    private final Plugin plugin;

    private final File file;
    private final String name;

    private Connection conn;

    public DatabaseSqlite(Plugin plugin, File file) {
        this.plugin = plugin;
        this.file = file;
        this.name = file.getName();
    }

    @Override
    public DatabaseType getType() {
        return DatabaseType.SQLITE;
    }

    public String getHost() {
        return file.getName();
    }

    public boolean connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+ file.getPath());

            Bukkit.getConsoleSender().sendMessage("§a - Connecting sql database "+ name +"... §eSuccessful!");
            return true;
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("§c - Cannot connect to "+ name +"! Error: " + e.getMessage());
            return false;
        }
    }

    public boolean disconnect() {
        try {
            conn.close();

            Bukkit.getConsoleSender().sendMessage("§c - Disconnecting sql database "+ name +"... §eSuccessful!");
            return true;
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("§c - Cannot disconnect from "+ name +"! Error: " + e.getMessage());
            return false;
        }
    }

    public void createIfNotExists() {
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        if (conn != null) {
            try {
                return !conn.isClosed();
            } catch (Exception e) { /* */ }
        }

        return false;
    }

    public Connection getConnection() {
        return conn;
    }

    public void save() {

    }

    public void update(String query) {
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.executeUpdate(query);
        } catch (Exception e) {
            plugin.getLogger().info(" ");
            Bukkit.getConsoleSender().sendMessage("§c - Update failed! §4" + query);
            Bukkit.getConsoleSender().sendMessage("§c - Error: " + e.getMessage());
        }
    }

    public void updatePrepared(String query, String... args) {
        try {
            PreparedStatement ps = conn.prepareStatement(query);

            int i = 0;
            for (String arg : args) {
                ps.setString(i++,arg);
            }

            ps.executeUpdate(query);
        } catch (Exception e) {
            plugin.getLogger().info(" ");
            Bukkit.getConsoleSender().sendMessage("§c - Update failed! §4" + query);
            Bukkit.getConsoleSender().sendMessage("§c - Error: " + e.getMessage());
        }
    }

    public ResultSet query(String query) {
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            return ps.executeQuery();
        } catch (Exception e) {
            plugin.getLogger().info(" ");
            Bukkit.getConsoleSender().sendMessage("§c - Query failed! §4" + query);
            Bukkit.getConsoleSender().sendMessage("§c - Error: " + e.getMessage());
        }

        return null;
    }

    public ResultSet queryPrepared(String query, String... args) {
        try {
            PreparedStatement ps = conn.prepareStatement(query);

            int i = 0;
            for (String arg : args) {
                ps.setString(i++,arg);
            }

            return ps.executeQuery(query);
        } catch (Exception e) {
            plugin.getLogger().info(" ");
            Bukkit.getConsoleSender().sendMessage("§c - Query failed! §4" + query);
            Bukkit.getConsoleSender().sendMessage("§c - Error: " + e.getMessage());
        }

        return null;
    }
}
