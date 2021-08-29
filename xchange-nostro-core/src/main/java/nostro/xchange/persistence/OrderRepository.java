package nostro.xchange.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository {
    private static final String INSERT_SQL =
            "INSERT INTO order$ (id, external_id, document, terminal) VALUES (?,?,?,?)";
    
    private static final String UPDATE_EXTERNAL_ID_SQL =
            "UPDATE order$ SET external_id = ?, updated = NOW() WHERE id = ?";
    
    private static final String UPDATE_BY_ID_SQL =
            "UPDATE order$ SET document = ?, terminal = ?, updated = NOW() WHERE id = ?";

    private static final String UPDATE_BY_EXTERNAL_ID_SQL =
            "UPDATE order$ SET document = ?, terminal = ?, updated = NOW() WHERE external_id = ?";
    
    private static final String FIND_BY_ID_SQL =
            "SELECT * FROM order$ WHERE id = ?";
    
    private static final String FIND_BY_EXTERNAL_ID_SQL =
            "SELECT * FROM order$ WHERE external_id = ?";

    private static final String FIND_ALL_OPEN_SQL =
            "SELECT * FROM order$ WHERE terminal = FALSE ORDER BY created ASC";

    private static final String LOCK_BY_ID_SQL =
            "SELECT * FROM order$ WHERE id = ? FOR UPDATE";

    private static final String LOCK_BY_EXTERNAL_ID_SQL =
            "SELECT * FROM order$ WHERE external_id = ? FOR UPDATE";

    private static final String TABLE_NAME = "order$";

    private final Connection connection;
    private final String tableName;

    public OrderRepository(Connection connection, String postfix) {
        this.connection = connection;
        this.tableName = TABLE_NAME + postfix;
    }

    public void insert(String id, String document) throws SQLException {
        insert(new OrderEntity.Builder()
                .id(id)
                .document(document)
                .build());
    }

    public void insert(OrderEntity o) throws SQLException {
        try(PreparedStatement stmt = prepareStatement(INSERT_SQL)) {
            stmt.setString(1, o.getId());
            stmt.setString(2, o.getExternalId());
            stmt.setObject(3, o.getDocument(), Types.OTHER);
            stmt.setBoolean(4, o.isTerminal());
            stmt.executeUpdate();
        }
    }

    public void update(String id, String externalId) throws SQLException {
        try(PreparedStatement stmt = prepareStatement(UPDATE_EXTERNAL_ID_SQL)) {
            stmt.setString(1, externalId);
            stmt.setString(2, id);
            stmt.executeUpdate();
        }
    }

    public void updateById(String id, String document, boolean terminal) throws SQLException {
        updateById(UPDATE_BY_ID_SQL, id, document, terminal);
    }

    public void updateByExternalId(String externalId, String document, boolean terminal) throws SQLException {
        updateById(UPDATE_BY_EXTERNAL_ID_SQL, externalId, document, terminal);
    }
    
    private void updateById(String sql, String id, String document, boolean terminal) throws SQLException {
        try(PreparedStatement stmt = prepareStatement(sql)) {
            stmt.setObject(1, document, Types.OTHER);
            stmt.setBoolean(2, terminal);
            stmt.setString(3, id);
            stmt.executeUpdate();
        }
    }
    
    public Optional<OrderEntity> findById(String id) throws SQLException {
        return findById(FIND_BY_ID_SQL, id);
    }

    public Optional<OrderEntity> findByExternalId(String externalId) throws SQLException {
        return findById(FIND_BY_EXTERNAL_ID_SQL, externalId);
    }
    
    private Optional<OrderEntity> findById(String sql, String id) throws SQLException {
        try(PreparedStatement stmt = prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? Optional.of(fromResultSet(rs)) : Optional.empty();
        }
    }

    public List<OrderEntity> findAllOpen() throws SQLException {
        try(PreparedStatement stmt = prepareStatement(FIND_ALL_OPEN_SQL)) {
            ResultSet rs = stmt.executeQuery();
            List<OrderEntity> list = new ArrayList<>();
            while (rs.next()) list.add(fromResultSet(rs));
            return list;
        }
    }

    public Optional<OrderEntity> lockById(String id) throws SQLException {
        return lockById(LOCK_BY_ID_SQL, id);
    }

    public Optional<OrderEntity> lockByExternalId(String externalId) throws SQLException {
        return lockById(LOCK_BY_EXTERNAL_ID_SQL, externalId);
    }
    
    private Optional<OrderEntity> lockById(String sql, String id) throws SQLException {
        try(PreparedStatement stmt = prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? Optional.of(fromResultSet(rs)) : Optional.empty();
        }
    }

    private PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql.replace(TABLE_NAME, tableName));
    }

    private static OrderEntity fromResultSet(ResultSet rs) throws SQLException {
        return new OrderEntity.Builder()
                .id(rs.getString("id"))
                .externalId(rs.getString("external_id"))
                .terminal(rs.getBoolean("terminal"))
                .created(rs.getTimestamp("created"))
                .updated(rs.getTimestamp("updated"))
                .document(rs.getString("document"))
                .build();
    }
}
