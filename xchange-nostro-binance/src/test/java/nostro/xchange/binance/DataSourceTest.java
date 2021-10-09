package nostro.xchange.binance;

import org.junit.BeforeClass;

public class DataSourceTest {

    @BeforeClass
    public static void setUpDataSource() {
        if (System.getProperties().getProperty("xchange.db.url") == null ) {
            // When testcontainers not working, try following steps:
            // "docker system prune -af" command
            // Docker Preferences -> General -> Disable "Use gRPC FUSE for file sharing"
            System.getProperties().put("xchange.db.url", "jdbc:tc:postgresql:13:///xchange");
            System.getProperties().put("xchange.db.user", "tc.user");
            System.getProperties().put("xchange.db.password", "tc.password");
        }
    }
}
