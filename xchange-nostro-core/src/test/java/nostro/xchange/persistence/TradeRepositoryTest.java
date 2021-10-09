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

public class TradeRepositoryTest extends DataSourceTest {

    private Connection connection;
    private TradeRepository repository;
    
    @Before
    public void setUp() throws SQLException {
        connection = TransactionFactory.getDataSource().getConnection();
        repository = new TradeRepository(connection, "");
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void insertFindByExternalId() throws SQLException {
        String orderId = NostroUtils.randomUUID();
        String externalId = "123abc";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String document = "{\"price\": 0.5, \"amount\": 8}";
        repository.insert(orderId, externalId, timestamp, document);

        Optional<TradeEntity> trade = repository.findByExternalId(externalId);
        assertThat(trade.isPresent()).isTrue();
        assertThat(trade.get().getOrderId()).isEqualTo(orderId);
        assertThat(trade.get().getExternalId()).isEqualTo(externalId);
        assertThat(trade.get().getTimestamp()).isEqualTo(timestamp);
        assertThat(trade.get().getDocument()).isEqualTo(document);
    }

    @Test
    public void insertFindAllByOrderId() throws SQLException {
        String orderId = NostroUtils.randomUUID();

        TradeEntity e1 = new TradeEntity.Builder()
                .orderId(orderId)
                .externalId("e1id")
                .timestamp(new Timestamp(System.currentTimeMillis() - 600_000))
                .document("{\"price\": 0.1, \"amount\": 8}")
                .build();
        repository.insert(e1);

        TradeEntity e2 = new TradeEntity.Builder()
                .orderId(orderId)
                .externalId("e2id")
                .timestamp(new Timestamp(System.currentTimeMillis() - 60_000))
                .document("{\"price\": 0.2, \"amount\": 4}")
                .build();
        repository.insert(e2);

        List<TradeEntity> trades = repository.findAllByOrderId(orderId);
        assertThat(trades.size()).isEqualTo(2);
        assertThat(trades.get(0).getOrderId()).isEqualTo(orderId);
        assertThat(trades.get(0).getExternalId()).isEqualTo(e1.getExternalId());
        assertThat(trades.get(0).getTimestamp()).isEqualTo(e1.getTimestamp());
        assertThat(trades.get(0).getDocument()).isEqualTo(e1.getDocument());

        assertThat(trades.get(1).getOrderId()).isEqualTo(orderId);
        assertThat(trades.get(1).getExternalId()).isEqualTo(e2.getExternalId());
        assertThat(trades.get(1).getTimestamp()).isEqualTo(e2.getTimestamp());
        assertThat(trades.get(1).getDocument()).isEqualTo(e2.getDocument());
    }

    @Test
    public void findAllByOrderIdEmpty() throws SQLException {
        List<TradeEntity> trades = repository.findAllByOrderId(NostroUtils.randomUUID());
        assertThat(trades.isEmpty()).isTrue();
    }

    @Test
    public void findByExternalIdEmpty() throws SQLException {
        Optional<TradeEntity> trade = repository.findByExternalId(NostroUtils.randomUUID());
        assertThat(trade.isPresent()).isFalse();
    }
    
    @Test
    public void insertFindAllByTimestamp() throws SQLException {
        long t = System.currentTimeMillis() - 1_000_000_000;

        TradeEntity e1 = new TradeEntity.Builder()
                .orderId(NostroUtils.randomUUID())
                .externalId("e1id")
                .timestamp(new Timestamp(t - 600_000))
                .document("{\"key\": \"value\"}")
                .build();
        repository.insert(e1);

        TradeEntity e2 = new TradeEntity.Builder()
                .orderId(NostroUtils.randomUUID())
                .externalId("e2id")
                .timestamp(new Timestamp(t - 300_000))
                .document("{\"key\": \"value\"}")
                .build();
        repository.insert(e2);

        TradeEntity e3 = new TradeEntity.Builder()
                .orderId(NostroUtils.randomUUID())
                .externalId("e3id")
                .timestamp(new Timestamp(t))
                .document("{\"key\": \"value\"}")
                .build();
        repository.insert(e3);

        List<TradeEntity> trades = repository.findAllByTimestamp(new Timestamp(t - 300_000), new Timestamp(t));
        assertThat(trades.size()).isEqualTo(2);
        assertThat(trades.get(0).getExternalId()).isEqualTo(e2.getExternalId());
        assertThat(trades.get(1).getExternalId()).isEqualTo(e3.getExternalId());

        trades = repository.findAllByTimestamp(new Timestamp(t - 600_000), new Timestamp(t - 600_000));
        assertThat(trades.size()).isEqualTo(1);
        assertThat(trades.get(0).getExternalId()).isEqualTo(e1.getExternalId());
    }
}