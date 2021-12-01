package nostro.xchange.sync;

import nostro.xchange.persistence.DataSourceTest;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroStreamingPublisher;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class SyncServiceTest extends DataSourceTest {

    public static final int SYNC_DELAY = 5;
    private TransactionFactory txFactory;
    private NostroStreamingPublisher publisher;

    @Before
    public void setUp() throws Exception {
        // not able to mock currently...
        txFactory = TransactionFactory.get("Binance Futures", "user0001");
        publisher = mock(NostroStreamingPublisher.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSimpleExecute_throws() throws Exception {
        Callable<Boolean> task = (Callable<Boolean>) mock(Callable.class);
        given(task.call()).willThrow(new RuntimeException());
        SimpleSyncService service = new SimpleSyncService(txFactory, publisher, SYNC_DELAY, task);
        assertThatThrownBy(service::init).isInstanceOf(RuntimeException.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSimpleExecute_not_throws() throws Exception {
        Callable<Boolean> task = (Callable<Boolean>) mock(Callable.class);
        given(task.call()).willReturn(true);
        SimpleSyncService service = new SimpleSyncService(txFactory, publisher, SYNC_DELAY, task);
        service.init();
        verify(task, times(1)).call();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testPeriodicSync() throws Exception {
        Callable<Boolean> task = (Callable<Boolean>) mock(Callable.class);
        given(task.call()).willReturn(true);
        int syncDelay = 3;
        SimpleSyncService service = new SimpleSyncService(txFactory, publisher, syncDelay, task);

        service.init();

        int timeout = 7;
        TimeUnit.SECONDS.sleep(timeout);

        verify(task, times((timeout/syncDelay) + 1)).call();
    }
}

class SimpleSyncService extends SyncService {
    private final Callable<Boolean> task;

    public SimpleSyncService(TransactionFactory txFactory, NostroStreamingPublisher publisher, long syncDelay, Callable<Boolean> task) {
        super(txFactory, publisher, syncDelay);
        this.task = task;
    }

    @Override
    public List<Callable<?>> generateTasks() {
        return Collections.singletonList(task);
    }
}