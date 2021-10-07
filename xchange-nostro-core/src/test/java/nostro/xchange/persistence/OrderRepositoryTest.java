package nostro.xchange.persistence;

import nostro.xchange.utils.NostroUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
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
        String instrument = "BTC/USDT";
        String document = "{\"price\": 0.5, \"amount\": 8}";
        Timestamp created = new Timestamp(0L);
        Timestamp updated = new Timestamp(System.currentTimeMillis());
        repository.insert(new OrderEntity.Builder()
                .id(id)
                .instrument(instrument)
                .document(document)
                .terminal(false)
                .created(created)
                .updated(updated)
                .build());

        Optional<OrderEntity> order = repository.findById(id);

        assertThat(order.isPresent()).isTrue();
        assertThat(order.get().getId()).isEqualTo(id);
        assertThat(order.get().getExternalId()).isNull();
        assertThat(order.get().getInstrument()).isEqualTo(instrument);
        assertThat(order.get().getDocument()).isEqualTo(document);
        assertThat(order.get().isTerminal()).isFalse();
        assertThat(order.get().getCreated()).isEqualTo(created);
        assertThat(order.get().getUpdated()).isEqualTo(updated);
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
                .instrument("BTC/USDT")
                .document("{\"price\": 0.7, \"amount\": 3}")
                .terminal(true)
                .created(new Timestamp(System.currentTimeMillis() - 1000))
                .updated(new Timestamp(System.currentTimeMillis()))
                .build();
        repository.insert(existing);

        Optional<OrderEntity> order = repository.findByExternalId("10001");
        assertThat(order.isPresent()).isTrue();
        assertThat(order.get().getId()).isEqualTo(existing.getId());
        assertThat(order.get().getExternalId()).isEqualTo(existing.getExternalId());
        assertThat(order.get().getInstrument()).isEqualTo(existing.getInstrument());
        assertThat(order.get().isTerminal()).isEqualTo(existing.isTerminal());
    }

    @Test
    public void updateExternalIdAndFind() throws SQLException {
        String id = NostroUtils.randomUUID();
        repository.insert(new OrderEntity.Builder()
                .id(id)
                .instrument("ETH/USDT")
                .document("{\"price\": 0.5, \"amount\": 8}")
                .terminal(false)
                .created(new Timestamp(0))
                .updated(new Timestamp(0))
                .build());
        
        repository.update(id, "20002");

        Optional<OrderEntity> order = repository.findByExternalId("20002");

        assertThat(order.isPresent()).isTrue();
        assertThat(order.get().getId()).isEqualTo(id);
    }

    @Test
    public void updateById() throws SQLException {
        OrderEntity existing = new OrderEntity.Builder()
                .id(NostroUtils.randomUUID())
                .externalId("30003")
                .instrument("BTC/USDT")
                .document("{\"price\": 0, \"amount\": 0}")
                .terminal(false)
                .created(new Timestamp(0))
                .updated(new Timestamp(0))
                .build();
        repository.insert(existing);
        
        repository.updateById(existing.getId(), "{\"price\": 0.3, \"amount\": 1}", true, new Timestamp(1000));

        Optional<OrderEntity> order = repository.findById(existing.getId());
        assertThat(order.isPresent()).isTrue();
        assertThat(order.get().getId()).isEqualTo(existing.getId());
        assertThat(order.get().getExternalId()).isEqualTo(existing.getExternalId());
        assertThat(order.get().getInstrument()).isEqualTo(existing.getInstrument());
        assertThat(order.get().getDocument()).isEqualTo("{\"price\": 0.3, \"amount\": 1}");
        assertThat(order.get().isTerminal()).isTrue();
        assertThat(order.get().getCreated()).isEqualTo(new Timestamp(0));
        assertThat(order.get().getUpdated()).isEqualTo(new Timestamp(1000));
    }

    @Test
    public void updateByExternalId() throws SQLException {
        OrderEntity existing = new OrderEntity.Builder()
                .id(NostroUtils.randomUUID())
                .externalId("40004")
                .instrument("BTC/USDT")
                .document("{\"price\": 0, \"amount\": 0}")
                .terminal(false)
                .created(new Timestamp(0))
                .updated(new Timestamp(0))
                .build();
        repository.insert(existing);

        repository.updateByExternalId("40004", "{\"price\": 0.3, \"amount\": 1}", true, new Timestamp(1000));

        Optional<OrderEntity> order = repository.findByExternalId("40004");
        assertThat(order.isPresent()).isTrue();
        assertThat(order.get().getId()).isEqualTo(existing.getId());
        assertThat(order.get().getExternalId()).isEqualTo(existing.getExternalId());
        assertThat(order.get().getInstrument()).isEqualTo(existing.getInstrument());
        assertThat(order.get().getDocument()).isEqualTo("{\"price\": 0.3, \"amount\": 1}");
        assertThat(order.get().isTerminal()).isTrue();
        assertThat(order.get().getCreated()).isEqualTo(new Timestamp(0));
        assertThat(order.get().getUpdated()).isEqualTo(new Timestamp(1000));
    }

    @Test
    public void findAllOpenAndByInstrument() throws Exception {
        TransactionFactory factory = TransactionFactory.get("test", "open_orders");

        String id1 = NostroUtils.randomUUID();
        String id2 = NostroUtils.randomUUID();
        String id3 = NostroUtils.randomUUID();

        OrderEntity.Builder builder = new OrderEntity.Builder()
                .terminal(false)
                .document("{\"key\": \"value\"}")
                .created(new Timestamp(0))
                .updated(new Timestamp(0));
        
        factory.execute(t -> {
            OrderRepository r = t.getOrderRepository();
            r.insert(builder.id(id1).instrument("c1/c2").build());
            r.insert(builder.id(id2).instrument("c1/c2").build());
            r.insert(builder.id(id3).instrument("c1/c3").build());
        });

        factory.execute(t -> {
            OrderRepository r = t.getOrderRepository();
            r.update(id2, "id2ex");
            r.updateByExternalId("id2ex", "{\"p\": 1.0, \"a\": 10}", true, new Timestamp(1000));
        });

        factory.execute(t -> {
            OrderRepository r = t.getOrderRepository();
            r.update(id3, "id3ex");
            r.updateByExternalId("id3ex", "{\"p\": 1.1, \"a\": 5}", false, new Timestamp(1000));
        });

        List<OrderEntity> orders = factory.executeAndGet(t -> t.getOrderRepository().findAllOpen());

        assertThat(orders.size()).isEqualTo(2);
        assertThat(orders.get(0).getId()).isEqualTo(id1);
        assertThat(orders.get(1).getId()).isEqualTo(id3);

        orders = factory.executeAndGet(t -> t.getOrderRepository().findOpenByInstrument("c1/c2"));
        assertThat(orders.size()).isEqualTo(1);
        assertThat(orders.get(0).getId()).isEqualTo(id1);

        orders = factory.executeAndGet(t -> t.getOrderRepository().findOpenByInstrument("c1/c3"));
        assertThat(orders.size()).isEqualTo(1);
        assertThat(orders.get(0).getId()).isEqualTo(id3);
    }

    @Test
    public void lockById() throws SQLException {
        String orderId = NostroUtils.randomUUID();
        OrderEntity existing = new OrderEntity.Builder()
                .id(orderId)
                .externalId("70007")
                .instrument("ETH/USDT")
                .document("{\"price\": 0.2, \"amount\": 40}")
                .terminal(false)
                .created(new Timestamp(0))
                .updated(new Timestamp(0))
                .build();
        repository.insert(existing);

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
                .externalId("80008")
                .instrument("ETH/USDT")
                .document("{\"price\": 0.2, \"amount\": 40}")
                .terminal(false)
                .created(new Timestamp(0))
                .updated(new Timestamp(0))
                .build();
        repository.insert(existing);

        Connection txConnection = TransactionFactory.getDataSource().getConnection();
        Connection failConnection = TransactionFactory.getDataSource().getConnection();
        Connection okConnection = TransactionFactory.getDataSource().getConnection();

        txConnection.setAutoCommit(false);
        failConnection.setAutoCommit(false);
        okConnection.setAutoCommit(false);

        OrderRepository txRepository = new OrderRepository(txConnection, "");

        String sql = "SELECT * FROM order$ WHERE external_id='80008' FOR UPDATE NOWAIT";

        txRepository.lockByExternalId("80008");
        assertThatCode(() -> failConnection.prepareStatement(sql).execute()).hasMessageContaining("could not obtain lock");

        txConnection.commit();
        assertThatCode(() -> okConnection.prepareStatement(sql).execute()).doesNotThrowAnyException();

        txConnection.close();
        failConnection.close();
        okConnection.close();
    }
}