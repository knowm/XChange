package nostro.xchange.persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class AccountRepositoryTest extends DataSourceTest {

    private Connection connection;
    private AccountRepository repository;
    private int accountId;

    @Before
    public void setUp() throws SQLException {
        TransactionFactory.get("binance", "user0002");
        accountId = TransactionFactory.selectAccountId("binance", "user0002");
        
        connection = TransactionFactory.getDataSource().getConnection();
        repository = new AccountRepository(connection, accountId);
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void selectAndUpdate() throws SQLException {
        String document = repository.get();
        assertThat(document).isNull();

        repository.update("{\"subscriptions\": \"BTC/USD\"}");

        document = repository.lock();
        assertThat(document).isEqualTo("{\"subscriptions\": \"BTC/USD\"}");
    }
    
    @Test
    public void lock() throws SQLException {
        Connection txConnection = TransactionFactory.getDataSource().getConnection();
        Connection failConnection = TransactionFactory.getDataSource().getConnection();
        Connection okConnection = TransactionFactory.getDataSource().getConnection();

        txConnection.setAutoCommit(false);
        failConnection.setAutoCommit(false);
        okConnection.setAutoCommit(false);

        AccountRepository txRepository = new AccountRepository(txConnection, accountId);

        String sql = "SELECT * FROM account WHERE id='" + accountId + "' FOR UPDATE NOWAIT";

        txRepository.lock();
        assertThatCode(() -> failConnection.prepareStatement(sql).execute()).hasMessageContaining("could not obtain lock");

        txConnection.commit();
        assertThatCode(() -> okConnection.prepareStatement(sql).execute()).doesNotThrowAnyException();

        txConnection.close();
        failConnection.close();
        okConnection.close();
    }
}