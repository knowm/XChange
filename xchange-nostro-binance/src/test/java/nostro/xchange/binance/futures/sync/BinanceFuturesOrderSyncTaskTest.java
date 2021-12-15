package nostro.xchange.binance.futures.sync;

import nostro.xchange.binance.DataSourceTest;
import nostro.xchange.binance.futures.NostroBinanceFuturesUtils;
import nostro.xchange.binance.utils.NostroDBUtils;
import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroStreamingPublisher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.binance.dto.trade.OrderStatus;
import org.knowm.xchange.binance.futures.BinanceFuturesAdapter;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.mockito.internal.verification.VerificationModeFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static nostro.xchange.binance.utils.NostroBinanceFuturesDtoUtils.generateOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BinanceFuturesOrderSyncTaskTest extends DataSourceTest {

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

        BinanceFuturesOrderSyncTask.LIMIT = 100;
    }

    @After
    public void tearDown() throws Exception {
        NostroDBUtils.dropTable(TransactionFactory.getDataSource(), "order$" + txFactory.getAccountId());
    }

    @Test
    public void testSync_noOrders() throws Exception {
        final int fromId = 0;
        int limit = 1;
        BinanceFuturesOrderSyncTask.LIMIT = limit;
        CurrencyPair pair = CurrencyPair.BTC_USDT;
        given(syncService.getOrders(any(), anyLong(), anyInt())).willReturn(Collections.emptyList());
        // when
        BinanceFuturesOrderSyncTask task = new BinanceFuturesOrderSyncTask(syncService, pair, fromId);
        Long result = task.call();
        // then
        assertThat(result).isEqualTo(fromId);
        verify(syncService, VerificationModeFactory.times(1)).getOrders(pair, fromId, limit);
        verify(syncService, VerificationModeFactory.times(0)).getOrders(pair, fromId+1, limit);
    }

    @Test
    public void testSync_1Page() throws Exception {
        int fromId = 20;
        CurrencyPair pair = CurrencyPair.BTC_USDT;
        int limit = 1;
        BinanceFuturesOrderSyncTask.LIMIT = limit;
        BinanceFuturesOrder order1 = generateOrder(fromId+1, OrderStatus.NEW, "BTCUSDT", Long.parseLong("1638264757254"), null, null);
        given(syncService.getOrders(pair, fromId, limit)).willReturn(Collections.singletonList(order1));
        given(syncService.getOrders(pair, fromId+1, limit)).willReturn(Collections.emptyList());

        // when
        BinanceFuturesOrderSyncTask task = new BinanceFuturesOrderSyncTask(syncService, pair, fromId);
        Long result = task.call();

        // then
        assertThat(result).isEqualTo(order1.orderId+1);
        verify(syncService, VerificationModeFactory.times(1)).getOrders(pair, fromId, limit);
        verify(syncService, VerificationModeFactory.times(1)).getOrders(pair, fromId+1, limit);
    }

    @Test
    public void testSync_1Page2Orders() throws Exception {
        int fromId = 30;
        CurrencyPair pair = CurrencyPair.BTC_USDT;
        int limit = 10;
        BinanceFuturesOrderSyncTask.LIMIT = limit;
        BinanceFuturesOrder order1 = generateOrder(fromId+1, OrderStatus.NEW, "BTCUSDT", Long.parseLong("1638264757254"), null, null);
        BinanceFuturesOrder order2 = generateOrder(fromId+2, OrderStatus.NEW, "BTCUSDT", Long.parseLong("1638264757254"), null, null);
        given(syncService.getOrders(pair, fromId, limit)).willReturn(Arrays.asList(order1, order2));

        // when
        BinanceFuturesOrderSyncTask task = new BinanceFuturesOrderSyncTask(syncService, pair, fromId);
        Long result = task.call();

        // then
        assertThat(result).isEqualTo(order2.orderId+1);
        verify(syncService, VerificationModeFactory.times(1)).getOrders(pair, fromId, limit);
        verify(syncService, VerificationModeFactory.times(0)).getOrders(pair, fromId+2, limit);
        verify(syncService, VerificationModeFactory.times(0)).getOrders(pair, fromId+1, limit);
    }

    @Test
    public void testSync_2Pages() throws Exception {
        int fromId = 40;
        CurrencyPair pair = CurrencyPair.BTC_USDT;
        int limit = 1;
        BinanceFuturesOrderSyncTask.LIMIT = limit;
        BinanceFuturesOrder order1 = generateOrder(fromId+1, OrderStatus.NEW, "BTCUSDT", Long.parseLong("1638264757254"), null, null);
        BinanceFuturesOrder order2 = generateOrder(fromId+2, OrderStatus.NEW, "BTCUSDT", Long.parseLong("1638264757254"), null, null);
        given(syncService.getOrders(pair, fromId, limit)).willReturn(Collections.singletonList(order1));
        given(syncService.getOrders(pair, fromId+1, limit)).willReturn(Collections.singletonList(order2));
        given(syncService.getOrders(pair, fromId+2, limit)).willReturn(Collections.emptyList());

        // when
        BinanceFuturesOrderSyncTask task = new BinanceFuturesOrderSyncTask(syncService, pair, fromId);
        Long result = task.call();

        // then
        assertThat(result).isEqualTo(order2.orderId+1);
        verify(syncService, VerificationModeFactory.times(1)).getOrders(pair, fromId, limit);
        verify(syncService, VerificationModeFactory.times(1)).getOrders(pair, fromId+1, limit);
        verify(syncService, VerificationModeFactory.times(1)).getOrders(pair, fromId+2, limit);
    }

    @Test
    public void testVerifyPublisher() throws Exception {
        int fromId = 50;
        CurrencyPair pair = CurrencyPair.BTC_USDT;
        BinanceFuturesOrder order1 = generateOrder(fromId+1, OrderStatus.NEW, "BTCUSDT", Long.parseLong("1638264757254"), null, new BigDecimal("0.001"));
        given(syncService.getOrders(pair, fromId, BinanceFuturesOrderSyncTask.LIMIT)).willReturn(Collections.singletonList(order1));
        given(syncService.getOrders(pair, fromId+1, BinanceFuturesOrderSyncTask.LIMIT)).willReturn(Collections.emptyList());

        // when
        new BinanceFuturesOrderSyncTask(syncService, pair, fromId).call();

        // then
        verify(publisher, VerificationModeFactory.times(1)).publish(BinanceFuturesAdapter.adaptOrder(order1));

        new BinanceFuturesOrderSyncTask(syncService, pair, fromId).call();
        verify(publisher, VerificationModeFactory.noMoreInteractions()).publish(BinanceFuturesAdapter.adaptOrder(order1));
    }

    @Test
    public void testInsertOrder() throws Exception {
        int fromId = 60;
        CurrencyPair pair = CurrencyPair.BTC_USDT;
        BinanceFuturesOrder order1 = generateOrder(fromId+1, OrderStatus.NEW, "BTCUSDT", Long.parseLong("1638264757254"), null, null);
        given(syncService.getOrders(pair, fromId, BinanceFuturesOrderSyncTask.LIMIT)).willReturn(Collections.singletonList(order1));
        given(syncService.getOrders(pair, fromId+1, BinanceFuturesOrderSyncTask.LIMIT)).willReturn(Collections.emptyList());
        assertThat(txFactory.executeAndGet(tx -> tx.getOrderRepository().findById(String.valueOf(order1.clientOrderId))).isPresent()).isFalse();

        // when
        new BinanceFuturesOrderSyncTask(syncService, pair, fromId).call();

        // then
        Optional<OrderEntity> orderEntity = txFactory.executeAndGet(tx -> tx.getOrderRepository().findById(String.valueOf(order1.clientOrderId)));
        assertThat(orderEntity.isPresent()).isTrue();
        assertThat(orderEntity.get().isTerminal()).isFalse();
    }

    @Test
    public void testUpdateOrder() throws Exception {
        int fromId = 70;
        CurrencyPair pair = CurrencyPair.BTC_USDT;
        BinanceFuturesOrder order = generateOrder(fromId+1, OrderStatus.NEW, "BTCUSDT", Long.parseLong("1638264757254"), null, null);
        BinanceFuturesOrder orderModified = generateOrder(fromId+1, OrderStatus.FILLED, "BTCUSDT", Long.parseLong("1638264757254"), null, null);
        given(syncService.getOrders(pair, fromId, BinanceFuturesOrderSyncTask.LIMIT)).willReturn(Collections.singletonList(orderModified));
        given(syncService.getOrders(pair, fromId+1, BinanceFuturesOrderSyncTask.LIMIT)).willReturn(Collections.emptyList());

        // when
        txFactory.execute(tx -> tx.getOrderRepository().insert(NostroBinanceFuturesUtils.toEntity(order)));
        new BinanceFuturesOrderSyncTask(syncService, pair, fromId).call();

        // then
        Optional<OrderEntity> orderEntity = txFactory.executeAndGet(tx -> tx.getOrderRepository().findById(String.valueOf(order.clientOrderId)));
        assertThat(orderEntity.isPresent()).isTrue();
        assertThat(orderEntity.get().isTerminal()).isTrue();
    }

}
