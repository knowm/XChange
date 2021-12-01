package nostro.xchange.binance.futures.sync;

import nostro.xchange.binance.DataSourceTest;
import nostro.xchange.binance.sync.SyncTaskDocument;
import nostro.xchange.binance.utils.NostroBinanceFuturesDTOUtils;
import nostro.xchange.binance.utils.NostroDBUtils;
import nostro.xchange.persistence.SyncTaskEntity;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroStreamingPublisher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.trade.OrderStatus;
import org.knowm.xchange.binance.futures.BinanceFuturesAdapter;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesOrder;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class BinanceFuturesSyncSubscriptionsTaskTest extends DataSourceTest {
    private TransactionFactory txFactory;
    private NostroStreamingPublisher publisher;
    private BinanceFuturesSyncService syncService;

    @Before
    public void setUp() throws Exception {
        txFactory = TransactionFactory.get("Binance Futures", "user0001");
        publisher = mock(NostroStreamingPublisher.class);
        syncService = mock(BinanceFuturesSyncService.class);

        given(syncService.getTXFactory()).willReturn(txFactory);
        given(syncService.getPublisher()).willReturn(publisher);
    }

    @After
    public void tearDown() throws Exception {
        NostroDBUtils.dropTable(TransactionFactory.getDataSource(), "account");
        NostroDBUtils.dropTable(TransactionFactory.getDataSource(), "trade$" + txFactory.getAccountId());
        NostroDBUtils.dropTable(TransactionFactory.getDataSource(), "order$" + txFactory.getAccountId());
    }

    @Test
    public void testSync_noSubscriptions() throws Exception {
        new BinanceFuturesSyncSubscriptionsTask(syncService, Collections.emptySet()).call();

        assertThat(txFactory.executeAndGet(tx -> tx.getSyncTaskRepository().findAllLatest()).size()).isEqualTo(0);
    }

    @Test
    public void testSync_fromInitTask_notSaved() throws Exception {
        long time = new Timestamp(new Date().getTime()).getTime();
        int orderId = 1;
        CurrencyPair currencyPair = CurrencyPair.BTC_USDT;
        BinanceFuturesOrder order = NostroBinanceFuturesDTOUtils.generateOrder(orderId, OrderStatus.NEW, BinanceAdapters.toSymbol(currencyPair), time, time, new BigDecimal(0));
        BinanceFuturesTrade trade = NostroBinanceFuturesDTOUtils.generateTrade(orderId, orderId, time);
        Instrument instrument = BinanceFuturesAdapter.adaptOrder(order).getInstrument();
        given(syncService.getOpenOrders(currencyPair)).willReturn(Collections.singletonList(order));
        given(syncService.getFirstTrade(currencyPair, time)).willReturn(trade);

        new BinanceFuturesSyncSubscriptionsTask(syncService, Collections.singleton(instrument.toString())).call();

        assertThat(txFactory.executeAndGet(tx -> tx.getSyncTaskRepository().findAllLatest()).size()).isEqualTo(0);
    }

    @Test
    public void testSync_fromInitTask() throws Exception {
        long time = new Timestamp(new Date().getTime()).getTime();
        long orderId = 2;
        CurrencyPair currencyPair = CurrencyPair.BTC_USDT;
        String symbol = currencyPair.toString();
        BinanceFuturesOrder order = NostroBinanceFuturesDTOUtils.generateOrder(orderId, OrderStatus.NEW, BinanceAdapters.toSymbol(currencyPair), time, time, new BigDecimal(0));
        BinanceFuturesTrade trade = NostroBinanceFuturesDTOUtils.generateTrade(orderId, orderId, time);
        BinanceFuturesTrade trade2 = NostroBinanceFuturesDTOUtils.generateTrade(orderId+1, orderId, time);
        given(syncService.getOpenOrders(currencyPair)).willReturn(Collections.singletonList(order));
        given(syncService.getFirstTrade(currencyPair, time)).willReturn(trade);
        given(syncService.getTrades(eq(currencyPair), eq(orderId), anyInt())).willReturn(Collections.singletonList(trade2));
        given(syncService.getOrder(currencyPair, order.orderId)).willReturn(order);

        new BinanceFuturesSyncSubscriptionsTask(syncService, Collections.singleton(symbol)).call();

        List<SyncTaskEntity> syncTaskEntities = txFactory.executeAndGet(tx -> tx.getSyncTaskRepository().findAllLatest());
        assertThat(syncTaskEntities.size()).isEqualTo(1);
        SyncTaskDocument document = SyncTaskDocument.read(syncTaskEntities.get(0).getDocument());
        assertThat(document.getTradeId()).isEqualTo(orderId + 2);
        assertThat(document.getOrderId()).isEqualTo(orderId);
    }

    @Test
    public void testSync_fromDBTask() throws Exception {
        long time = new Timestamp(new Date().getTime()).getTime();
        long orderId = 3;
        CurrencyPair currencyPair = CurrencyPair.BTC_USDT;
        BinanceFuturesOrder order = NostroBinanceFuturesDTOUtils.generateOrder(orderId, OrderStatus.NEW, BinanceAdapters.toSymbol(currencyPair), time, time, new BigDecimal(0));
        BinanceFuturesTrade trade = NostroBinanceFuturesDTOUtils.generateTrade(orderId, orderId, time);
        BinanceFuturesTrade trade2 = NostroBinanceFuturesDTOUtils.generateTrade(orderId+1, orderId, time);
        given(syncService.getOpenOrders(currencyPair)).willReturn(Collections.singletonList(order));
        given(syncService.getFirstTrade(currencyPair, time)).willReturn(trade);
        given(syncService.getTrades(eq(currencyPair), eq(orderId), anyInt())).willReturn(Collections.singletonList(trade2));
        given(syncService.getOrder(currencyPair, order.orderId)).willReturn(order);

        // insert sync task
        String symbol = currencyPair.toString();
        txFactory.execute(tx -> tx.getSyncTaskRepository().insert(symbol, new Timestamp(time-10), SyncTaskDocument.write(new SyncTaskDocument(orderId, orderId))));

        // sync
        new BinanceFuturesSyncSubscriptionsTask(syncService, Collections.singleton(symbol)).call();

        List<SyncTaskEntity> syncTaskEntities = txFactory.executeAndGet(tx -> tx.getSyncTaskRepository().findAllLatest());
        assertThat(syncTaskEntities.size()).isEqualTo(1);
        SyncTaskDocument document = SyncTaskDocument.read(syncTaskEntities.get(0).getDocument());
        assertThat(document.getTradeId()).isEqualTo(orderId + 2);
        assertThat(document.getOrderId()).isEqualTo(orderId);
    }


    @Test
    public void tryThrowWithIllegalSymbol() {
        assertThatThrownBy(() -> new BinanceFuturesSyncSubscriptionsTask(syncService, Collections.singleton("lol")).call()).isInstanceOf(IllegalArgumentException.class);
    }
}