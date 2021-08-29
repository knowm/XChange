package nostro.xchange.persistence;

import nostro.xchange.utils.NostroUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class TransactionFactoryTest extends DataSourceTest {

    @Test
    public void getFactory() throws SQLException {
        String xchange = "binance";
        String accountId = "user0001";

        assertThat(selectAccount(accountId)).isFalse();
        assertThatCode(() -> selectFrom("order$binance$user0001")).hasMessageContaining("does not exist");
        assertThatCode(() -> selectFrom("trade$binance$user0001")).hasMessageContaining("does not exist");

        TransactionFactory.get(xchange, accountId);

        assertThat(selectAccount(accountId)).isTrue();
        assertThatCode(() -> selectFrom("order$binance$user0001")).doesNotThrowAnyException();
        assertThatCode(() -> selectFrom("trade$binance$user0001")).doesNotThrowAnyException();

        TransactionFactory.get(xchange, accountId);
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
        
        String orderId = NostroUtils.randomUUID();
        factory.execute(tx -> tx.getOrderRepository().insert(orderId, "{\"key\":\"value\"}"));

        try(Connection connection = TransactionFactory.getDataSource().getConnection()) {
            Optional<OrderEntity> order = new OrderRepository(connection, "x$aid").findById(orderId);
            assertThat(order.isPresent()).isTrue();
        }
    }

    @Test
    public void rollBackTransaction() throws SQLException {
        TransactionFactory factory = TransactionFactory.get("x", "aid");

        String orderId = NostroUtils.randomUUID();
        assertThatThrownBy(() ->
                factory.execute(tx -> {
                    tx.getOrderRepository().insert(orderId, "{\"key\":\"value\"}");
                    throw new ArithmeticException("Expected exception");
                })).isInstanceOf(ArithmeticException.class).hasMessage("Expected exception");

        try (Connection connection = TransactionFactory.getDataSource().getConnection()) {
            Optional<OrderEntity> order = new OrderRepository(connection, "x$aid").findById(orderId);
            assertThat(order.isPresent()).isFalse();
        }
    }
}