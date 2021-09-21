package nostro.xchange.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BalanceRepository {
    private static final String INSERT_SQL =
            "INSERT INTO balance$ (asset, timestamp, zero, document) VALUES (?,?,?,?)";
    
    private static final String FIND_BY_ASSET_SQL =
            "SELECT * FROM balance$ WHERE asset = ? ORDER BY timestamp DESC, id DESC LIMIT 1";

    private static final String FIND_ALL_NON_ZERO_SQL =
            "SELECT DISTINCT ON(asset) * FROM balance$ WHERE zero = FALSE ORDER BY asset ASC, timestamp DESC";
    
    private static final String LOCK_TABLE_SQL = 
            "LOCK TABLE balance$ IN EXCLUSIVE MODE";

    private static final String TABLE_NAME = "balance$";

    private final Connection connection;
    private final String tableName;

    public BalanceRepository(Connection connection, String postfix) {
        this.connection = connection;
        this.tableName = TABLE_NAME + postfix;
    }
    
    public void insert(BalanceEntity e) throws SQLException {
        insert(e.getAsset(), e.getTimestamp(), e.isZero(), e.getDocument());
    }

    public void insert(String asset, Timestamp timestamp, boolean zero, String document) throws SQLException {
        try(PreparedStatement stmt = prepareStatement(INSERT_SQL)) {
            stmt.setString(1, asset);
            stmt.setTimestamp(2, timestamp);
            stmt.setBoolean(3, zero);
            stmt.setObject(4, document, Types.OTHER);
            stmt.executeUpdate();
        }
    }
    
    public Optional<BalanceEntity> findLatestByAsset(String asset) throws SQLException {
        try(PreparedStatement stmt = prepareStatement(FIND_BY_ASSET_SQL)) {
            stmt.setString(1, asset);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? Optional.of(fromResultSet(rs)) : Optional.empty();
        }
    }

    public List<BalanceEntity> findAllLatest() throws SQLException {
        try(PreparedStatement stmt = prepareStatement(FIND_ALL_NON_ZERO_SQL)) {
            ResultSet rs = stmt.executeQuery();
            List<BalanceEntity> list = new ArrayList<>();
            while (rs.next()) list.add(fromResultSet(rs));
            return list;
        }
    }

    public void lock() throws SQLException {
        try(PreparedStatement stmt = prepareStatement(LOCK_TABLE_SQL)) {
            stmt.execute();
        }
    }

    private PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql.replace(TABLE_NAME, tableName));
    }

    private static BalanceEntity fromResultSet(ResultSet rs) throws SQLException {
        return new BalanceEntity.Builder()
                .asset(rs.getString("asset"))
                .timestamp(rs.getTimestamp("timestamp"))
                .zero(rs.getBoolean("zero"))
                .document(rs.getString("document"))
                .build();
    }
}
