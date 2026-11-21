package org.knowm.xchange.bybit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;
import static org.knowm.xchange.currency.CurrencyPair.BTC_USDT;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.trade.BybitCancelAllOrdersParams;
import org.knowm.xchange.bybit.dto.trade.BybitCancelOrderParams;
import org.knowm.xchange.bybit.dto.trade.BybitOpenOrdersParam;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.TradeService;

class BybitTradeServiceTest extends BaseWiremockTest {

  BybitExchange bybitExchange;
  TradeService tradeService;

  @BeforeEach
  void setUp() throws IOException {
    bybitExchange = createExchange();
    tradeService = new BybitTradeService(bybitExchange, bybitExchange.getResilienceRegistries());
  }

  @Test
  void getBybitOrderLinear() throws Exception {
    initGetStub("/v5/order/realtime", "/getOrderLinear.json5");

    Collection<Order> orders = tradeService.getOrder("fd4300ae-7847-404e-b947-b46980a4d140");
    assertThat(orders.size()).isEqualTo(1);

    Order order = (Order) orders.toArray()[0];
    assertThat(order.getType()).isEqualTo(OrderType.BID);
    assertThat(order.getInstrument()).isEqualTo(new FuturesContract("ETH/USDT/PERP"));
    assertThat(order.getAveragePrice()).isEqualTo(new BigDecimal("0"));
    assertThat(order.getStatus()).isEqualTo(OrderStatus.NEW);
    assertThat(order.getOriginalAmount()).isEqualTo(new BigDecimal("0.10"));
    assertThat(order.getFeeCurrency()).isNull();
  }

  @Test
  void getBybitOrderSpotBuyFeeInBaseCurrency() throws Exception {
    initGetStub("/v5/order/realtime", "/getOrderSpotBuyFeeInBaseCurrency.json5");

    Collection<Order> orders = tradeService.getOrder("c1f7ba8b-2f1e-4c1e-9c1a-64d0e5c8b2f1");
    assertThat(orders.size()).isEqualTo(1);

    Order order = (Order) orders.toArray()[0];
    assertThat(order.getType()).isEqualTo(OrderType.BID);
    assertThat(order.getInstrument()).isEqualTo(new CurrencyPair("BTC", "USDT"));
    assertThat(order.getStatus()).isEqualTo(OrderStatus.FILLED);
    assertThat(order.getOriginalAmount()).isEqualTo(new BigDecimal("0.00100000"));
    // Bybit spot buy fee is charged in the base currency,
    // so cumulativeAmount is cumExecQty (0.00100000) reduced by cumExecFee (0.00001000)
    assertThat(order.getCumulativeAmount()).isEqualByComparingTo(new BigDecimal("0.00099000"));
    assertThat(order.getFee()).isEqualByComparingTo(new BigDecimal("0.00001000"));
    assertThat(order.getFeeCurrency()).isEqualTo(new Currency("BTC"));
  }

  @Test
  void getBybitOrderSpotBuyFeeDetail() throws Exception {
    initGetStub("/v5/order/realtime", "/getOrderSpotBuyFeeDetail.json5");

    Collection<Order> orders = tradeService.getOrder("a2e8c7d4-3b6f-49a8-8e2d-75f1b0c9a4e6");
    assertThat(orders.size()).isEqualTo(1);

    Order order = (Order) orders.toArray()[0];
    // cumFeeDetail is authoritative: the BTC entry is deducted even though
    // the deprecated cumExecFee is "0" and feeCurrency is empty
    assertThat(order.getCumulativeAmount()).isEqualByComparingTo(new BigDecimal("0.00099000"));
    assertThat(order.getFee()).isEqualByComparingTo(new BigDecimal("0.00001000"));
    assertThat(order.getFeeCurrency()).isEqualTo(new Currency("BTC"));
  }

