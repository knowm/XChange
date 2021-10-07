package nostro.xchange.persistence;

import nostro.xchange.utils.NostroUtils;
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
import static org.assertj.core.api.Assertions.assertThatCode;

public class BalanceRepositoryTest extends DataSourceTest {

    private Connection connection;
    private BalanceRepository repository;

    @Before
    public void setUp() throws SQLException {
        connection = TransactionFactory.getDataSource().getConnection();
        repository = new BalanceRepository(connection, "");
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void insertFindByAsset() throws SQLException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String document = "{\"total\": 0.5}";
        repository.insert("BTC", timestamp, false, document);

        Optional<BalanceEntity> balance = repository.findLatestByAsset("BTC");
        assertThat(balance.isPresent()).isTrue();
        assertThat(balance.get().getAsset()).isEqualTo("BTC");
        assertThat(balance.get().getTimestamp()).isEqualTo(timestamp);
        assertThat(balance.get().getDocument()).isEqualTo(document);
    }

    @Test
    public void findByAssetEmpty() throws SQLException {
        Optional<BalanceEntity> balance = repository.findLatestByAsset(NostroUtils.randomUUID());
        assertThat(balance.isPresent()).isFalse();
    }

    @Test
    public void insertStale() throws SQLException {
        long time = System.currentTimeMillis();
        String asset = "ETH";

        String document2 = "{\"ETH\": 2}";
        repository.insert(asset, new Timestamp(time), false, document2);

        String document1 = "{\"ETH\": 1}";
        repository.insert(asset, new Timestamp(time - 1000), false, document1);

        Optional<BalanceEntity> balance = repository.findLatestByAsset(asset);
        assertThat(balance.isPresent()).isTrue();
        assertThat(balance.get().getAsset()).isEqualTo(asset);
        assertThat(balance.get().getTimestamp()).isEqualTo(new Timestamp(time));
        assertThat(balance.get().getDocument()).isEqualTo(document2);
    }

    @Test
    public void findAll() throws SQLException {
        clearTable();
        long time = System.currentTimeMillis();

        String eth = "{\"ETH\": 5}";
        repository.insert("ETH", new Timestamp(time - 5000), false, eth);

        eth = "{\"ETH\": 5.5}";
        repository.insert("ETH", new Timestamp(time - 2000), false, eth);

        String btc = "{\"BTC\": 2}";
        repository.insert("BTC", new Timestamp(time - 3000), false, btc);

        btc = "{\"BTC\": 1.3}";
        repository.insert("BTC", new Timestamp(time - 1000), false, btc);

        String usd = "{\"USD\": 100}";
        repository.insert("USD", new Timestamp(time - 4000), false, usd);

        String sol = "{\"SOL\": 0}";
        repository.insert("SOL", new Timestamp(time), true, sol);

        List<BalanceEntity> list = repository.findAllLatest();
        assertThat(list.size()).isEqualTo(3);

        BalanceEntity t = list.get(0);
        assertThat(t.getAsset()).isEqualTo("BTC");
        assertThat(t.getTimestamp()).isEqualTo(new Timestamp(time - 1000));
        assertThat(t.getDocument()).isEqualTo(btc);

        t = list.get(1);
        assertThat(t.getAsset()).isEqualTo("ETH");
        assertThat(t.getTimestamp()).isEqualTo(new Timestamp(time - 2000));
        assertThat(t.getDocument()).isEqualTo(eth);

        t = list.get(2);
        assertThat(t.getAsset()).isEqualTo("USD");
        assertThat(t.getTimestamp()).isEqualTo(new Timestamp(time - 4000));
        assertThat(t.getDocument()).isEqualTo(usd);
        
    }

    private void clearTable() throws SQLException {
        try (Connection connection = TransactionFactory.getDataSource().getConnection();
             PreparedStatement stmt = connection.prepareStatement("DELETE FROM balance$")) {
            stmt.execute();
        }
    }

    @Test
    public void lock() throws SQLException {
        Connection txConnection = TransactionFactory.getDataSource().getConnection();
        Connection failConnection = TransactionFactory.getDataSource().getConnection();
        Connection okConnection = TransactionFactory.getDataSource().getConnection();

        txConnection.setAutoCommit(false);
        failConnection.setAutoCommit(false);
        okConnection.setAutoCommit(false);

        failConnection.prepareStatement("SET LOCAL lock_timeout = '1ms'").execute();

        BalanceRepository txRepository = new BalanceRepository(txConnection, "");

        String sql = "INSERT INTO balance$(asset, timestamp, zero, document) VALUES ('A', NOW(), false, '{\"a\": 0}')";

        txRepository.lock();
        assertThatCode(() -> failConnection.prepareStatement(sql).execute()).hasMessageContaining("canceling statement due to lock timeout");

        txConnection.commit();
        assertThatCode(() -> okConnection.prepareStatement(sql).execute()).doesNotThrowAnyException();

        txConnection.close();
        failConnection.close();
        okConnection.close();
    }
}