package nostro.xchange.persistence;

import nostro.xchange.utils.NostroUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTaskRepositoryTest  extends DataSourceTest {

    private Connection connection;
    private OrderTaskRepository repository;

    @Before
    public void setUp() throws SQLException {
        connection = TransactionFactory.getDataSource().getConnection();
        repository = new OrderTaskRepository(connection, "");
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void insertAndFind() throws SQLException {
        String orderId = NostroUtils.randomUUID();
        String type = "T";
        String document = "{\"error\": 503}";
        repository.insert(orderId, type, false, document);

        Optional<OrderTaskEntity> e = repository.find(orderId, type);
        assertThat(e.isPresent()).isTrue();
        assertThat(e.get().getOrderId()).isEqualTo(orderId);
        assertThat(e.get().getType()).isEqualTo(type);
        assertThat(e.get().isFinished()).isFalse();
        assertThat(e.get().getDocument()).isEqualTo(document);
    }

    @Test
    public void update() throws SQLException {
        String orderId = NostroUtils.randomUUID();
        String type = "T";
        String document = "{\"error\": 503}";
        repository.insert(orderId, type, false, document);

        Optional<OrderTaskEntity> e = repository.find(orderId, type);
        assertThat(e.isPresent()).isTrue();
        assertThat(e.get().isFinished()).isFalse();
        assertThat(e.get().getDocument()).isEqualTo(document);

        document = "{\"error\": 0}";
        repository.update(orderId, type, true, document);

        e = repository.find(orderId, type);
        assertThat(e.isPresent()).isTrue();
        assertThat(e.get().isFinished()).isTrue();
        assertThat(e.get().getDocument()).isEqualTo(document);
    }

    @Test
    public void findActiveByType() throws SQLException {
        String typeCancel = "CANCEL";
        String typeTrail = "TRAIL";
        
        repository.insert("0000", typeCancel, false, "{\"e\": 0}");
        repository.insert("0001", typeCancel, false, "{\"e\": 1}");
        repository.insert("0002", typeCancel, true, "{\"e\": 2}");

        repository.insert("0003", typeTrail, false, "{\"p\": 3}");
        repository.insert("0004", typeTrail, true, "{\"p\": 4}");

        List<OrderTaskEntity> cancel = repository.findActiveByType(typeCancel);
        assertThat(cancel.size()).isEqualTo(2);
        assertThat(cancel.get(0).getOrderId()).isEqualTo("0000");
        assertThat(cancel.get(1).getOrderId()).isEqualTo("0001");

        List<OrderTaskEntity> trail = repository.findActiveByType(typeTrail);
        assertThat(trail.size()).isEqualTo(1);
        assertThat(trail.get(0).getOrderId()).isEqualTo("0003");
    }
}