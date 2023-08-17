import itm.dbWorks.ClientService;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceTest {
    @Test
    public void testThatClientServiceWorksCorrect() throws SQLException {
        //Given
        ClientService clientService = new ClientService();

        //When
        String expectedGoodName = "Jack";

        String expectedBadNameSpace = "Bad Name";
        String expectedBadNameEmpty = "";
        String expectedBadNameLeet = "/\\/\\AR-15/\\";

        long idOfGoodNameClientCreated = clientService.create(expectedGoodName);

        long expectedBadIdZero = 0L;
        long expectedBadIdSubZero = -1;

        //Then
        // Test create
        assertDoesNotThrow( () -> clientService.create(expectedGoodName) );

        assertThrows(IllegalArgumentException.class, () -> clientService.create(expectedBadNameSpace) );
        assertThrows(IllegalArgumentException.class, () -> clientService.create(expectedBadNameEmpty) );
        assertThrows(IllegalArgumentException.class, () -> clientService.create(expectedBadNameLeet) );

        assertEquals(expectedGoodName, clientService.getById(idOfGoodNameClientCreated));

        // Test getById
        assertThrows(IllegalArgumentException.class, () -> clientService.getById(expectedBadIdZero) );
        assertThrows(IllegalArgumentException.class, () -> clientService.getById(expectedBadIdSubZero) );

        // Test setName
        assertDoesNotThrow( () -> clientService.setName(idOfGoodNameClientCreated, expectedGoodName) );

        assertThrows(IllegalArgumentException.class, () -> clientService.setName(idOfGoodNameClientCreated, expectedBadNameSpace) );
        assertThrows(IllegalArgumentException.class, () -> clientService.setName(idOfGoodNameClientCreated, expectedBadNameEmpty) );
        assertThrows(IllegalArgumentException.class, () -> clientService.setName(idOfGoodNameClientCreated, expectedBadNameLeet) );

        // Test deleteById
        assertDoesNotThrow( () -> clientService.deleteById(idOfGoodNameClientCreated) );

        assertThrows(IllegalArgumentException.class, () -> clientService.deleteById(expectedBadIdZero) );
        assertThrows(IllegalArgumentException.class, () -> clientService.deleteById(expectedBadIdSubZero) );
    }
}













