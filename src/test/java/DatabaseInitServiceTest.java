import itm.dbWorks.DatabaseInitService;
import org.flywaydb.core.api.output.MigrateResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatabaseInitServiceTest {
    @Test
    public void testThatInitDbWorksCorrect() {
        //Given
        MigrateResult initResult = DatabaseInitService.initDb();

        //When
        String expectedVersion = "2";

        //Then
        assertTrue(initResult.success);
        assertEquals(expectedVersion, initResult.targetSchemaVersion);
    }
}
