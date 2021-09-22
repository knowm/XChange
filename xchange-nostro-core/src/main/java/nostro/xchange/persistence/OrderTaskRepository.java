package nostro.xchange.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderTaskRepository {
    private static final String INSERT_SQL =
            "INSERT INTO order_task$ (order_id, type, finished, document) VALUES (?,?,?,?)";

    private static final String UPDATE_SQL =
            "UPDATE order_task$ SET finished = ?, document = ? WHERE order_id = ? AND type = ?";

    private static final String FIND_SQL =
            "SELECT * FROM order_task$ WHERE order_id = ? AND type = ? ORDER BY order_id ASC";

    private static final String FIND_ACTIVE_BY_TYPE_SQL =
            "SELECT * FROM order_task$ WHERE finished = FALSE AND type = ? ORDER BY order_id ASC";
    
    private static final String TABLE_NAME = "order_task$";

    private final Connection connection;
    private final String tableName;

    public OrderTaskRepository(Connection connection, String postfix) {
        this.connection = connection;
        this.tableName = TABLE_NAME + postfix;
    }

    public void insert(String orderId, String type, boolean finished, String document) throws SQLException {
        try(PreparedStatement stmt = prepareStatement(INSERT_SQL)) {
            stmt.setString(1, orderId);
            stmt.setString(2, type);
            stmt.setBoolean(3, finished);
            stmt.setObject(4, document, Types.OTHER);
            stmt.executeUpdate();
        }
    }

    public void update(String orderId, String type, boolean finished, String document) throws SQLException {
        try(PreparedStatement stmt = prepareStatement(UPDATE_SQL)) {
            stmt.setBoolean(1, finished);
            stmt.setObject(2, document, Types.OTHER);
            stmt.setString(3, orderId);
            stmt.setString(4, type);
            stmt.executeUpdate();
        }
    }
    
    public Optional<OrderTaskEntity> find(String orderId, String type) throws SQLException {
        try(PreparedStatement stmt = prepareStatement(FIND_SQL)) {
            stmt.setString(1, orderId);
            stmt.setString(2, type);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? Optional.of(fromResultSet(rs)) : Optional.empty();
        }
    }

    public List<OrderTaskEntity> findActiveByType(String type) throws SQLException {
        try(PreparedStatement stmt = prepareStatement(FIND_ACTIVE_BY_TYPE_SQL)) {
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();
            List<OrderTaskEntity> list = new ArrayList<>();
            while (rs.next()) list.add(fromResultSet(rs));
            return list;
        }
    }

    private PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql.replace(TABLE_NAME, tableName));
    }

    private static OrderTaskEntity fromResultSet(ResultSet rs) throws SQLException {
        return new OrderTaskEntity(
                rs.getString("order_id"),
                rs.getString("type"),
                rs.getBoolean("finished"),
                rs.getString("document"));
    }
}
