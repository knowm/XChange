package nostro.xchange.binance.futures;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.binance.dto.ExecutionReportBinanceUserTransaction;
import info.bitrich.xchangestream.binance.futures.dto.OrderTradeUpdateBinanceUserTransaction;
import nostro.xchange.binance.DataSourceTest;
import nostro.xchange.binance.utils.NostroBinanceFuturesDTOUtils;
import nostro.xchange.binance.utils.NostroDBUtils;
import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.persistence.TradeEntity;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.binance.futures.service.BinanceFuturesTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderByUserReferenceParams;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderParamId;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParam;
import org.knowm.xchange.service.trade.params.orders.DefaultQueryOrderParam;
import org.knowm.xchange.utils.ObjectMapperHelper;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class NostroBinanceFuturesTradeServiceTest extends DataSourceTest {

    private BinanceFuturesTradeService inner;
    private TransactionFactory txFactory;
    private NostroBinanceFuturesTradeService service;
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        txFactory = TransactionFactory.get("Binance", "user0001");
        inner = mock(BinanceFuturesTradeService.class);
        service = new NostroBinanceFuturesTradeService(inner, txFactory);
        mapper = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @After
    public void tearDown() throws Exception {
        NostroDBUtils.dropTable(TransactionFactory.getDataSource(), "order$" + txFactory.getAccountId());
    }

    @Test
    public void testPlaceLimitOrder() throws IOException {
        final LimitOrder order = new LimitOrder.Builder(Order.OrderType.BID, new FuturesContract(CurrencyPair.BTC_USDT, null))
                .userReference(NostroUtils.randomUUID())
                .limitPrice(new BigDecimal("40000"))
                .originalAmount(new BigDecimal("2"))
                .build();

        given(inner.placeLimitOrder(any())).willReturn("12345");

        String externalId = service.placeLimitOrder(order);

        assertThat(externalId).isEqualTo("12345");
        Optional<OrderEntity> entity = txFactory.executeAndGet(tx -> tx.getOrderRepository().findByExternalId(externalId));
        assertThat(entity.isPresent()).isTrue();
        assertThat(entity.get().getId()).isEqualTo(order.getUserReference());

        LimitOrder dbOrder = ObjectMapperHelper.readValue(entity.get().getDocument(), LimitOrder.class);
        assertThat(dbOrder.getLimitPrice()).isEqualTo(order.getLimitPrice());
        assertThat(dbOrder.getOriginalAmount()).isEqualTo(order.getOriginalAmount());
        assertThat(dbOrder.getInstrument()).isEqualTo(order.getInstrument());
    }

    @Test
    public void testPlaceLimitOrderThrows() {
        final LimitOrder order = new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USDT)
                .userReference(NostroUtils.randomUUID())
                .limitPrice(new BigDecimal("40000"))
                .originalAmount(new BigDecimal("2"))
                .build();

        assertThatThrownBy(() -> service.placeLimitOrder(order)).isInstanceOf(NotAvailableFromExchangeException.class);
    }

    @Test
    public void testPlaceStopOrder() throws IOException {
        final StopOrder order = new StopOrder.Builder(Order.OrderType.BID, new FuturesContract(CurrencyPair.BTC_USDT, null))
                .userReference(NostroUtils.randomUUID())
                .stopPrice(new BigDecimal(40000))
                .limitPrice(new BigDecimal(41000))
                .originalAmount(BigDecimal.ONE)
                .build();

        given(inner.placeStopOrder(any())).willReturn("12345");

        String externalId = service.placeStopOrder(order);

        assertThat(externalId).isEqualTo("12345");
        Optional<OrderEntity> entity = txFactory.executeAndGet(tx -> tx.getOrderRepository().findByExternalId(externalId));
        assertThat(entity.isPresent()).isTrue();
        assertThat(entity.get().getId()).isEqualTo(order.getUserReference());

        StopOrder dbOrder = ObjectMapperHelper.readValue(entity.get().getDocument(), StopOrder.class);
        assertThat(dbOrder.getLimitPrice()).isEqualTo(order.getLimitPrice());
        assertThat(dbOrder.getStopPrice()).isEqualTo(order.getStopPrice());
        assertThat(dbOrder.getOriginalAmount()).isEqualTo(order.getOriginalAmount());
        assertThat(dbOrder.getInstrument()).isEqualTo(order.getInstrument());
    }

    @Test
    public void testPlaceStopOrderThrows() {
        final StopOrder order = new StopOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USDT)
                .userReference(NostroUtils.randomUUID())
                .stopPrice(new BigDecimal(40000))
                .limitPrice(new BigDecimal(41000))
                .originalAmount(BigDecimal.ONE)
                .build();

        assertThatThrownBy(() -> service.placeStopOrder(order)).isInstanceOf(NotAvailableFromExchangeException.class);
    }

    @Test
    public void testPlaceMarketOrder() throws IOException {
        final MarketOrder order = new MarketOrder.Builder(Order.OrderType.BID, new FuturesContract(CurrencyPair.BTC_USDT, null))
                .userReference(NostroUtils.randomUUID())
                .originalAmount(BigDecimal.ONE)
                .build();

        given(inner.placeMarketOrder(any())).willReturn("12345");

        String externalId = service.placeMarketOrder(order);

        assertThat(externalId).isEqualTo("12345");
        Optional<OrderEntity> entity = txFactory.executeAndGet(tx -> tx.getOrderRepository().findByExternalId(externalId));
        assertThat(entity.isPresent()).isTrue();
        assertThat(entity.get().getId()).isEqualTo(order.getUserReference());

        MarketOrder dbOrder = ObjectMapperHelper.readValue(entity.get().getDocument(), MarketOrder.class);
        assertThat(dbOrder.getOriginalAmount()).isEqualTo(order.getOriginalAmount());
        assertThat(dbOrder.getInstrument()).isEqualTo(order.getInstrument());
    }

    @Test
    public void testPlaceMarketOrderThrows() {
        final MarketOrder order = new MarketOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USDT)
                .userReference(NostroUtils.randomUUID())
                .originalAmount(BigDecimal.ONE)
                .build();

        assertThatThrownBy(() -> service.placeMarketOrder(order)).isInstanceOf(NotAvailableFromExchangeException.class);
    }

    @Test
    public void testCancelOrder() throws IOException {
        final LimitOrder order = new LimitOrder.Builder(Order.OrderType.BID, new FuturesContract(CurrencyPair.BTC_USDT, null))
                .userReference(NostroUtils.randomUUID())
                .limitPrice(new BigDecimal("40000"))
                .originalAmount(new BigDecimal("2"))
                .build();

        given(inner.placeLimitOrder(any())).willReturn("12345");

        service.placeLimitOrder(order);

        boolean cancelled = service.cancelOrder(new DefaultCancelOrderByUserReferenceParams(order.getUserReference()));
        assertThat(cancelled).isTrue();

        Optional<OrderEntity> entity = txFactory.executeAndGet(tx -> tx.getOrderRepository().findByExternalId("12345"));
        assertThat(entity.isPresent()).isTrue();
        assertThat(ObjectMapperHelper.readValue(entity.get().getDocument(), LimitOrder.class).getStatus()).isEqualTo(Order.OrderStatus.PENDING_CANCEL);
    }

    @Test
    public void testCancelOrderThrows() {
        assertThrows(ExchangeException.class, () -> {
            service.cancelOrder(new DefaultCancelOrderParamId("1"));
        });
    }

    @Test
    public void testGetOpenOrders() throws IOException {
        final LimitOrder limitOrder = new LimitOrder.Builder(Order.OrderType.BID, new FuturesContract(CurrencyPair.BTC_USDT, null))
                .userReference(NostroUtils.randomUUID())
                .limitPrice(new BigDecimal("40000"))
                .originalAmount(new BigDecimal("2"))
                .build();
        final MarketOrder marketOrder = new MarketOrder.Builder(Order.OrderType.BID, new FuturesContract(CurrencyPair.BTC_USDT, null))
                .userReference(NostroUtils.randomUUID())
                .originalAmount(BigDecimal.ONE)
                .build();

        given(inner.placeLimitOrder(any())).willReturn("1");
        given(inner.placeMarketOrder(any())).willReturn("2");

        service.placeLimitOrder(limitOrder);
        service.placeMarketOrder(marketOrder);

        OpenOrders openOrders = service.getOpenOrders();
        assertThat(openOrders.getAllOpenOrders().size()).isEqualTo(2);
        assertThat(openOrders.getOpenOrders().size()).isEqualTo(1);
    }

    @Test
    public void testGetOpenOrdersParams() throws IOException {
        final LimitOrder limitOrder = new LimitOrder.Builder(Order.OrderType.BID, new FuturesContract(CurrencyPair.BTC_USDT, null))
                .userReference(NostroUtils.randomUUID())
                .limitPrice(new BigDecimal("40000"))
                .originalAmount(new BigDecimal("2"))
                .build();

        given(inner.placeLimitOrder(any())).willReturn("1");

        service.placeLimitOrder(limitOrder);

        assertThat(service.getOpenOrders(null).getAllOpenOrders().size()).isEqualTo(1);
        assertThat(service.getOpenOrders(new DefaultOpenOrdersParam()).getAllOpenOrders().size()).isEqualTo(1);
        assertThat(service.getOpenOrders(order -> false).getAllOpenOrders().size()).isEqualTo(0);
    }

    @Test
    public void testGetOrderByIds() throws IOException {
        final LimitOrder limitOrder = new LimitOrder.Builder(Order.OrderType.BID, new FuturesContract(CurrencyPair.BTC_USDT, null))
                .userReference(NostroUtils.randomUUID())
                .limitPrice(new BigDecimal("40000"))
                .originalAmount(new BigDecimal("2"))
                .build();
        final MarketOrder marketOrder = new MarketOrder.Builder(Order.OrderType.BID, new FuturesContract(CurrencyPair.BTC_USDT, null))
                .userReference(NostroUtils.randomUUID())
                .originalAmount(BigDecimal.ONE)
                .build();

        given(inner.placeLimitOrder(any())).willReturn("1");
        given(inner.placeMarketOrder(any())).willReturn("2");

        service.placeLimitOrder(limitOrder);
        service.placeMarketOrder(marketOrder);

        assertThat(service.getOrder("1", "2").size()).isEqualTo(2);
        assertThat(service.getOrder("1").size()).isEqualTo(1);
        assertThrows(IllegalArgumentException.class, () -> {
            assertThat(service.getOrder("3").size()).isEqualTo(0);
        });
    }

    @Test
    public void testGetOrderByParams() throws IOException {
        assertThrows(NotAvailableFromExchangeException.class, () -> {
            service.getOrder(new DefaultQueryOrderParam("1"));
        });
    }

    @Test
    public void testGetOpenPositions() {
        assertThrows(NotYetImplementedForExchangeException.class, () -> {
            service.getOpenPositions();
        });
    }

    @Test
    public void testGetTradeHistory() {
        assertThrows(NotYetImplementedForExchangeException.class, () -> {
            service.getTradeHistory(null);
        });
    }

    @Test
    public void testVerifyOrder() {
        assertThrows(NotYetImplementedForExchangeException.class, () -> {
            service.verifyOrder(new LimitOrder(Order.OrderType.ASK, BigDecimal.ONE, CurrencyPair.BTC_USDT, "1", new Date(), BigDecimal.ONE));
        });
        assertThrows(NotYetImplementedForExchangeException.class, () -> {
            service.verifyOrder(new MarketOrder(Order.OrderType.ASK, BigDecimal.ONE, CurrencyPair.BTC_USDT));
        });
    }

    @Test
    public void testSaveExecutionReport() throws JsonProcessingException {
        long created = Long.parseLong("1638271651233");
        String userReference = "DTI";
        String orderId = "123123";
        OrderTradeUpdateBinanceUserTransaction transaction = NostroBinanceFuturesDTOUtils.orderTradeUpdateBinanceUserTransaction(orderId, userReference, "0", "BTCUSDT", Order.OrderStatus.NEW, ExecutionReportBinanceUserTransaction.ExecutionType.NEW, created);

        Pair<Order, UserTrade> pair = service.saveExecutionReport(transaction);
        Order order = pair.getLeft();
        UserTrade trade = pair.getRight();

        assertThat(order).isNotNull();
        assertThat(trade).isNull();

        Optional<OrderEntity> orderEntity = txFactory.executeAndGet(tx -> tx.getOrderRepository().findById(order.getUserReference()));
        assertThat(orderEntity.isPresent()).isTrue();
        assertThat(orderEntity.get().getCreated().getTime()).isEqualTo(created);
        assertThat(orderEntity.get().getUpdated().getTime()).isEqualTo(created);
        assertThat(orderEntity.get().getExternalId()).isEqualTo(orderId);
        assertThat(orderEntity.get().getId()).isEqualTo(order.getUserReference());
        assertThat(orderEntity.get().isTerminal()).isFalse();
        assertThat(orderEntity.get().getInstrument()).isEqualTo(new FuturesContract(CurrencyPair.BTC_USDT, null).toString());


        List<TradeEntity> tradeEntities = txFactory.executeAndGet(tx -> tx.getTradeRepository().findAllByOrderId(order.getUserReference()));
        assertThat(tradeEntities.size()).isEqualTo(0);
    }

    @Test
    public void testSaveExecutionReport_scenario() throws JsonProcessingException {
        // create and fill market order scenario

        long created = Long.parseLong("1638271651233");
        String userReference = "YbV";
        String orderId = "777";
        OrderTradeUpdateBinanceUserTransaction transaction = NostroBinanceFuturesDTOUtils.orderTradeUpdateBinanceUserTransaction(orderId, userReference, "0", "BTCUSDT", Order.OrderStatus.NEW, ExecutionReportBinanceUserTransaction.ExecutionType.NEW, created);
        service.saveExecutionReport(transaction);

        long updated = Long.parseLong("1638271651243");
        String tradeId = "111";
        OrderTradeUpdateBinanceUserTransaction transaction2 = NostroBinanceFuturesDTOUtils.orderTradeUpdateBinanceUserTransaction(orderId, userReference, tradeId, "BTCUSDT", Order.OrderStatus.FILLED, ExecutionReportBinanceUserTransaction.ExecutionType.TRADE, updated);
        Pair<Order, UserTrade> pair = service.saveExecutionReport(transaction2);
        Order order = pair.getLeft();
        UserTrade trade = pair.getRight();

        assertThat(order).isNotNull();
        assertThat(trade).isNotNull();

        Optional<OrderEntity> orderEntity = txFactory.executeAndGet(tx -> tx.getOrderRepository().findById(order.getUserReference()));
        assertThat(orderEntity.isPresent()).isTrue();
        assertThat(orderEntity.get().getCreated().getTime()).isEqualTo(created);
        assertThat(orderEntity.get().getUpdated().getTime()).isEqualTo(updated);
        assertThat(orderEntity.get().isTerminal()).isTrue();

        List<TradeEntity> tradeEntities = txFactory.executeAndGet(tx -> tx.getTradeRepository().findAllByOrderId(order.getUserReference()));
        assertThat(tradeEntities.size()).isEqualTo(1);

        TradeEntity tradeEntity = tradeEntities.get(0);
        assertThat(tradeEntity.getTimestamp().getTime()).isEqualTo(updated);
        assertThat(tradeEntity.getExternalId()).isEqualTo(tradeId);
        assertThat(tradeEntity.getOrderId()).isEqualTo(order.getUserReference());
    }

    @Test
    public void testSaveExecutionReport_scenario_create_date_update() throws JsonProcessingException {
        // create and fill market order scenario however imitate that create date is not set

        String userReference = "XYZ";
        String orderId = "99999";
        OrderTradeUpdateBinanceUserTransaction transaction = NostroBinanceFuturesDTOUtils.orderTradeUpdateBinanceUserTransaction(orderId, userReference, "0", "BTCUSDT", Order.OrderStatus.NEW, ExecutionReportBinanceUserTransaction.ExecutionType.NEW, 0);
        Order order = transaction.toOrder();

        OrderEntity e = new OrderEntity.Builder()
                .id(order.getUserReference())
                .instrument(order.getInstrument().toString())
                .document(NostroUtils.writeOrderDocument(order))
                .externalId(null)
                .terminal(false)
                .created(new Timestamp(0))
                .updated(new Timestamp(0))
                .build();

        txFactory.execute(tx -> tx.getOrderRepository().insert(e));

        long created = Long.parseLong("1638271651233");
        service.saveExecutionReport(NostroBinanceFuturesDTOUtils.orderTradeUpdateBinanceUserTransaction(orderId, userReference, "0", "BTCUSDT", Order.OrderStatus.NEW, ExecutionReportBinanceUserTransaction.ExecutionType.NEW, created));

        long updated = Long.parseLong("1638271651243");
        service.saveExecutionReport(NostroBinanceFuturesDTOUtils.orderTradeUpdateBinanceUserTransaction(orderId, userReference, "909990", "BTCUSDT", Order.OrderStatus.FILLED, ExecutionReportBinanceUserTransaction.ExecutionType.TRADE, updated));

        Optional<OrderEntity> orderEntity = txFactory.executeAndGet(tx -> tx.getOrderRepository().findById(userReference));
        assertThat(orderEntity.isPresent()).isTrue();
        assertThat(orderEntity.get().getCreated().getTime()).isEqualTo(created);
        assertThat(orderEntity.get().getUpdated().getTime()).isEqualTo(updated);
    }
}