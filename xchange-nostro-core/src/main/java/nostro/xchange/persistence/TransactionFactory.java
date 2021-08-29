package nostro.xchange.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransactionFactory {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionFactory.class); 
    
    private static final HikariDataSource HDS;
    private static final int MAXIMUM_POOL_SIZE = 20;

    static {
        String url = System.getProperty("xchange.db.url");
        String user = System.getProperty("xchange.db.user");
        String password = System.getProperty("xchange.db.password");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HDS = new HikariDataSource(config);

        Flyway flyway = Flyway.configure().dataSource(HDS).load();
        flyway.migrate();
    }

    public static DataSource getDataSource() {
        return HDS;
    }
    
    private static final String CALL_INSERT_ACCOUNT_SQL = "CALL insert_account(?,?,?)";
    
    private final String xchange;
    private final String accountId;
    private final String postfix;

    private TransactionFactory(String xchange, String accountId) {
        this.xchange = xchange;
        this.accountId = accountId;
        this.postfix = xchange + "$" + accountId;
    }
    
    public static TransactionFactory get(String xchange, String accountId) throws SQLException {
        LOG.debug("Creating tx factory for Account({}, {})", xchange, accountId);
        
        if (xchange == null || xchange.isEmpty()) {
            throw new IllegalArgumentException("\"xchange\" cannot be null or empty");
        }
        if (accountId == null || accountId.isEmpty()) {
            throw new IllegalArgumentException("\"accountId\" cannot be null or empty");
        }
        
        TransactionFactory factory = new TransactionFactory(xchange, accountId);
        factory.insertAccount();
        return factory;
    }

    public void execute(TransactionConsumer consumer) {
        executeAndGet(tx -> {
            consumer.accept(tx);
            return null;
        });
    }

    public <R> R executeAndGet(TransactionFunction<R> function) {
        try {
            return new Transaction(HDS, postfix).execute(function);
        } catch (Exception e) {
            throw (e instanceof RuntimeException) ? (RuntimeException) e : new RuntimeException(e);
        }
    }
    
    private void insertAccount() throws SQLException {
        try(Connection connection = getDataSource().getConnection();
            PreparedStatement stmt = connection.prepareCall(CALL_INSERT_ACCOUNT_SQL)) {
            stmt.setString(1, xchange);
            stmt.setString(2, accountId);
            stmt.setString(3, postfix);
            stmt.execute();
        } catch (SQLException e) {
            LOG.error("Error while inserting Account({}, {}): {}", xchange, accountId, e.getMessage());
            throw e;
        }
        LOG.info("Inserted Account({}, {})", xchange, accountId);
    }
}
