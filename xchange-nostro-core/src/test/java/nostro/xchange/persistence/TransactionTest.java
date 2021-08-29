package nostro.xchange.persistence;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.*;

public class TransactionTest extends DataSourceTest {
    
    private Transaction tx;

    @Before
    public void setUp() {
        tx = new Transaction(TransactionFactory.getDataSource(), "");
    }

    @Test
    public void outsideTx() {
        assertThatThrownBy(() -> tx.getOrderRepository()).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> tx.getTradeRepository()).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> tx.getTimestamp()).isInstanceOf(IllegalStateException.class);
        
        assertThatCode(() -> tx.execute(tx0 -> {
            tx0.getOrderRepository();
            tx0.getTradeRepository();
            return tx0.getTimestamp();
        })).doesNotThrowAnyException();
    }
    
    @Test
    public void getTimestamp() throws Exception {
        Timestamp ts = tx.execute(Transaction::getTimestamp);
        assertThat(ts).isNotNull();
    }

}