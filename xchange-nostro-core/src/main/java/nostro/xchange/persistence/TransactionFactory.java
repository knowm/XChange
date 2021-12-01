package nostro.xchange.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;

public class TransactionFactory {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionFactory.class); 
    
    private static final HikariDataSource HDS;
    private static final int MAXIMUM_POOL_SIZE = 20;

    static {
        String url = System.getProperty("xchange.db.url");
        String user = System.getProperty("xchange.db.user");
        String password = System.getProperty("xchange.db.password");
        String driver = System.getProperty("xchange.db.driver");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        if (driver != null) {
            config.setDriverClassName(driver);
        }
        config.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HDS = new HikariDataSource(config);

        Flyway flyway = Flyway.configure().dataSource(HDS).baselineOnMigrate(true).load();
        flyway.migrate();
    }

    public static DataSource getDataSource() {
        return HDS;
    }
    
    private static final String CALL_INSERT_ACCOUNT_SQL = "CALL insert_account(?,?)";
    private static final String SELECT_ACCOUNT_ID_SQL = "SELECT id FROM account WHERE xchange = ? AND external_id = ?";
    
    private final int accountId;

    private TransactionFactory(int accountId) {
        this.accountId = accountId;
    }

    public int getAccountId() {
        return accountId;
    }

    public static TransactionFactory get(String xchange, String externalId) throws SQLException {
        LOG.debug("Creating tx factory for Account({}, {})", xchange, externalId);
        
        if (xchange == null || xchange.isEmpty()) {
            throw new IllegalArgumentException("\"xchange\" cannot be null or empty");
        }
        if (externalId == null || externalId.isEmpty()) {
            throw new IllegalArgumentException("\"accountId\" cannot be null or empty");
        }
        insertAccount(xchange, externalId);
        int accountId = selectAccountId(xchange, externalId);
        return new TransactionFactory(accountId);
    }

    public void execute(TransactionConsumer consumer) {
        executeAndGet(tx -> {
            consumer.accept(tx);
            return null;
        });
    }

    public <R> R executeAndGet(TransactionFunction<R> function) {
        try {
            return new Transaction(HDS, accountId).execute(function);
        } catch (Exception e) {
            throw (e instanceof RuntimeException) ? (RuntimeException) e : new RuntimeException(e);
        }
    }
    
    private static void insertAccount(String xchange, String externalId) throws SQLException {
        try(Connection connection = getDataSource().getConnection();
            PreparedStatement stmt = connection.prepareCall(CALL_INSERT_ACCOUNT_SQL)) {
            stmt.setString(1, xchange);
            stmt.setString(2, externalId);
            stmt.execute();
        } catch (SQLException e) {
            LOG.error("Error while inserting Account({}, {}): {}", xchange, externalId, e.getMessage());
            throw e;
        }
        LOG.info("Inserted Account({}, {})", xchange, externalId);
    }

    static int selectAccountId(String xchange, String externalId) throws SQLException {
        int accountId;
        try(Connection connection = getDataSource().getConnection();
            PreparedStatement stmt = connection.prepareCall(SELECT_ACCOUNT_ID_SQL)) {
            stmt.setString(1, xchange);
            stmt.setString(2, externalId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            accountId = rs.getInt(1);
            
        } catch (SQLException e) {
            LOG.error("Error while getting id for Account({}, {}): {}", xchange, externalId, e.getMessage());
            throw e;
        }
        LOG.info("Got id={} for Account({}, {})", accountId, xchange, externalId);
        return accountId;
    }
}
