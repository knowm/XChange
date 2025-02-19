package org.knowm.xchange.bybit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.knowm.xchange.currency.CurrencyPair.BTC_USDT;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.trade.BybitCancelAllOrdersParams;
import org.knowm.xchange.bybit.dto.trade.BybitCancelOrderParams;
import org.knowm.xchange.bybit.dto.trade.BybitOpenOrdersParam;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.TradeService;

public class BybitTradeServiceTest extends BaseWiremockTest {

  BybitExchange bybitExchange;
  TradeService tradeService;

  @Before
  public void setUp() throws IOException {
    bybitExchange = createExchange();
    tradeService = new BybitTradeService(bybitExchange, bybitExchange.getResilienceRegistries());
  }

  @Test
  public void testGetBybitOrderLinear() throws IOException {
    initGetStub("/v5/order/realtime", "/getOrderLinear.json5");

    Collection<Order> orders = tradeService.getOrder("fd4300ae-7847-404e-b947-b46980a4d140");
    assertThat(orders.size()).isEqualTo(1);

    Order order = (Order) orders.toArray()[0];
    assertThat(order.getType()).isEqualTo(OrderType.BID);
    assertThat(order.getInstrument()).isEqualTo(new FuturesContract("ETH/USDT/PERP"));
    assertThat(order.getAveragePrice()).isEqualTo(new BigDecimal("0"));
    assertThat(order.getStatus()).isEqualTo(OrderStatus.NEW);
    assertThat(order.getOriginalAmount()).isEqualTo(new BigDecimal("0.10"));
  }

  @Test
  public void testGetOrders() throws IOException {
    initGetStub("/v5/order/realtime", "/getOrderLinear.json5");
    BybitOpenOrdersParam param = new BybitOpenOrdersParam(new FuturesContract("ETH/USDT/PERP"), BybitCategory.LINEAR);
    List<LimitOrder> limitOrder = tradeService.getOpenOrders(param).getOpenOrders();

    assertThat(limitOrder.size()).isEqualTo(1);

    Order order = (Order) limitOrder.toArray()[0];
    assertThat(order.getType()).isEqualTo(OrderType.BID);
    assertThat(order.getInstrument()).isEqualTo(new FuturesContract("ETH/USDT/PERP"));
    assertThat(order.getStatus()).isEqualTo(OrderStatus.NEW);
    assertThat(order.getOriginalAmount()).isEqualTo(new BigDecimal("0.10"));
  }

  @Test(expected = BybitException.class)
  public void testGetOrdersError() throws IOException {
    initGetStub("/v5/order/realtime", "/getOrderError.json5");
    BybitOpenOrdersParam param = new BybitOpenOrdersParam(new FuturesContract("ETH/USDT/PERP"), BybitCategory.LINEAR);
    List<LimitOrder> limitOrder = tradeService.getOpenOrders(param).getOpenOrders();
  }

  @Test
  public void testPlaceBybitOrder() throws IOException {
    initPostStub("/v5/order/create", "/placeMarketOrder.json5");
    MarketOrder marketOrder = new MarketOrder(OrderType.ASK, new BigDecimal("0.1"),
        new CurrencyPair("BTC", "USDT"));
    String marketOrderId = tradeService.placeMarketOrder(marketOrder);
    assertThat(marketOrderId).isEqualTo("1321003749386327552");

    LimitOrder limitOrder = new LimitOrder(OrderType.EXIT_BID, new BigDecimal("0.1"),
        new CurrencyPair("BTC", "USDT"), "", new Date(1672211918471L), new BigDecimal("110"));
    String limitOrderId = tradeService.placeLimitOrder(limitOrder);
    assertThat(limitOrderId).isEqualTo("1321003749386327552");
  }

  @Test
  public void testChangeBybitOrder() throws IOException {
    initPostStub("/v5/order/amend", "/changeOrder.json5");

    LimitOrder limitOrder = new LimitOrder(OrderType.BID, new BigDecimal("0.1"),
        new CurrencyPair("BTC", "USDT"), "", new Date(1672211918471L), new BigDecimal("110"));

    String orderId =
        tradeService.changeOrder(limitOrder);

    assertThat(orderId).isEqualTo("c6f055d9-7f21-4079-913d-e6523a9cfffa");
  }

  @Test
  public void testCancelBybitOrder() throws IOException {
    initPostStub("/v5/order/cancel", "/cancelOrder.json5");

    Instrument BTC_USDT_PERP = new FuturesContract("BTC/USDT/PERP");
    try {
      tradeService.cancelOrder(new BybitCancelOrderParams(BTC_USDT, "", ""));
      fail("Expected UnsupportedOperationException");
    } catch (UnsupportedOperationException ignored) {
    }
    boolean resultSpot = tradeService.cancelOrder(
        new BybitCancelOrderParams(BTC_USDT, "c6f055d9-7f21-4079-913d-e6523a9cfffa", ""));
    boolean resultFuture0 = tradeService.cancelOrder(
        new BybitCancelOrderParams(BTC_USDT_PERP, "c6f055d9-7f21-4079-913d-e6523a9cfffa", ""));
    boolean resultFuture1 = tradeService.cancelOrder(
        new BybitCancelOrderParams(BTC_USDT_PERP, "", "linear-004"));
    assertThat(resultSpot).isTrue();
    assertThat(resultFuture0).isTrue();
    assertThat(resultFuture1).isTrue();
  }

  @Test
  public void testCancelAllBybitOrder() throws IOException {
    initPostStub("/v5/order/cancel-all", "/cancelAllOrders.json5");
    Instrument BTC_USDT_PERP = new FuturesContract("BTC/USDT/PERP");

    try {
      tradeService.cancelAllOrders(new BybitCancelAllOrdersParams(
          null, null));
      fail("Expected UnsupportedOperationException");
    } catch (UnsupportedOperationException ignored) {

    }
    try {
      tradeService.cancelAllOrders(new BybitCancelAllOrdersParams(BybitCategory.LINEAR, null));
      fail("Expected UnsupportedOperationException");
    } catch (UnsupportedOperationException ignored) {

    }
    Collection<String> resultSpot = tradeService.cancelAllOrders(
        new BybitCancelAllOrdersParams(
            BybitCategory.SPOT, null));

    Collection<String> resultFuture0 = tradeService.cancelAllOrders(
        new BybitCancelAllOrdersParams(BybitCategory.LINEAR, BTC_USDT_PERP));

    assertThat(resultSpot.stream().findFirst().get()).isEqualTo("1616024329462743808");
    assertThat(resultFuture0.stream().findFirst().get()).isEqualTo("1616024329462743808");

  }

}
