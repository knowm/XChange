package nostro.xchange.binance.sync;

import nostro.xchange.binance.DataSourceTest;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroStreamingPublisher;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.binance.futures.service.BinanceFuturesAccountService;
import org.knowm.xchange.binance.futures.service.BinanceFuturesTradeService;

import java.util.List;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class BinanceSyncServiceTest extends DataSourceTest {
    public static final int SYNC_DELAY = 1000;
    private TransactionFactory txFactory;
    private NostroStreamingPublisher publisher;
    private BinanceFuturesAccountService accountService;
    private BinanceFuturesTradeService tradeService;

    @Before
    public void setUp() throws Exception {
        // not able to mock currently...
        txFactory = TransactionFactory.get("Binance Futures", "user0001");
        publisher = mock(NostroStreamingPublisher.class);
        accountService = mock(BinanceFuturesAccountService.class);
        tradeService = mock(BinanceFuturesTradeService.class);
    }

    @Test
    public void testGenerateTasks() {
        BinanceSyncService service = new BinanceSyncService(txFactory, publisher, accountService, tradeService, SYNC_DELAY);

        List<Callable<?>> tasks = service.generateTasks();
        assertThat(tasks.size()).isEqualTo(1);
        assertThat(tasks.get(0)).isInstanceOf(SyncAllTask.class);
    }

}