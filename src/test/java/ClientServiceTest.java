import itm.dbWorks.ClientService;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

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

        // Test listAll
        long firstClientId = 0L;
        String firstClientName = "";
        for(long i=0L; ++i < 1000L;) {
            firstClientName = clientService.getById(i);
            if(! firstClientName.isEmpty() ) { firstClientId = i; break; }
        }
        long finalFirstClientId = firstClientId;
        String finalFirstClientName = firstClientName;
        AtomicBoolean firstIdEncountered = new AtomicBoolean(false);
        clientService.listAll().forEach((c) -> {
            try {
                if(c.getId() == finalFirstClientId ) {
                    firstIdEncountered.set(true);
                    assertEquals(finalFirstClientName, c.getName()); }
                if(c.getId() == idOfGoodNameClientCreated ) assertEquals(expectedGoodName, clientService.getById(c.getId()));
                assertEquals(c.getName(), clientService.getById(c.getId()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        assertTrue(firstIdEncountered.get()); // test against HardToAssert Error
    }
}













