package nostro.xchange.binance.futures.sync;

import nostro.xchange.binance.DataSourceTest;
import nostro.xchange.binance.futures.NostroBinanceFuturesUtils;
import nostro.xchange.binance.utils.NostroBinanceFuturesDtoUtils;
import nostro.xchange.binance.utils.NostroDBUtils;
import nostro.xchange.persistence.TradeEntity;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroStreamingPublisher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.binance.dto.trade.OrderStatus;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesOrder;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesTrade;
import org.knowm.xchange.currency.CurrencyPair;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class BinanceFuturesTradeSyncTaskTest extends DataSourceTest {
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
        NostroDBUtils.dropTable(TransactionFactory.getDataSource(), "trade$" + txFactory.getAccountId());
        NostroDBUtils.dropTable(TransactionFactory.getDataSource(), "order$" + txFactory.getAccountId());
    }

    @Test
    public void testSync_empty() throws Exception {
        int fromId = 1;
        CurrencyPair pair = CurrencyPair.BTC_USDT;

        Long result = new BinanceFuturesTradeSyncTask(syncService, pair, fromId).call();
        assertThat(result).isEqualTo(fromId);
        verify(syncService, times(1)).getTrades(pair, fromId, BinanceFuturesTradeSyncTask.LIMIT);
    }

    @Test
    public void testSync_1Page_1Order_1Trade_from_service() throws Exception {
        // order is resolved from service
        int tradeId = 1;
        int fromTradeId = 0;
        long time = new Timestamp(new Date().getTime()).getTime();
        CurrencyPair pair = CurrencyPair.BTC_USDT;
        BinanceFuturesTrade trade = NostroBinanceFuturesDtoUtils.generateTrade(tradeId, tradeId, time);
        BinanceFuturesOrder order = NostroBinanceFuturesDtoUtils.generateOrder(tradeId, OrderStatus.FILLED, "BTCUSDT", time, time, new BigDecimal(1));
        given(syncService.getTrades(pair, fromTradeId, BinanceFuturesTradeSyncTask.LIMIT)).willReturn(Collections.singletonList(trade));
        given(syncService.getOrder(pair, order.orderId)).willReturn(order);

        Long result = new BinanceFuturesTradeSyncTask(syncService, pair, fromTradeId).call();

        assertThat(result).isEqualTo(tradeId+1);
        verify(syncService, times(1)).getTrades(pair, fromTradeId, BinanceFuturesTradeSyncTask.LIMIT);
        verify(syncService, times(1)).getOrder(pair, order.orderId);
        assertThat(txFactory.executeAndGet(tx -> tx.getTradeRepository().findByExternalId(String.valueOf(trade.id))).isPresent()).isTrue();
        assertThat(txFactory.executeAndGet(tx -> tx.getOrderRepository().findByExternalId(String.valueOf(order.orderId))).isPresent()).isTrue();
    }

    @Test
    public void testSync_1Page_1Order_1Trade_from_db() throws Exception {
        // order is resolved from db
        int tradeId = 2;
        int fromTradeId = tradeId-1;
        long time = new Timestamp(new Date().getTime()).getTime();
        CurrencyPair pair = CurrencyPair.BTC_USDT;
        BinanceFuturesTrade trade = NostroBinanceFuturesDtoUtils.generateTrade(tradeId, tradeId, time);
        BinanceFuturesOrder order = NostroBinanceFuturesDtoUtils.generateOrder(tradeId, OrderStatus.FILLED, "BTCUSDT", time, time, new BigDecimal(1));
        given(syncService.getTrades(pair, fromTradeId, BinanceFuturesTradeSyncTask.LIMIT)).willReturn(Collections.singletonList(trade));
        given(syncService.getOrder(pair, order.orderId)).willReturn(order);
        txFactory.execute(tx -> tx.getOrderRepository().insert(NostroBinanceFuturesUtils.toEntity(order)));
        assertThat(txFactory.executeAndGet(tx -> tx.getOrderRepository().findByExternalId(String.valueOf(order.orderId))).isPresent()).isTrue();

        Long result = new BinanceFuturesTradeSyncTask(syncService, pair, fromTradeId).call();

        assertThat(result).isEqualTo(tradeId+1);
        verify(syncService, times(1)).getTrades(pair, fromTradeId, BinanceFuturesTradeSyncTask.LIMIT);
        verify(syncService, never()).getOrder(pair, order.orderId);
        assertThat(txFactory.executeAndGet(tx -> tx.getTradeRepository().findByExternalId(String.valueOf(trade.id))).isPresent()).isTrue();
    }

    @Test
    public void testSync_2Pages_1Order_2Trades_from_service() throws Exception {
        int tradeId = 3;
        int fromTradeId = tradeId-1;
        long time = new Timestamp(new Date().getTime()).getTime();
        int limit = 1;
        BinanceFuturesTradeSyncTask.LIMIT = limit;
        CurrencyPair pair = CurrencyPair.BTC_USDT;
        BinanceFuturesTrade trade = NostroBinanceFuturesDtoUtils.generateTrade(tradeId, tradeId, time);
        BinanceFuturesTrade trade2 = NostroBinanceFuturesDtoUtils.generateTrade(tradeId+1, tradeId, time);
        BinanceFuturesOrder order = NostroBinanceFuturesDtoUtils.generateOrder(tradeId, OrderStatus.FILLED, "BTCUSDT", time, time, new BigDecimal(1));
        given(syncService.getTrades(pair, fromTradeId, limit)).willReturn(Collections.singletonList(trade));
        given(syncService.getTrades(pair, fromTradeId+1, limit)).willReturn(Collections.singletonList(trade2));
        given(syncService.getTrades(pair, fromTradeId+2, limit)).willReturn(Collections.emptyList());
        given(syncService.getOrder(pair, order.orderId)).willReturn(order);

        Long result = new BinanceFuturesTradeSyncTask(syncService, pair, fromTradeId).call();

        assertThat(result).isEqualTo(tradeId+2);
        verify(syncService, times(1)).getTrades(pair, fromTradeId, limit);
        verify(syncService, times(1)).getTrades(pair, fromTradeId+1, limit);
        verify(syncService, times(1)).getTrades(pair, fromTradeId+2, limit);
        verify(syncService, times(1)).getOrder(pair, order.orderId);
        assertThat(txFactory.executeAndGet(tx -> tx.getTradeRepository().findByExternalId(String.valueOf(trade.id))).isPresent()).isTrue();
        assertThat(txFactory.executeAndGet(tx -> tx.getTradeRepository().findByExternalId(String.valueOf(trade2.id))).isPresent()).isTrue();
        assertThat(txFactory.executeAndGet(tx -> tx.getOrderRepository().findByExternalId(String.valueOf(order.orderId))).isPresent()).isTrue();
    }

    @Test
    public void testInsert() throws Exception {
        // order is resolved from service
        int tradeId = 4;
        int fromTradeId = tradeId-1;
        long time = new Timestamp(new Date().getTime()).getTime();
        CurrencyPair pair = CurrencyPair.BTC_USDT;
        BinanceFuturesTrade trade = NostroBinanceFuturesDtoUtils.generateTrade(tradeId, tradeId, time);
        BinanceFuturesOrder order = NostroBinanceFuturesDtoUtils.generateOrder(tradeId, OrderStatus.FILLED, "BTCUSDT", time, time, new BigDecimal(1));
        given(syncService.getTrades(pair, fromTradeId, BinanceFuturesTradeSyncTask.LIMIT)).willReturn(Collections.singletonList(trade));
        given(syncService.getOrder(pair, order.orderId)).willReturn(order);

        new BinanceFuturesTradeSyncTask(syncService, pair, fromTradeId).call();

        Optional<TradeEntity> tradeEntity = txFactory.executeAndGet(tx -> tx.getTradeRepository().findByExternalId(String.valueOf(trade.id)));
        assertThat(tradeEntity.isPresent()).isTrue();
        assertThat(tradeEntity.get().getTimestamp().getTime()).isEqualTo(trade.time);
        assertThat(tradeEntity.get().getExternalId()).isEqualTo(String.valueOf(trade.id));
        assertThat(tradeEntity.get().getOrderId()).isEqualTo(String.valueOf(order.clientOrderId));
    }

    @Test
    public void testPublisher() throws Exception {
        // order is resolved from service
        int tradeId = 5;
        int fromTradeId = tradeId-1;
        long time = new Timestamp(new Date().getTime()).getTime();
        CurrencyPair pair = CurrencyPair.BTC_USDT;
        BinanceFuturesTrade trade = NostroBinanceFuturesDtoUtils.generateTrade(tradeId, tradeId, time);
        BinanceFuturesOrder order = NostroBinanceFuturesDtoUtils.generateOrder(tradeId, OrderStatus.FILLED, "BTCUSDT", time, time, new BigDecimal(1));
        given(syncService.getTrades(pair, fromTradeId, BinanceFuturesTradeSyncTask.LIMIT)).willReturn(Collections.singletonList(trade));
        given(syncService.getOrder(pair, order.orderId)).willReturn(order);

        new BinanceFuturesTradeSyncTask(syncService, pair, fromTradeId).call();

        verify(publisher, times(1)).publish(NostroBinanceFuturesUtils.adaptTrade(trade, pair));
    }
}