  @Test
  void getBybitOrderSpotBuyFeeInThirdCurrency() throws Exception {
    initGetStub("/v5/order/realtime", "/getOrderSpotBuyFeeInThirdCurrency.json5");

    Collection<Order> orders = tradeService.getOrder("b3d9f1e2-8c7a-4b5d-9f0e-26a7c3d8e5f1");
    assertThat(orders.size()).isEqualTo(1);

    Order order = (Order) orders.toArray()[0];
    // cumFeeDetail has no base-currency entry (the fee is paid in BIT),
    // so nothing is deducted even though the deprecated cumExecFee/feeCurrency say otherwise
    assertThat(order.getCumulativeAmount()).isEqualByComparingTo(new BigDecimal("0.00100000"));
    assertThat(order.getFee()).isEqualByComparingTo(new BigDecimal("1.5"));
    assertThat(order.getFeeCurrency()).isEqualTo(new Currency("BIT"));
  }

  @Test
  void getBybitOrderSpotMarketBuyQuoteUnit() throws Exception {
    initGetStub("/v5/order/realtime", "/getOrderSpotMarketBuyQuoteUnit.json5");

    Collection<Order> orders = tradeService.getOrder("0f7c98b1-9a5e-4f2e-b6c4-31c2a8e1d9f3");
    assertThat(orders.size()).isEqualTo(1);

    Order order = (Order) orders.toArray()[0];
    assertThat(order.getInstrument()).isEqualTo(new CurrencyPair("ETH", "USDT"));
    // marketUnit=quoteCoin: qty (20 USDT) is converted to the base currency by the average fill
    // price
    assertThat(order.getOriginalAmount())
        .isCloseTo(new BigDecimal("0.00077202"), within(new BigDecimal("0.00000001")));
    assertThat(order.getCumulativeAmount()).isEqualByComparingTo(new BigDecimal("0.00077202"));
  }

  @Test
  void getBybitOrderSpotFeeInQuoteCurrency() throws Exception {
    initGetStub("/v5/order/realtime", "/getOrderDetailsSpot.json5");

    Collection<Order> orders = tradeService.getOrder("fd4300ae-7847-404e-b947-b46980a4d140");
    assertThat(orders.size()).isEqualTo(1);

    Order order = (Order) orders.toArray()[0];
    assertThat(order.getType()).isEqualTo(OrderType.BID);
    assertThat(order.getInstrument()).isEqualTo(new CurrencyPair("ETH", "USDT"));
    // empty marketUnit + Buy + Market means Bybit's default "by value" market buy,
    // so the partially filled order has its qty (20 USDT) converted to the base currency
    assertThat(order.getOriginalAmount())
        .isCloseTo(new BigDecimal("0.00077202"), within(new BigDecimal("0.00000001")));
    // the fee is charged in the quote currency (USDT), so cumulativeAmount stays as cumExecQty
    assertThat(order.getCumulativeAmount()).isEqualByComparingTo(new BigDecimal("0.000772"));
    // cumFeeDetail is absent, so the deprecated cumExecFee/feeCurrency are used as a fallback
    assertThat(order.getFee()).isEqualByComparingTo(new BigDecimal("0.000000772"));
    assertThat(order.getFeeCurrency()).isEqualTo(new Currency("USDT"));
  }

  @Test
  void getOrders() throws Exception {
    initGetStub("/v5/order/realtime", "/getOrderLinear.json5");
    BybitOpenOrdersParam param =
        new BybitOpenOrdersParam(new FuturesContract("ETH/USDT/PERP"), BybitCategory.LINEAR);
    List<LimitOrder> limitOrder = tradeService.getOpenOrders(param).getOpenOrders();

    assertThat(limitOrder.size()).isEqualTo(1);

    Order order = (Order) limitOrder.toArray()[0];
    assertThat(order.getType()).isEqualTo(OrderType.BID);
    assertThat(order.getInstrument()).isEqualTo(new FuturesContract("ETH/USDT/PERP"));
    assertThat(order.getStatus()).isEqualTo(OrderStatus.NEW);
    assertThat(order.getOriginalAmount()).isEqualTo(new BigDecimal("0.10"));
  }

