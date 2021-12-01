package nostro.xchange.binance.futures.sync;

import nostro.xchange.binance.DataSourceTest;
import nostro.xchange.binance.futures.NostroBinanceFuturesUtils;
import nostro.xchange.binance.utils.NostroBinanceFuturesDTOUtils;
import nostro.xchange.binance.utils.NostroDBUtils;
import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroStreamingPublisher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.trade.OrderStatus;
import org.knowm.xchange.binance.futures.BinanceFuturesAdapter;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class BinanceFuturesOpenOrderSyncTaskTest extends DataSourceTest {

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
        NostroDBUtils.dropTable(TransactionFactory.getDataSource(), "order$" + txFactory.getAccountId());
    }

    @Test
    public void testSyncOpenOrders_noOpenOrders() throws Exception {
        new BinanceFuturesOpenOrderSyncTask(syncService, CurrencyPair.BTC_USDT, 0).call();
        verify(publisher, never()).publish(any(Order.class));
        verify(syncService, never()).getOpenOrders(any(CurrencyPair.class));
    }

    @Test
    public void testSyncOpenOrders_noOpenOrders_terminal() throws Exception {
        // insert terminal order - verify that it is not synced
        BinanceFuturesOrder futuresOrder = NostroBinanceFuturesDTOUtils.generateOrder(1, OrderStatus.FILLED, BinanceAdapters.toSymbol(CurrencyPair.BTC_USDT), Long.parseLong("1638264757254"), null, null);
        txFactory.execute(tx -> tx.getOrderRepository().insert(NostroBinanceFuturesUtils.toEntity(futuresOrder)));
        new BinanceFuturesOpenOrderSyncTask(syncService, CurrencyPair.BTC_USDT, 0).call();
        verify(publisher, never()).publish(any(Order.class));
        verify(syncService, never()).getOpenOrders(any(CurrencyPair.class));
    }

    @Test
    public void testSyncOpenOrders_noOpenOrders_currency() throws Exception {
        BinanceFuturesOrder futuresOrder = NostroBinanceFuturesDTOUtils.generateOrder(2, OrderStatus.NEW, BinanceAdapters.toSymbol(CurrencyPair.BTC_USDT), Long.parseLong("1638264757254"), null, null);
        txFactory.execute(tx -> tx.getOrderRepository().insert(NostroBinanceFuturesUtils.toEntity(futuresOrder)));
        new BinanceFuturesOpenOrderSyncTask(syncService, CurrencyPair.ETH_USDT, 0).call();
        verify(publisher, never()).publish(any(Order.class));
        verify(syncService, never()).getOpenOrders(any(CurrencyPair.class));
    }

    @Test
    public void testSyncOpenOrders_sync_order_by_external_id_no_changes() throws Exception {
        // given
        long orderId = 3;
        long time = Long.parseLong("1638264757254");
        CurrencyPair pair = CurrencyPair.BTC_USDT;
        BinanceFuturesOrder futuresOrder = NostroBinanceFuturesDTOUtils.generateOrder(orderId, OrderStatus.NEW, BinanceAdapters.toSymbol(pair), time, time, new BigDecimal("0.001"));
        Instrument instrument = BinanceFuturesAdapter.adaptOrder(futuresOrder).getInstrument();
        OrderEntity orderEntity = NostroBinanceFuturesUtils.toEntity(futuresOrder);
        txFactory.execute(tx -> tx.getOrderRepository().insert(orderEntity));
        given(syncService.getOpenOrders(pair)).willReturn(Collections.singletonList(futuresOrder));

        // when
        new BinanceFuturesOpenOrderSyncTask(syncService, pair, orderId+1).call();

        // then
        List<OrderEntity> orderEntities = txFactory.executeAndGet(tx -> tx.getOrderRepository().findOpenOrders(instrument));
        assertThat(orderEntities.size()).isEqualTo(1);
        assertThat(orderEntities.get(0).getUpdated().getTime()).isEqualTo(time);
        verify(publisher, never()).publish(any(Order.class));
        verify(syncService, times(1)).getOpenOrders(pair);
        verify(syncService, never()).getOrder(any(CurrencyPair.class), anyLong());
        verify(syncService, never()).getOrder(any(CurrencyPair.class), anyString());
    }

    @Test
    public void testSyncOpenOrders_sync_order_by_external_id_became_terminal() throws Exception {
        // given
        long orderId = 4;
        Timestamp time = new Timestamp(new Date().getTime());
        CurrencyPair pair = CurrencyPair.BTC_USDT;
        BinanceFuturesOrder futuresOrder = NostroBinanceFuturesDTOUtils.generateOrder(orderId, OrderStatus.NEW, BinanceAdapters.toSymbol(pair), time.getTime() - 10, null, null);
        Instrument instrument = BinanceFuturesAdapter.adaptOrder(futuresOrder).getInstrument();
        OrderEntity orderEntity = NostroBinanceFuturesUtils.toEntity(futuresOrder);
        txFactory.execute(tx -> tx.getOrderRepository().insert(orderEntity));

        BinanceFuturesOrder futuresOrderUpd = NostroBinanceFuturesDTOUtils.generateOrder(orderId, OrderStatus.FILLED, BinanceAdapters.toSymbol(pair), time.getTime() - 10, time.getTime(), null);

        given(syncService.getOpenOrders(pair)).willReturn(Collections.emptyList());
        given(syncService.getOrder(pair, orderId)).willReturn(futuresOrderUpd);

        // when
        new BinanceFuturesOpenOrderSyncTask(syncService, pair, orderId+1).call();

        // then
        assertThat(txFactory.executeAndGet(tx -> tx.getOrderRepository().findOpenOrders(instrument)).size()).isEqualTo(0);
        Optional<OrderEntity> orderEntityUpd = txFactory.executeAndGet(tx -> tx.getOrderRepository().findByExternalId(String.valueOf(futuresOrderUpd.orderId)));
        assertThat(orderEntityUpd.isPresent()).isTrue();
        assertThat(orderEntityUpd.get().getUpdated()).isEqualTo(time);
        assertThat(orderEntityUpd.get().isTerminal()).isTrue();
        verify(publisher, times(1)).publish(BinanceFuturesAdapter.adaptOrder(futuresOrderUpd));
        verify(syncService, times(1)).getOpenOrders(pair);
        verify(syncService, times(1)).getOrder(pair, futuresOrderUpd.orderId);
        verify(syncService, never()).getOrder(any(CurrencyPair.class), anyString());
    }

    @Test
    public void testSyncOpenOrders_sync_order_by_client_order_id_became_terminal() throws Exception {
        // given
        long orderId = 5;
        Timestamp time = new Timestamp(new Date().getTime());
        CurrencyPair pair = CurrencyPair.BTC_USDT;
        BinanceFuturesOrder futuresOrder = NostroBinanceFuturesDTOUtils.generateOrder(orderId, OrderStatus.NEW, BinanceAdapters.toSymbol(pair), time.getTime() - 10, null, null);
        Instrument instrument = BinanceFuturesAdapter.adaptOrder(futuresOrder).getInstrument();
        OrderEntity orderEntity = NostroBinanceFuturesUtils.toEntity(futuresOrder);
        orderEntity.setExternalId(null);
        txFactory.execute(tx -> tx.getOrderRepository().insert(orderEntity));

        BinanceFuturesOrder futuresOrderUpd = NostroBinanceFuturesDTOUtils.generateOrder(orderId, OrderStatus.FILLED, BinanceAdapters.toSymbol(pair), time.getTime() - 10, time.getTime(), null);

        given(syncService.getOpenOrders(pair)).willReturn(Collections.emptyList());
        given(syncService.getOrder(pair, futuresOrder.clientOrderId)).willReturn(futuresOrderUpd);

        // when
        new BinanceFuturesOpenOrderSyncTask(syncService, pair, orderId+1).call();

        // then
        assertThat(txFactory.executeAndGet(tx -> tx.getOrderRepository().findOpenOrders(instrument)).size()).isEqualTo(0);
        Optional<OrderEntity> orderEntityUpd = txFactory.executeAndGet(tx -> tx.getOrderRepository().findById(futuresOrderUpd.clientOrderId));
        assertThat(orderEntityUpd.isPresent()).isTrue();
        assertThat(orderEntityUpd.get().getUpdated()).isEqualTo(time);
        assertThat(orderEntityUpd.get().isTerminal()).isTrue();
        verify(publisher, times(1)).publish(BinanceFuturesAdapter.adaptOrder(futuresOrderUpd));
        verify(syncService, times(1)).getOpenOrders(pair);
        verify(syncService, times(1)).getOrder(pair, futuresOrder.clientOrderId);
        verify(syncService, never()).getOrder(pair, futuresOrderUpd.orderId);
    }

    @Test
    public void testMarkRejected() throws Exception {
        long orderId = 6;
        Timestamp timestamp = new Timestamp(new Date().getTime());
        CurrencyPair pair = CurrencyPair.BTC_USDT;
        BinanceFuturesOrder futuresOrder = NostroBinanceFuturesDTOUtils.generateOrder(orderId, OrderStatus.NEW, BinanceAdapters.toSymbol(pair), timestamp.getTime(), null, null);
        OrderEntity orderEntity = NostroBinanceFuturesUtils.toEntity(futuresOrder);
        orderEntity.setExternalId(null);
        txFactory.execute(tx -> tx.getOrderRepository().insert(orderEntity));

        given(syncService.getOpenOrders(pair)).willReturn(Collections.emptyList());

        // when
        new BinanceFuturesOpenOrderSyncTask(syncService, pair, orderId+1).call();

        // then
        Optional<OrderEntity> orderEntityUpd = txFactory.executeAndGet(tx -> tx.getOrderRepository().findById(futuresOrder.clientOrderId));
        assertThat(orderEntityUpd.isPresent()).isTrue();
        assertThat(orderEntityUpd.get().isTerminal()).isTrue();

        verify(syncService, times(1)).getOpenOrders(pair);
        verify(syncService, times(1)).getOrder(pair, futuresOrder.clientOrderId);
        verify(syncService, never()).getOrder(pair, futuresOrder.orderId);
    }

    // TODO: add tests regarding PAIR / instrument
}