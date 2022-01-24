package cz.devfire.firelibs.Spigot.Database.Objects;

import java.sql.Connection;
import java.sql.ResultSet;

public interface IDatabase {

    boolean connect();

    boolean disconnect();

    boolean isConnected();

    Connection getConnection();

    void update(String query);

    ResultSet query(String query);

    DatabaseType getType();

}
