package nostro.xchange.persistence;

import nostro.xchange.utils.NostroUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class OrderRepositoryTest extends DataSourceTest {

    private Connection connection;
    private OrderRepository repository;

    @Before
    public void setUp() throws SQLException {
        connection = TransactionFactory.getDataSource().getConnection();
        repository = new OrderRepository(connection, "");
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void insertNewFindById() throws SQLException {
        String id = NostroUtils.randomUUID();
        String document = "{\"price\": 0.5, \"amount\": 8}";
        repository.insert(id, document);

        Optional<OrderEntity> order = repository.findById(id);

        assertThat(order.isPresent()).isTrue();
        assertThat(order.get().getId()).isEqualTo(id);
        assertThat(order.get().getExternalId()).isNull();
        assertThat(order.get().getDocument()).isEqualTo(document);
        assertThat(order.get().isTerminal()).isFalse();
        assertThat(order.get().getCreated()).isNotNull();
        assertThat(order.get().getUpdated()).isNotNull();
    }

    @Test
    public void findByIdEmpty() throws SQLException {
        Optional<OrderEntity> order = repository.findById(NostroUtils.randomUUID());
        assertThat(order.isPresent()).isFalse();
    }

    @Test
    public void findByExternalIdEmpty() throws SQLException {
        Optional<OrderEntity> order = repository.findByExternalId(NostroUtils.randomUUID());
        assertThat(order.isPresent()).isFalse();
    }

    @Test
    public void insertAndFindByExternalId() throws SQLException {
        OrderEntity existing = new OrderEntity.Builder()
                .id(NostroUtils.randomUUID())
                .externalId("10001")
                .document("{\"price\": 0.7, \"amount\": 3}")
                .terminal(true)
                .build();
        repository.insert(existing);

        Optional<OrderEntity> order = repository.findByExternalId("10001");
        assertThat(order.isPresent()).isTrue();
        assertThat(order.get().getId()).isEqualTo(existing.getId());
        assertThat(order.get().getExternalId()).isEqualTo(existing.getExternalId());
        assertThat(order.get().isTerminal()).isEqualTo(existing.isTerminal());
    }

    @Test
    public void updateExternalIdAndFind() throws SQLException {
        String id = NostroUtils.randomUUID();
        String document = "{\"price\": 0.5, \"amount\": 8}";
        repository.insert(id, document);
        repository.update(id, "20002");

        Optional<OrderEntity> order = repository.findByExternalId("20002");

        assertThat(order.isPresent()).isTrue();
        assertThat(order.get().getId()).isEqualTo(id);

        Timestamp created = order.get().getCreated();
        Timestamp updated = order.get().getUpdated();
        assertThat(created).isNotNull();
        assertThat(updated).isNotNull();
        assertThat(created.before(updated)).isTrue();
    }

    @Test
    public void updateById() throws SQLException {
        OrderEntity existing = new OrderEntity.Builder()
                .id(NostroUtils.randomUUID())
                .externalId("30003")
                .document("{\"price\": 0, \"amount\": 0}")
                .terminal(false)
                .build();
        repository.insert(existing);
        
        repository.updateById(existing.getId(), "{\"price\": 0.3, \"amount\": 1}", true);

        Optional<OrderEntity> order = repository.findById(existing.getId());
        assertThat(order.isPresent()).isTrue();
        assertThat(order.get().getId()).isEqualTo(existing.getId());
        assertThat(order.get().getExternalId()).isEqualTo(existing.getExternalId());
        assertThat(order.get().getDocument()).isEqualTo("{\"price\": 0.3, \"amount\": 1}");
        assertThat(order.get().isTerminal()).isTrue();

        Timestamp created = order.get().getCreated();
        Timestamp updated = order.get().getUpdated();
        assertThat(created).isNotNull();
        assertThat(updated).isNotNull();
        assertThat(created.before(updated)).isTrue();
    }

    @Test
    public void updateByExternalId() throws SQLException {
        OrderEntity existing = new OrderEntity.Builder()
                .id(NostroUtils.randomUUID())
                .externalId("40004")
                .document("{\"price\": 0, \"amount\": 0}")
                .terminal(false)
                .build();
        repository.insert(existing);

        repository.updateByExternalId("40004", "{\"price\": 0.3, \"amount\": 1}", true);

        Optional<OrderEntity> order = repository.findByExternalId("40004");
        assertThat(order.isPresent()).isTrue();
        assertThat(order.get().getId()).isEqualTo(existing.getId());
        assertThat(order.get().getExternalId()).isEqualTo(existing.getExternalId());
        assertThat(order.get().getDocument()).isEqualTo("{\"price\": 0.3, \"amount\": 1}");
        assertThat(order.get().isTerminal()).isTrue();

        Timestamp created = order.get().getCreated();
        Timestamp updated = order.get().getUpdated();
        assertThat(created).isNotNull();
        assertThat(updated).isNotNull();
        assertThat(created.before(updated)).isTrue();
    }

    @Test
    public void findAllOpen() throws Exception {
        TransactionFactory factory = TransactionFactory.get("test", "open_orders");

        String id1 = NostroUtils.randomUUID();
        String id2 = NostroUtils.randomUUID();
        String id3 = NostroUtils.randomUUID();
        
        factory.execute(t -> {
            OrderRepository r = t.getOrderRepository();
            r.insert(id1, "{\"key\": \"value\"}");
            r.insert(id2, "{\"key\": \"value\"}");
            r.insert(id3, "{\"key\": \"value\"}");
        });

        factory.execute(t -> {
            OrderRepository r = t.getOrderRepository();
            r.update(id2, "id2ex");
            r.updateByExternalId("id2ex", "{\"p\": 1.0, \"a\": 10}", true);
        });

        factory.execute(t -> {
            OrderRepository r = t.getOrderRepository();
            r.update(id3, "id3ex");
            r.updateByExternalId("id3ex", "{\"p\": 1.1, \"a\": 5}", false);
        });

        List<OrderEntity> orders = new ArrayList<>();
        factory.execute(t -> {
            OrderRepository r = t.getOrderRepository();
            orders.addAll(r.findAllOpen());
        });

        assertThat(orders.size()).isEqualTo(2);
        assertThat(orders.get(0).getId()).isEqualTo(id1);
        assertThat(orders.get(1).getId()).isEqualTo(id3);
    }

    @Test
    public void lockById() throws SQLException {
        String orderId = NostroUtils.randomUUID();
        String document = "{\"price\": 0.2, \"amount\": 40}";
        
        repository.insert(orderId, document);

        Connection txConnection = TransactionFactory.getDataSource().getConnection();
        Connection failConnection = TransactionFactory.getDataSource().getConnection();
        Connection okConnection = TransactionFactory.getDataSource().getConnection();
        
        txConnection.setAutoCommit(false);
        failConnection.setAutoCommit(false);
        okConnection.setAutoCommit(false);

        OrderRepository txRepository = new OrderRepository(txConnection, "");

        String sql = "SELECT * FROM order$ WHERE id='" + orderId + "' FOR UPDATE NOWAIT";

        txRepository.lockById(orderId);
        assertThatCode(() -> failConnection.prepareStatement(sql).execute()).hasMessageContaining("could not obtain lock");
        
        txConnection.commit();
        assertThatCode(() -> okConnection.prepareStatement(sql).execute()).doesNotThrowAnyException();

        txConnection.close();
        failConnection.close();
        okConnection.close();
    }
    
    @Test
    public void lockByExternalId() throws SQLException {
        OrderEntity existing = new OrderEntity.Builder()
                .id(NostroUtils.randomUUID())
                .externalId("70007")
                .document("{\"price\": 0.2, \"amount\": 40}")
                .terminal(false)
                .build();
        repository.insert(existing);

        Connection txConnection = TransactionFactory.getDataSource().getConnection();
        Connection failConnection = TransactionFactory.getDataSource().getConnection();
        Connection okConnection = TransactionFactory.getDataSource().getConnection();

        txConnection.setAutoCommit(false);
        failConnection.setAutoCommit(false);
        okConnection.setAutoCommit(false);

        OrderRepository txRepository = new OrderRepository(txConnection, "");

        String sql = "SELECT * FROM order$ WHERE external_id='70007' FOR UPDATE NOWAIT";

        txRepository.lockByExternalId("70007");
        assertThatCode(() -> failConnection.prepareStatement(sql).execute()).hasMessageContaining("could not obtain lock");

        txConnection.commit();
        assertThatCode(() -> okConnection.prepareStatement(sql).execute()).doesNotThrowAnyException();

        txConnection.close();
        failConnection.close();
        okConnection.close();
    }
}