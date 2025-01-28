package org.knowm.xchange.bybit.service;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.fail;
import static org.knowm.xchange.currency.CurrencyPair.BTC_USDT;

import com.github.tomakehurst.wiremock.matching.ContainsPattern;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.service.BybitTradeService.BybitCancelAllOrdersParams;
import org.knowm.xchange.bybit.service.BybitTradeService.BybitCancelOrderParams;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;

public class BybitTradeServiceTest extends BaseWiremockTest {

  BybitExchange bybitExchange;
  BybitTradeService bybitTradeService;

  @Before
  public void setUp() throws IOException {
    bybitExchange = createExchange();
    bybitTradeService = new BybitTradeService(bybitExchange,
        bybitExchange.getResilienceRegistries());
  }

  @Test
  public void testGetBybitOrder() throws IOException {
    initGetStub("/v5/order/realtime", "/getOrder.json5", "orderId",
        new ContainsPattern("fd4300ae-7847-404e-b947-b46980a4d140"));

    Collection<Order> orders = bybitTradeService.getOrder("fd4300ae-7847-404e-b947-b46980a4d140");
    assertThat(orders.size()).isEqualTo(1);

    Order order = (Order) orders.toArray()[0];
    assertThat(order.getType()).isEqualTo(OrderType.BID);
    assertThat(order.getInstrument()).isEqualTo(new CurrencyPair("ETH", "USDT"));
    assertThat(order.getAveragePrice()).isEqualTo(new BigDecimal("0"));
    assertThat(order.getStatus()).isEqualTo(OrderStatus.NEW);
    assertThat(order.getOriginalAmount()).isEqualTo(new BigDecimal("0.10"));
  }

  @Test
  public void testPlaceBybitOrder() throws IOException {
    initPostStub("/v5/order/create", "/placeMarketOrder.json5");
    MarketOrder marketOrder = new MarketOrder(OrderType.ASK, new BigDecimal("0.1"),
        new CurrencyPair("BTC", "USDT"));
    String marketOrderId = bybitTradeService.placeMarketOrder(marketOrder);
    assertThat(marketOrderId).isEqualTo("1321003749386327552");

    LimitOrder limitOrder = new LimitOrder(OrderType.EXIT_BID, new BigDecimal("0.1"),
        new CurrencyPair("BTC", "USDT"), "", new Date(1672211918471L), new BigDecimal("110"));
    String limitOrderId = bybitTradeService.placeLimitOrder(limitOrder);
    assertThat(limitOrderId).isEqualTo("1321003749386327552");
  }

  @Test
  public void testChangeBybitOrder() throws IOException {
    initPostStub("/v5/order/amend", "/changeOrder.json5");

    LimitOrder limitOrder = new LimitOrder(OrderType.BID, new BigDecimal("0.1"),
        new CurrencyPair("BTC", "USDT"), "", new Date(1672211918471L), new BigDecimal("110"));

    String orderId =
        bybitTradeService.changeOrder(limitOrder);

    assertThat(orderId).isEqualTo("c6f055d9-7f21-4079-913d-e6523a9cfffa");
  }

  @Test
  public void testCancelBybitOrder() throws IOException {
    initPostStub("/v5/order/cancel", "/cancelOrder.json5");

    Instrument BTC_USDT_PERP = new FuturesContract("BTC/USDT/PERP");
    try {
      bybitTradeService.cancelOrder(new BybitCancelOrderParams(BTC_USDT, "", ""));
      fail("Expected UnsupportedOperationException");
    } catch (UnsupportedOperationException ignored) {
    }
    boolean resultSpot = bybitTradeService.cancelOrder(
        new BybitCancelOrderParams(BTC_USDT, "c6f055d9-7f21-4079-913d-e6523a9cfffa", ""));
    boolean resultFuture0 = bybitTradeService.cancelOrder(
        new BybitCancelOrderParams(BTC_USDT_PERP, "c6f055d9-7f21-4079-913d-e6523a9cfffa", ""));
    boolean resultFuture1 = bybitTradeService.cancelOrder(
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
      bybitTradeService.cancelAllOrders(new BybitCancelAllOrdersParams(
          null, null));
      fail("Expected UnsupportedOperationException");
    } catch (UnsupportedOperationException ignored) {

    }
    try {
      bybitTradeService.cancelAllOrders(new BybitCancelAllOrdersParams(BybitCategory.LINEAR, null));
      fail("Expected UnsupportedOperationException");
    } catch (UnsupportedOperationException ignored) {

    }
    Collection<String> resultSpot = bybitTradeService.cancelAllOrders(
        new BybitCancelAllOrdersParams(
            BybitCategory.SPOT, null));

    Collection<String> resultFuture0 = bybitTradeService.cancelAllOrders(
        new BybitCancelAllOrdersParams(BybitCategory.LINEAR, BTC_USDT_PERP));

    assertThat(resultSpot.stream().findFirst().get()).isEqualTo("1616024329462743808");
    assertThat(resultFuture0.stream().findFirst().get()).isEqualTo("1616024329462743808");

  }

}
