package itm.dbWorks;

import itm.entities.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ClientService {
    private static final Connection conn = Database.getInstance().getConnection();
    private static final Pattern alphaNumUnder = Pattern.compile("^\\w+$");

    private PreparedStatement cs; // createStatement
    private PreparedStatement gs; // getByIdStatement
    private PreparedStatement sns; // setNameStatement;
    private PreparedStatement ds; // deleteByIdStatement;
    private PreparedStatement las; // listAllStatement;

    public ClientService() {
        try {
            cs = conn.prepareStatement("INSERT INTO client(name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            gs = conn.prepareStatement("SELECT name FROM client where id = ?");
            sns = conn.prepareStatement("UPDATE client SET name = ? WHERE id = ?");
            ds = conn.prepareStatement("DELETE FROM client WHERE id = ?");
            las = conn.prepareStatement("SELECT * FROM client");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean invalidId(Long id) { return id < 1; }
    private boolean invalidName(String name) {
        return name.length() < 2 || name.length() > 1000 || ! alphaNumUnder.matcher(name).matches();
    }

    public long create(String name) throws SQLException {
        if ( invalidName(name) ) throw new IllegalArgumentException();

        cs.setString(1, name);
        cs.executeUpdate();

        ResultSet rs = cs.getGeneratedKeys();
        if (rs.next()) return rs.getLong("id");
        else           return 0;
    }

    public String getById(long id) throws SQLException {
        if ( invalidId(id) ) throw new IllegalArgumentException();

        gs.setLong(1, id);
        ResultSet rs = gs.executeQuery();

        if (rs.next()) return rs.getString("name");
        else           return "";
}

    public void setName(long id, String name) throws SQLException {
        if ( invalidName(name) ) throw new IllegalArgumentException();

        sns.setString(1, name);
        sns.setLong(2, id);
        sns.executeUpdate();
    }

    public void deleteById(long id) throws SQLException {
        if ( invalidId(id) ) throw new IllegalArgumentException();

        ds.setLong(1, id);
        ds.executeUpdate();
    }

    public List<Client> listAll() throws SQLException {
        ResultSet rs = las.executeQuery();

        List<Client> res = new ArrayList<>();
        //Client client = new Client(); // HardToAssert Error
        while(rs.next()) {
            Client client = new Client();
            client.setId(rs.getLong("id"));
            client.setName(rs.getString("name"));
            res.add(client);
        }

        return res;
    }
}
