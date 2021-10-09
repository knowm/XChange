package nostro.xchange.persistence;

import java.sql.*;

public class AccountRepository {
    private static final String SELECT_SQL =
            "SELECT document FROM account WHERE id = ?";
    
    private static final String LOCK_SQL =
            "SELECT document FROM account WHERE id = ? FOR UPDATE";

    private static final String UPDATE_SQL =
            "UPDATE account SET document = ? WHERE id = ?";

    private final Connection connection;
    private final int accountId;

    public AccountRepository(Connection connection, int accountId) {
        this.connection = connection;
        this.accountId = accountId;
    }

    public String get() throws SQLException {
        return select(SELECT_SQL);
    }

    public String lock() throws SQLException {
        return select(LOCK_SQL);
    }
    
    private String select(String sql) throws SQLException {
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getString(1);
        }
    }

    public void update(String document) throws SQLException {
        try(PreparedStatement stmt = connection.prepareStatement(UPDATE_SQL)) {
            stmt.setObject(1, document, Types.OTHER);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();
        }
    }
}
