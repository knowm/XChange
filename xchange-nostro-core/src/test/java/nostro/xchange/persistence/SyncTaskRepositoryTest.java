package nostro.xchange.persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SyncTaskRepositoryTest extends DataSourceTest {

    private Connection connection;
    private SyncTaskRepository repository;

    @Before
    public void setUp() throws SQLException {
        connection = TransactionFactory.getDataSource().getConnection();
        repository = new SyncTaskRepository(connection, "");
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void insertAndFind() throws SQLException {
        String symbol = "BTC/USDT";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String document = "{\"orders\": 5, \"trades\": 8}";
        repository.insert(symbol, timestamp, document);

        Optional<SyncTaskEntity> e = repository.findLatestBySymbol(symbol);
        assertThat(e.isPresent()).isTrue();
        assertThat(e.get().getSymbol()).isEqualTo(symbol);
        assertThat(e.get().getTimestamp()).isEqualTo(timestamp);
        assertThat(e.get().getDocument()).isEqualTo(document);
    }

    @Test
    public void findLatestBySymbolEmpty() throws SQLException {
        Optional<SyncTaskEntity> e = repository.findLatestBySymbol("ETH/USDT-perpetual");
        assertThat(e.isPresent()).isFalse();
    }

    @Test
    public void insertFindLatestBySymbol() throws SQLException {
        clearTable();
        long time = System.currentTimeMillis();
        
        String eth = "{\"balance\": 5}";
        repository.insert("ETH", new Timestamp(time - 5000), eth);

        eth = "{\"balance\": 5.5}";
        repository.insert("ETH", new Timestamp(time - 2000), eth);

        String btc = "{\"balance\": 2}";
        repository.insert("BTC", new Timestamp(time - 3000), btc);

        btc = "{\"balance\": 1.3}";
        repository.insert("BTC", new Timestamp(time - 1000), btc);

        Optional<SyncTaskEntity> e1 = repository.findLatestBySymbol("ETH");
        assertThat(e1.isPresent()).isTrue();
        assertThat(e1.get().getDocument()).isEqualTo(eth);

        Optional<SyncTaskEntity> e2 = repository.findLatestBySymbol("BTC");
        assertThat(e2.isPresent()).isTrue();
        assertThat(e2.get().getDocument()).isEqualTo(btc);
    }

    @Test
    public void findAll() throws SQLException {
        clearTable();
        long time = System.currentTimeMillis();

        String eth = "{\"balance\": 5}";
        repository.insert("ETH", new Timestamp(time - 5000), eth);

        eth = "{\"balance\": 5.5}";
        repository.insert("ETH", new Timestamp(time - 2000), eth);

        String btc = "{\"balance\": 2}";
        repository.insert("BTC", new Timestamp(time - 3000), btc);

        btc = "{\"balance\": 1.3}";
        repository.insert("BTC", new Timestamp(time - 1000), btc);

        String usd = "{\"balance\": 100}";
        repository.insert("USD", new Timestamp(time - 4000), usd);

        String sol = "{\"balance\": 20}";
        repository.insert("SOL", new Timestamp(time), sol);

        List<SyncTaskEntity> list = repository.findAllLatest();
        assertThat(list.size()).isEqualTo(4);

        SyncTaskEntity t = list.get(0);
        assertThat(t.getSymbol()).isEqualTo("USD");
        assertThat(t.getTimestamp()).isEqualTo(new Timestamp(time - 4000));
        assertThat(t.getDocument()).isEqualTo(usd);

        t = list.get(1);
        assertThat(t.getSymbol()).isEqualTo("ETH");
        assertThat(t.getTimestamp()).isEqualTo(new Timestamp(time - 2000));
        assertThat(t.getDocument()).isEqualTo(eth);

        t = list.get(2);
        assertThat(t.getSymbol()).isEqualTo("BTC");
        assertThat(t.getTimestamp()).isEqualTo(new Timestamp(time - 1000));
        assertThat(t.getDocument()).isEqualTo(btc);

        t = list.get(3);
        assertThat(t.getSymbol()).isEqualTo("SOL");
        assertThat(t.getTimestamp()).isEqualTo(new Timestamp(time));
        assertThat(t.getDocument()).isEqualTo(sol);
    }

    public void clearTable() throws SQLException {
        try (Connection connection = TransactionFactory.getDataSource().getConnection();
             PreparedStatement stmt = connection.prepareStatement("DELETE FROM sync_task$")) {
            stmt.execute();
        }
    }


}