  @Test
  void getOrdersError() throws Exception {
    initGetStub("/v5/order/realtime", "/getOrderError.json5");
    BybitOpenOrdersParam param =
        new BybitOpenOrdersParam(new FuturesContract("ETH/USDT/PERP"), BybitCategory.LINEAR);
    assertThatExceptionOfType(BybitException.class)
        .isThrownBy(
            () -> {
              List<LimitOrder> limitOrder = tradeService.getOpenOrders(param).getOpenOrders();
            });
  }

  @Test
  void placeBybitOrder() throws Exception {
    initPostStub("/v5/order/create", "/placeMarketOrder.json5");
    MarketOrder marketOrder =
        new MarketOrder(OrderType.ASK, new BigDecimal("0.1"), new CurrencyPair("BTC", "USDT"));
    String marketOrderId = tradeService.placeMarketOrder(marketOrder);
    assertThat(marketOrderId).isEqualTo("1321003749386327552");

    LimitOrder limitOrder =
        new LimitOrder(
            OrderType.EXIT_BID,
            new BigDecimal("0.1"),
            new CurrencyPair("BTC", "USDT"),
            "",
            new Date(1672211918471L),
            new BigDecimal("110"));
    String limitOrderId = tradeService.placeLimitOrder(limitOrder);
    assertThat(limitOrderId).isEqualTo("1321003749386327552");
  }

  @Test
  void changeBybitOrder() throws Exception {
    initPostStub("/v5/order/amend", "/changeOrder.json5");

    LimitOrder limitOrder =
        new LimitOrder(
            OrderType.BID,
            new BigDecimal("0.1"),
            new CurrencyPair("BTC", "USDT"),
            "",
            new Date(1672211918471L),
            new BigDecimal("110"));

    String orderId = tradeService.changeOrder(limitOrder);

    assertThat(orderId).isEqualTo("c6f055d9-7f21-4079-913d-e6523a9cfffa");
  }

  @Test
  void cancelBybitOrder() throws Exception {
    initPostStub("/v5/order/cancel", "/cancelOrder.json5");

    Instrument BTC_USDT_PERP = new FuturesContract("BTC/USDT/PERP");
    assertThatThrownBy(() -> tradeService.cancelOrder(new BybitCancelOrderParams(BTC_USDT, "", "")))
        .isInstanceOf(UnsupportedOperationException.class);
    boolean resultSpot =
        tradeService.cancelOrder(
            new BybitCancelOrderParams(BTC_USDT, "c6f055d9-7f21-4079-913d-e6523a9cfffa", ""));
    boolean resultFuture0 =
        tradeService.cancelOrder(
            new BybitCancelOrderParams(BTC_USDT_PERP, "c6f055d9-7f21-4079-913d-e6523a9cfffa", ""));
    boolean resultFuture1 =
        tradeService.cancelOrder(new BybitCancelOrderParams(BTC_USDT_PERP, "", "linear-004"));
    assertThat(resultSpot).isTrue();
    assertThat(resultFuture0).isTrue();
    assertThat(resultFuture1).isTrue();
  }

  @Test
  void cancelAllBybitOrder() throws Exception {
    initPostStub("/v5/order/cancel-all", "/cancelAllOrders.json5");
    Instrument BTC_USDT_PERP = new FuturesContract("BTC/USDT/PERP");

    assertThatThrownBy(
            () -> tradeService.cancelAllOrders(new BybitCancelAllOrdersParams(null, null)))
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(
            () ->
                tradeService.cancelAllOrders(
                    new BybitCancelAllOrdersParams(BybitCategory.LINEAR, null)))
        .isInstanceOf(UnsupportedOperationException.class);
    Collection<String> resultSpot =
        tradeService.cancelAllOrders(new BybitCancelAllOrdersParams(BybitCategory.SPOT, null));

    Collection<String> resultFuture0 =
        tradeService.cancelAllOrders(
            new BybitCancelAllOrdersParams(BybitCategory.LINEAR, BTC_USDT_PERP));

    assertThat(resultSpot.stream().findFirst().get()).isEqualTo("1616024329462743808");
    assertThat(resultFuture0.stream().findFirst().get()).isEqualTo("1616024329462743808");
  }
}
