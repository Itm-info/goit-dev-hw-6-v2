package itm.dbWorks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


class Database {
    private static final Database database = new Database();
    private Connection conn;
    private Statement stmt;
    private Database() {
/*      String dbUrl = "jdbc:mysql://*:3306/goit?&serverTimezone=Europe/Kyiv"; */
        String dbUrl = "jdbc:h2:~/test";

        try {
            conn = DriverManager.getConnection(dbUrl); // , dbUser, dbPass);
            stmt = conn.createStatement();
        }
        catch ( SQLException e) { e.printStackTrace(); }
    }

    public static Database getInstance() {
        return database;
    }

    public Connection getConnection() {
        return conn;
    }

    public Statement getStatement() {
        return stmt;
    }
}