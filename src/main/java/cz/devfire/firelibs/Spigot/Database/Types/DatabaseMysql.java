package cz.devfire.firelibs.Spigot.Database.Types;

import cz.devfire.firelibs.Spigot.Database.Objects.DatabaseType;
import cz.devfire.firelibs.Spigot.Database.Objects.IDatabase;
import cz.devfire.firelibs.Spigot.Utils.ServerUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseMysql implements IDatabase {
    private final Plugin plugin;

    private final String db;
    private final String host;
    private final String user;
    private final String pass;
    private final int port;

    private final boolean ssl = false;
    private final boolean reconnect = true;
    private final int reconnectTries = 1;

    private Connection conn;

    public DatabaseMysql(Plugin plugin, ConfigurationSection section) {
        this(plugin,
                section.getString("Database.DB"),
                section.getString("Database.Host"),
                section.getString("Database.User"),
                section.getString("Database.Pass"),
                section.getInt("Database.Port",3306));
    }

    public DatabaseMysql(Plugin plugin, String db, String host, String user, String pass) {
        this(plugin,db, host, user, pass, 3306);
    }

    public DatabaseMysql(Plugin plugin, String db, String host, String user, String pass, int port) {
        this.plugin = plugin;
        this.db = db;
        this.host = host;
        this.user = user;
        this.pass = pass;
        this.port = port;
    }

    @Override
    public DatabaseType getType() {
        return DatabaseType.MYSQL;
    }

    public String getHost() {
        return host;
    }

    public boolean connect() {
        try {
            if (ServerUtils.getServerVersionID() >= 17) {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } else {
                Class.forName("com.mysql.jdbc.Driver");
            }

            String args = "";
            args += "&useSSL="+ (ssl ? "true" : "false");
            args += "&autoReconnect="+ (reconnect ? "true" : "false");
            args += "&maxReconnects="+ reconnectTries;
            args += "&useUnicode=true";
            args += "&characterEncoding=utf-8";
            if (args.startsWith("&")) args = args.substring(1);

            conn = DriverManager.getConnection("jdbc:mysql://"+ host +":"+ port +"/"+ db +"?"+ args,user,pass);

            Bukkit.getConsoleSender().sendMessage("§a - Connecting database "+ host +"... §eSuccessful!");
            return true;
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("§c - Cannot connect to "+ host +"! Error: " + e.getMessage());
            return false;
        }
    }

    public boolean disconnect() {
        try {
            conn.close();

            Bukkit.getConsoleSender().sendMessage("§c - Disconnecting database "+ host +"... §eSuccessful!");
            return true;
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("§c - Cannot disconnect from "+ host +"! Error: " + e.getMessage());
            return false;
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
