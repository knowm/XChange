package nostro.xchange.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;

public class Transaction {
    private static final Logger LOG = LoggerFactory.getLogger(Transaction.class);

    private static final String SELECT_TIMESTAMP_SQL = "SELECT NOW()::TIMESTAMP";
    
    private final DataSource dataSource;
    private final int accountId;
    private final String postfix;
    private Connection connection;

    Transaction(DataSource dataSource, int accountId) {
        this.dataSource = dataSource;
        this.accountId = accountId;
        this.postfix = String.valueOf(accountId);
    }
    
    public Timestamp getTimestamp() throws SQLException {
        checkConnection();
        try(PreparedStatement stmt = connection.prepareStatement(SELECT_TIMESTAMP_SQL)) {
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getTimestamp(1);
        }
    }
    
    public AccountRepository getAccountRepository() {
        checkConnection();
        return new AccountRepository(connection, accountId);
    }

    public BalanceRepository getBalanceRepository() {
        checkConnection();
        return new BalanceRepository(connection, postfix);
    }
    
    public OrderRepository getOrderRepository() {
        checkConnection();
        return new OrderRepository(connection, postfix);
    }

    public TradeRepository getTradeRepository() {
        checkConnection();
        return new TradeRepository(connection, postfix);
    }

    public OrderTaskRepository getOrderTaskRepository() {
        checkConnection();
        return new OrderTaskRepository(connection, postfix);
    }

    public SyncTaskRepository getSyncTaskRepository() {
        checkConnection();
        return new SyncTaskRepository(connection, postfix);
    }

    private void checkConnection() {
        if (connection == null) {
            throw new IllegalStateException("Not available outside of transaction");
        }
    }

    <R> R execute(TransactionFunction<R> function) throws Exception {
        connection = dataSource.getConnection();
        R result;
        try {
            connection.setAutoCommit(false);
            result = function.apply(this);
            connection.commit();
        } catch (Exception e) {
            LOG.error("Error when running transaction, rolling back!", e);
            try {
                connection.rollback();
            } catch (Exception e2) {
                LOG.error("Error when rolling back transaction!", e2);
            }
            throw e;
        } finally {
            try {
                connection.close();
            } catch (Exception e3) {
                LOG.error("Error when closing transaction!", e3);
            } finally {
                connection = null;
            }
        }
        
        return result;
    }
}
