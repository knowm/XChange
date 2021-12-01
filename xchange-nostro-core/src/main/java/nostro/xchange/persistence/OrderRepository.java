package nostro.xchange.persistence;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository {
    private static final String INSERT_SQL =
            "INSERT INTO order$ (id, external_id, instrument, terminal, created, updated, document) VALUES (?,?,?,?,?,?,?)";
    
    private static final String UPDATE_EXTERNAL_ID_SQL =
            "UPDATE order$ SET external_id = ? WHERE id = ?";
    
    private static final String UPDATE_BY_ID_SQL =
            "UPDATE order$ SET document = ?, terminal = ?, updated = ? WHERE id = ?";

    private static final String UPDATE_BY_EXTERNAL_ID_SQL =
            "UPDATE order$ SET document = ?, terminal = ?, updated = ? WHERE external_id = ?";
    
    private static final String UPDATE_CREATED_BY_ID_SQL =
            "UPDATE order$ SET created = ? WHERE id = ?";
    
    private static final String FIND_BY_ID_SQL =
            "SELECT * FROM order$ WHERE id = ?";
    
    private static final String FIND_BY_EXTERNAL_ID_SQL =
            "SELECT * FROM order$ WHERE external_id = ?";

    private static final String FIND_ALL_OPEN_SQL =
            "SELECT * FROM order$ WHERE terminal = FALSE ORDER BY created ASC";

    private static final String FIND_OPEN_BY_INSTRUMENT_SQL =
            "SELECT * FROM order$ WHERE terminal = FALSE AND instrument = ? ORDER BY created ASC";

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

    public void insert(OrderEntity o) throws SQLException {
        try(PreparedStatement stmt = prepareStatement(INSERT_SQL)) {
            stmt.setString(1, o.getId());
            stmt.setString(2, o.getExternalId());
            stmt.setString(3, o.getInstrument());
            stmt.setBoolean(4, o.isTerminal());
            stmt.setTimestamp(5, o.getCreated());
            stmt.setTimestamp(6, o.getUpdated());
            stmt.setObject(7, o.getDocument(), Types.OTHER);
            
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

    public void updateById(String id, String document, boolean terminal, Timestamp updated) throws SQLException {
        updateById(UPDATE_BY_ID_SQL, id, document, terminal, updated);
    }

    public void updateByExternalId(String externalId, String document, boolean terminal, Timestamp updated) throws SQLException {
        updateById(UPDATE_BY_EXTERNAL_ID_SQL, externalId, document, terminal, updated);
    }
    
    private void updateById(String sql, String id, String document, boolean terminal, Timestamp updated) throws SQLException {
        try(PreparedStatement stmt = prepareStatement(sql)) {
            stmt.setObject(1, document, Types.OTHER);
            stmt.setBoolean(2, terminal);
            stmt.setTimestamp(3, updated);
            stmt.setString(4, id);
            stmt.executeUpdate();
        }
    }


    public void updateCreatedById(String id, Timestamp created) throws SQLException {
        try(PreparedStatement stmt = prepareStatement(UPDATE_CREATED_BY_ID_SQL)) {
            stmt.setTimestamp(1, created);
            stmt.setString(2, id);
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

    public List<OrderEntity> findOpenOrders(Instrument instrument) throws SQLException {
        return findOpenByInstrument(instrument.toString());
    }

    public List<OrderEntity> findOpenByInstrument(String instrument) throws SQLException {
        try(PreparedStatement stmt = prepareStatement(FIND_OPEN_BY_INSTRUMENT_SQL)) {
            stmt.setString(1, instrument);
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
                .instrument(rs.getString("instrument"))
                .terminal(rs.getBoolean("terminal"))
                .created(rs.getTimestamp("created"))
                .updated(rs.getTimestamp("updated"))
                .document(rs.getString("document"))
                .build();
    }
}
