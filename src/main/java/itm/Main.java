package itm;

import itm.dbWorks.*;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println("Hello world!");

        DatabaseInitService.initDb();

        ClientService clientService = new ClientService();

        long id = clientService.create("Name");

        clientService.getById(id);

        clientService.setName(id,"New_Name");

        clientService.deleteById(id-1);

        clientService.listAll().forEach(System.out::println);

    }
}