package nostro.xchange.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FundingRepository {
    private static final String INSERT_SQL =
            "INSERT INTO funding$ (external_id, type, timestamp, document) VALUES (?,?,?,?)";
    
    private static final String FIND_ALL_BY_TIMESTAMP_SQL =
            "SELECT * FROM funding$ WHERE timestamp >= ? ORDER BY timestamp ASC";

    private static final String FIND_ALL_BY_TIMESTAMP_RANGE_SQL =
            "SELECT * FROM funding$ WHERE timestamp BETWEEN ? AND ? ORDER BY timestamp ASC";

    private static final String TABLE_NAME = "funding$";

    private final Connection connection;
    private final String tableName;

    public FundingRepository(Connection connection, String postfix) {
        this.connection = connection;
        this.tableName = TABLE_NAME + postfix;
    }

    public void insert(String externalId, String type, Timestamp timestamp, String document) throws SQLException {
        try(PreparedStatement stmt = prepareStatement(INSERT_SQL)) {
            stmt.setString(1, externalId);
            stmt.setString(2, type);
            stmt.setTimestamp(3, timestamp);
            stmt.setObject(4, document, Types.OTHER);
            stmt.executeUpdate();
        }
    }

    public void insert(FundingEntity o) throws SQLException {
        insert(o.getExternalId(), o.getType(), o.getTimestamp(), o.getDocument());
    }

    public List<FundingEntity> findAllByTimestamp(Timestamp from, Timestamp to) throws SQLException {
        boolean range = to != null;
        String sql = range ? FIND_ALL_BY_TIMESTAMP_RANGE_SQL : FIND_ALL_BY_TIMESTAMP_SQL;
        try(PreparedStatement stmt = prepareStatement(sql)) {
            stmt.setTimestamp(1, from);
            if (range) {
                stmt.setTimestamp(2, to);
            }
            ResultSet rs = stmt.executeQuery();
            List<FundingEntity> list = new ArrayList<>();
            while (rs.next()) list.add(fromResultSet(rs));
            return list;
        }
    }

    private PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql.replace(TABLE_NAME, tableName));
    }

    private static FundingEntity fromResultSet(ResultSet rs) throws SQLException {
        return new FundingEntity.Builder()
                .id(rs.getLong("id"))
                .externalId(rs.getString("external_id"))
                .type(rs.getString("type"))
                .timestamp(rs.getTimestamp("timestamp"))
                .document(rs.getString("document"))
                .build();
    }
}
