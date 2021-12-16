package nostro.xchange.binance.utils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NostroDBUtils {
    public static void dropTable(DataSource dataSource, final String tableName) throws SQLException, InterruptedException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("DELETE FROM " + tableName)) {
            stmt.execute();
        }
    }
}
