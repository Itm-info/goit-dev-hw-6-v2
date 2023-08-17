package itm.dbWorks;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;

public class DatabaseInitService {
    public static MigrateResult initDb() {
        Flyway flyway = Flyway
                .configure()
                .dataSource(Database.CONNECTION_URL, null, null)
                .load();

        return flyway.migrate();
    }
}