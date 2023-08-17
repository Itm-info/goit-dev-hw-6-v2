package itm.dbWorks;

import itm.entities.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.lang.reflect.Field;

public class ClientService {
    private static Connection conn = Database.getInstance().getConnection();
    private static Pattern alphaNumUnder = Pattern.compile("^\\w+$");

    private static List<? extends Object> resultSetToObjectList(ResultSet rs, Class cls) throws InstantiationException, IllegalAccessException, SQLException {
        Field[] fields = cls.getDeclaredFields();
        List<Object> res = new ArrayList<>();
        while(rs.next()) {
            Object obj = cls.newInstance();
            for (Field field : fields) {
                String name = field.getName();
                Object value = rs.getObject(name, field.getType());
                field.set(obj, value);
            }
            res.add(obj);
        }
        return res;
    }

    public static long create(String name) {
        if ( name.length() < 2 || name.length() > 1000 || ! alphaNumUnder.matcher(name).matches() )
            throw new IllegalArgumentException();

        final String query = "INSERT INTO client (name) VALUES (?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getLong("id");
            else           return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getById(long id) {
        if ( id < 1 ) throw new IllegalArgumentException();

        final String query = "SELECT name FROM client where id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("name");
            else           return "";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setName(long id, String name) {
        if ( id < 1 || name.length() < 2 || name.length() > 1000 || ! alphaNumUnder.matcher(name).matches() )
            throw new IllegalArgumentException();

        final String query = "UPDATE client SET name = ? WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setLong(2, id);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteById(long id) {
        if ( id < 1 )
            throw new IllegalArgumentException();

        final String query = "DELETE FROM client WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, id);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) System.out.println(rs.getLong("id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Client> listAll() {
        final String query = "SELECT * FROM client";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            return (List<Client>) resultSetToObjectList(rs, Client.class);
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
