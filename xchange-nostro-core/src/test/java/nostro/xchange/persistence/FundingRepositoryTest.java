package nostro.xchange.persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FundingRepositoryTest extends DataSourceTest {

    private Connection connection;
    private FundingRepository repository;
    
    @Before
    public void setUp() throws SQLException {
        connection = TransactionFactory.getDataSource().getConnection();
        repository = new FundingRepository(connection, "");
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
    }
    
    @Test
    public void insertFindAllByTimestamp() throws SQLException {
        long t = System.currentTimeMillis() - 1_000_000_000;

        FundingEntity e1 = new FundingEntity.Builder()
                .externalId("e1id")
                .type("FUNDING_PAYMENT")
                .timestamp(new Timestamp(t - 600_000))
                .document("{\"key\": \"value\"}")
                .build();
        repository.insert(e1);

        FundingEntity e2 = new FundingEntity.Builder()
                .externalId("e2id")
                .type("FUNDING_PAYMENT")
                .timestamp(new Timestamp(t - 300_000))
                .document("{\"key\": \"value\"}")
                .build();
        repository.insert(e2);

        FundingEntity e3 = new FundingEntity.Builder()
                .externalId("e3id")
                .type("FUNDING_PAYMENT")
                .timestamp(new Timestamp(t))
                .document("{\"key\": \"value\"}")
                .build();
        repository.insert(e3);

        List<FundingEntity> records = repository.findAllByTimestamp(new Timestamp(t - 300_000), new Timestamp(t));
        assertThat(records.size()).isEqualTo(2);
        assertThat(records.get(0).getExternalId()).isEqualTo(e2.getExternalId());
        assertThat(records.get(1).getExternalId()).isEqualTo(e3.getExternalId());

        records = repository.findAllByTimestamp(new Timestamp(t - 600_000), new Timestamp(t - 600_000));
        assertThat(records.size()).isEqualTo(1);
        assertThat(records.get(0).getExternalId()).isEqualTo(e1.getExternalId());
    }
}