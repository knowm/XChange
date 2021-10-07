package nostro.xchange.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SyncTaskRepository {
    private static final String INSERT_SQL =
            "INSERT INTO sync_task$ (symbol, timestamp, document) VALUES (?,?,?)";
    
    private static final String FIND_BY_SYMBOL_SQL =
            "SELECT * FROM sync_task$ WHERE symbol = ? ORDER BY timestamp DESC LIMIT 1";

    private static final String FIND_ALL_SQL =
            "SELECT * FROM (SELECT DISTINCT ON(symbol) * FROM sync_task$ ORDER BY symbol, timestamp DESC) t ORDER BY t.timestamp ASC";

    private static final String TABLE_NAME = "sync_task$";

    private final Connection connection;
    private final String tableName;

    public SyncTaskRepository(Connection connection, String postfix) {
        this.connection = connection;
        this.tableName = TABLE_NAME + postfix;
    }

    public void insert(String symbol, Timestamp timestamp, String document) throws SQLException {
        try(PreparedStatement stmt = prepareStatement(INSERT_SQL)) {
            stmt.setString(1, symbol);
            stmt.setTimestamp(2, timestamp);
            stmt.setObject(3, document, Types.OTHER);
            stmt.executeUpdate();
        }
    }
    
    public Optional<SyncTaskEntity> findLatestBySymbol(String symbol) throws SQLException {
        try(PreparedStatement stmt = prepareStatement(FIND_BY_SYMBOL_SQL)) {
            stmt.setString(1, symbol);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? Optional.of(fromResultSet(rs)) : Optional.empty();
        }
    }

    public List<SyncTaskEntity> findAllLatest() throws SQLException {
        try(PreparedStatement stmt = prepareStatement(FIND_ALL_SQL)) {
            ResultSet rs = stmt.executeQuery();
            List<SyncTaskEntity> list = new ArrayList<>();
            while (rs.next()) list.add(fromResultSet(rs));
            return list;
        }
    }

    private PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql.replace(TABLE_NAME, tableName));
    }

    private static SyncTaskEntity fromResultSet(ResultSet rs) throws SQLException {
        return new SyncTaskEntity(
                rs.getString("symbol"),
                rs.getTimestamp("timestamp"),
                rs.getString("document"));
    }
}
