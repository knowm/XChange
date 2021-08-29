package nostro.xchange.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TradeRepository {
    private static final String INSERT_SQL =
            "INSERT INTO trade$ (order_id, external_id, timestamp, document) VALUES (?,?,?,?)";
    
    private static final String FIND_BY_EXTERNAL_ID_SQL =
            "SELECT * FROM trade$ WHERE external_id = ?";

    private static final String FIND_ALL_BY_ORDER_ID_SQL =
            "SELECT * FROM trade$ WHERE order_id = ? ORDER BY timestamp ASC";

    private static final String FIND_ALL_BY_TIMESTAMP_SQL =
            "SELECT * FROM trade$ WHERE timestamp BETWEEN ? AND ? ORDER BY timestamp ASC";

    private static final String TABLE_NAME = "trade$";

    private final Connection connection;
    private final String tableName;

    public TradeRepository(Connection connection, String postfix) {
        this.connection = connection;
        this.tableName = TABLE_NAME + postfix;
    }

    public void insert(String orderId, String externalId, Timestamp timestamp, String document) throws SQLException {
        try(PreparedStatement stmt = prepareStatement(INSERT_SQL)) {
            stmt.setString(1, orderId);
            stmt.setString(2, externalId);
            stmt.setTimestamp(3, timestamp);
            stmt.setObject(4, document, Types.OTHER);
            stmt.executeUpdate();
        }
    }

    public void insert(TradeEntity o) throws SQLException {
        insert(o.getOrderId(), o.getExternalId(), o.getTimestamp(), o.getDocument());
    }
    
    public Optional<TradeEntity> findByExternalId(String externalId) throws SQLException {
        try(PreparedStatement stmt = prepareStatement(FIND_BY_EXTERNAL_ID_SQL)) {
            stmt.setString(1, externalId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? Optional.of(fromResultSet(rs)) : Optional.empty();
        }
    }

    public List<TradeEntity> findAllByOrderId(String orderId) throws SQLException {
        try(PreparedStatement stmt = prepareStatement(FIND_ALL_BY_ORDER_ID_SQL)) {
            stmt.setString(1, orderId);
            ResultSet rs = stmt.executeQuery();
            List<TradeEntity> list = new ArrayList<>();
            while (rs.next()) list.add(fromResultSet(rs));
            return list;
        }
    }

    public List<TradeEntity> findAllByTimestamp(Timestamp from, Timestamp to) throws SQLException {
        try(PreparedStatement stmt = prepareStatement(FIND_ALL_BY_TIMESTAMP_SQL)) {
            stmt.setTimestamp(1, from);
            stmt.setTimestamp(2, to);
            ResultSet rs = stmt.executeQuery();
            List<TradeEntity> list = new ArrayList<>();
            while (rs.next()) list.add(fromResultSet(rs));
            return list;
        }
    }

    private PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql.replace(TABLE_NAME, tableName));
    }

    private static TradeEntity fromResultSet(ResultSet rs) throws SQLException {
        return new TradeEntity.Builder()
                .id(rs.getLong("id"))
                .orderId(rs.getString("order_id"))
                .externalId(rs.getString("external_id"))
                .timestamp(rs.getTimestamp("timestamp"))
                .document(rs.getString("document"))
                .build();
    }
}
