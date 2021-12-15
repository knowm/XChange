package nostro.xchange.binance.futures.sync;

import nostro.xchange.binance.sync.SyncTaskDocument;
import nostro.xchange.binance.utils.NostroBinanceFuturesDtoUtils;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.trade.OrderStatus;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesOrder;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesTrade;
import org.knowm.xchange.currency.CurrencyPair;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class BinanceFuturesInitSyncTaskTest {

    private BinanceFuturesSyncService syncService;

    @Before
    public void setUp() {
        syncService = mock(BinanceFuturesSyncService.class);
    }

    @Test
    public void testSync_no_orders() throws Exception {
        CurrencyPair instrument = CurrencyPair.BTC_USDT;
        given(syncService.getOpenOrders(instrument)).willReturn(Collections.emptyList());
        given(syncService.getLastOrder(instrument)).willReturn(null);

        SyncTaskDocument document = new BinanceFuturesInitSyncTask(syncService, instrument).call();

        assertThat(document).isNull();
        verify(syncService, times(1)).getOpenOrders(instrument);
        verify(syncService, times(1)).getLastOrder(instrument);
    }

    @Test
    public void testSync_no_trades() throws Exception {
        long orderId = 1;
        CurrencyPair pair = CurrencyPair.BTC_USDT;
        BinanceFuturesOrder futuresOrder = NostroBinanceFuturesDtoUtils.generateOrder(orderId, OrderStatus.NEW, BinanceAdapters.toSymbol(pair), Long.parseLong("1638264757254"), Long.parseLong("1638264757254"), null);

        given(syncService.getOpenOrders(pair)).willReturn(Collections.singletonList(futuresOrder));
        given(syncService.getLastOrder(pair)).willReturn(null);
        given(syncService.getFirstTrade(pair, futuresOrder.time)).willReturn(null);
        given(syncService.getFirstTrade(pair, null)).willReturn(null);

        SyncTaskDocument document = new BinanceFuturesInitSyncTask(syncService, pair).call();

        assertThat(document).isNull();
        verify(syncService, times(1)).getOpenOrders(pair);
        verify(syncService, times(0)).getLastOrder(pair);
        verify(syncService, times(1)).getFirstTrade(pair, futuresOrder.time);
        verify(syncService, times(1)).getFirstTrade(pair, null);
    }

    @Test
    public void testSync() throws Exception {
        long orderId = 1;
        long tradeId = 2;
        CurrencyPair pair = CurrencyPair.BTC_USDT;
        BinanceFuturesOrder futuresOrder = NostroBinanceFuturesDtoUtils.generateOrder(orderId, OrderStatus.NEW, BinanceAdapters.toSymbol(pair), Long.parseLong("1638264757254"), Long.parseLong("1638264757254"), null);
        BinanceFuturesTrade futuresTrade = NostroBinanceFuturesDtoUtils.generateTrade(tradeId, orderId, Long.valueOf("1569514978020"));
        given(syncService.getOpenOrders(pair)).willReturn(Collections.singletonList(futuresOrder));
        given(syncService.getLastOrder(pair)).willReturn(null);
        given(syncService.getFirstTrade(pair, futuresOrder.time)).willReturn(futuresTrade);
        given(syncService.getFirstTrade(pair, null)).willReturn(futuresTrade);

        SyncTaskDocument document = new BinanceFuturesInitSyncTask(syncService, pair).call();

        assertThat(document).isNotNull();
        assertThat(document.getOrderId()).isEqualTo(orderId);
        assertThat(document.getTradeId()).isEqualTo(tradeId);
        verify(syncService, times(1)).getOpenOrders(pair);
        verify(syncService, times(0)).getLastOrder(pair);
        verify(syncService, times(1)).getFirstTrade(pair, futuresOrder.time);
        verify(syncService, times(0)).getFirstTrade(pair, null);

    }
}