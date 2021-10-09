package nostro.xchange.persistence;

import nostro.xchange.utils.NostroUtils;
import org.junit.Test;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class TransactionFactoryTest extends DataSourceTest {

    @Test
    public void getFactory() throws SQLException {
        String xchange = "binance";
        String accountId = "user0001";
        int nextId = getNextId();

        assertThat(selectAccount(accountId)).isFalse();
        assertThatCode(() -> selectFrom("balance$" + nextId)).hasMessageContaining("does not exist");
        assertThatCode(() -> selectFrom("order$" + nextId)).hasMessageContaining("does not exist");
        assertThatCode(() -> selectFrom("trade$" + nextId)).hasMessageContaining("does not exist");
        assertThatCode(() -> selectFrom("order_task$" + nextId)).hasMessageContaining("does not exist");
        assertThatCode(() -> selectFrom("sync_task$" + nextId)).hasMessageContaining("does not exist");

        TransactionFactory.get(xchange, accountId);

        assertThat(selectAccount(accountId)).isTrue();
        assertThatCode(() -> selectFrom("balance$" + nextId)).doesNotThrowAnyException();
        assertThatCode(() -> selectFrom("order$" + nextId)).doesNotThrowAnyException();
        assertThatCode(() -> selectFrom("trade$" + nextId)).doesNotThrowAnyException();
        assertThatCode(() -> selectFrom("order_task$" + nextId)).doesNotThrowAnyException();
        assertThatCode(() -> selectFrom("sync_task$" + nextId)).doesNotThrowAnyException();

        TransactionFactory.get(xchange, accountId);
    }
    
    private int getNextId() throws SQLException {
        try(Connection connection = TransactionFactory.getDataSource().getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT NEXTVAL(PG_GET_SERIAL_SEQUENCE('account', 'id'))")) {
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) + 1;
        }
    }

    private boolean selectAccount(String externalId) throws SQLException {
        try(Connection connection = TransactionFactory.getDataSource().getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM account WHERE external_id = ?")) {
            stmt.setString(1, externalId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) != 0; 
        }
    }
    
    private void selectFrom(String table) throws SQLException {
        try(Connection connection = TransactionFactory.getDataSource().getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM " + table)) {
            stmt.execute();
        }
    }

    @Test
    public void getFactoryEmptyParams() {
        assertThatThrownBy(() ->  TransactionFactory.get(null, "user0001"))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() ->  TransactionFactory.get("", "user0001"))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() ->  TransactionFactory.get("binance", null))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() ->  TransactionFactory.get("binance", ""))
                .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void commitTransaction() throws Exception {
        TransactionFactory factory = TransactionFactory.get("x", "aid");
        String postfix = String.valueOf(TransactionFactory.selectAccountId("x", "aid"));

        String orderId = NostroUtils.randomUUID();
        factory.execute(tx -> tx.getTradeRepository().insert(orderId, "exid1", new Timestamp(0), "{\"key\":\"value\"}"));

        
        try(Connection connection = TransactionFactory.getDataSource().getConnection()) {
            List<TradeEntity> trades = new TradeRepository(connection, postfix).findAllByOrderId(orderId);
            assertThat(trades.size()).isEqualTo(1);
            assertThat(trades.get(0).getOrderId()).isEqualTo(orderId);
        }
    }

    @Test
    public void rollBackTransaction() throws SQLException {
        TransactionFactory factory = TransactionFactory.get("x", "aid");
        String postfix = String.valueOf(TransactionFactory.selectAccountId("x", "aid"));

        String orderId = NostroUtils.randomUUID();
        assertThatThrownBy(() ->
                factory.execute(tx -> {
                    tx.getTradeRepository().insert(orderId, "exid2", new Timestamp(0), "{\"key\":\"value\"}");
                    throw new ArithmeticException("Expected exception");
                })).isInstanceOf(ArithmeticException.class).hasMessage("Expected exception");

        try (Connection connection = TransactionFactory.getDataSource().getConnection()) {
            List<TradeEntity> trades = new TradeRepository(connection, postfix).findAllByOrderId(orderId);
            assertThat(trades.size()).isEqualTo(0);
        }
    }
}