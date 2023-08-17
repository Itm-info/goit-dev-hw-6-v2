package itm.dbWorks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Database {
    public static final String CONNECTION_URL = "jdbc:h2:~/test";
    private static final Database database = new Database();
    private Connection conn;
    private Database() {
        try { conn = DriverManager.getConnection(CONNECTION_URL); }
        catch ( SQLException e) { e.printStackTrace(); }
    }

    public static Database getInstance() {
        return database;
    }

    public Connection getConnection() {
        return conn;
    }
}