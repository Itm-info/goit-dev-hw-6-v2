package itm;

import itm.dbWorks.*;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        new DatabaseInitService.initDb();
        
        System.out.println("Hello world!");

        long id = ClientService.create("Name");

        System.out.println("Just added: " + ClientService.getById(id));

        ClientService.setName(id,"New_Name");

        ClientService.deleteById(id-1);

        ClientService.listAll().forEach(System.out::println);

    }
}