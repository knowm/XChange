package org.knowm.xchange.bleutrade.service;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import static org.knowm.xchange.bleutrade.BleutradeAssert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bleutrade.BleutradeAuthenticated;
import org.knowm.xchange.bleutrade.BleutradeException;
import org.knowm.xchange.bleutrade.BleutradeExchange;
import org.knowm.xchange.bleutrade.dto.trade.BleutradeCancelOrderReturn;
import org.knowm.xchange.bleutrade.dto.trade.BleutradeOpenOrder;
import org.knowm.xchange.bleutrade.dto.trade.BleutradeOpenOrdersReturn;
import org.knowm.xchange.bleutrade.dto.trade.BleutradePlaceOrderReturn;
import org.knowm.xchange.bleutrade.dto.trade.BluetradeExecutedTrade;
import org.knowm.xchange.bleutrade.dto.trade.BluetradeExecutedTradesWrapper;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@RunWith(PowerMockRunner.class)
public class BleutradeTradeServiceIntegration extends BleutradeServiceTestSupport {

  private BleutradeTradeService tradeService;

  @Before
  public void setUp() {
    BleutradeExchange exchange = (BleutradeExchange) ExchangeFactory.INSTANCE.createExchange(BleutradeExchange.class.getCanonicalName());
    ExchangeSpecification specification = exchange.getExchangeSpecification();
    specification.setUserName(SPECIFICATION_USERNAME);
    specification.setApiKey(SPECIFICATION_API_KEY);
    specification.setSecretKey(SPECIFICATION_SECRET_KEY);

    tradeService = new BleutradeTradeService(exchange);
  }

  @Test
  public void constructor() {
    assertThat(Whitebox.getInternalState(tradeService, "apiKey")).isEqualTo(SPECIFICATION_API_KEY);
  }

  @Test
  public void shouldGetOpenOrders() throws IOException {
    // given
    BleutradeOpenOrdersReturn openOrdersReturn = new BleutradeOpenOrdersReturn();
    openOrdersReturn.setSuccess(true);
    openOrdersReturn.setMessage("test message");
    openOrdersReturn.setResult(expectedBleutradeOpenOrdersList());

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito
        .when(bleutrade.getOrders(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class)))
        .thenReturn(openOrdersReturn);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    final LimitOrder[] expectedOrders = expectedOrders();

    // when
    OpenOrders openOrders = tradeService.getOpenOrders();
    List<LimitOrder> ordersList = openOrders.getOpenOrders();

    // then
    assertThat(ordersList).hasSize(2);

    for (int i = 0; i < ordersList.size(); i++) {
      assertEquals(ordersList.get(i), expectedOrders[i]);
    }
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulGetOpenOrders() throws IOException {
    // given
    BleutradeOpenOrdersReturn openOrdersReturn = new BleutradeOpenOrdersReturn();
    openOrdersReturn.setSuccess(false);
    openOrdersReturn.setMessage("test message");
    openOrdersReturn.setResult(expectedBleutradeOpenOrdersList());

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito
        .when(bleutrade.getOrders(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class)))
        .thenReturn(openOrdersReturn);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    tradeService.getOpenOrders();

    // then
    fail("BleutradeAccountService should throw ExchangeException when open orders request was unsuccessful");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnGetOpenOrdersError() throws IOException {
    // given
    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito
        .when(bleutrade.getOrders(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class)))
        .thenThrow(BleutradeException.class);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    tradeService.getOpenOrders();

    // then
    fail("BleutradeAccountService should throw ExchangeException when open orders request throw error");
  }

  @Test(expected = NotAvailableFromExchangeException.class)
  public void shouldFailOnPlaceMarketOrder() throws IOException {
    // when
    tradeService.placeMarketOrder(new MarketOrder(Order.OrderType.ASK, BigDecimal.TEN, CurrencyPair.BTC_AUD));

    // then
    fail("BleutradeAccountService should throw NotAvailableFromExchangeException when placeMarketOrder is called");
  }

  @Test
  public void shouldProcessPlaceLimitOrder() throws IOException {
    // given
    BleutradePlaceOrderReturn placeBuyOrderReturn = new BleutradePlaceOrderReturn();
    placeBuyOrderReturn.setSuccess(true);
    placeBuyOrderReturn.setMessage("test message");
    placeBuyOrderReturn.setResult(createBleutradeOrderId("11111"));

    BleutradePlaceOrderReturn placeSellOrderReturn = new BleutradePlaceOrderReturn();
    placeSellOrderReturn.setSuccess(true);
    placeSellOrderReturn.setMessage("test message");
    placeSellOrderReturn.setResult(createBleutradeOrderId("22222"));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.buyLimit(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC_AUD"), Mockito.eq("10.00000000"), Mockito.eq("1.1")))
        .thenReturn(placeBuyOrderReturn);
    PowerMockito.when(bleutrade.sellLimit(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC_AUD"), Mockito.eq("20.00000000"), Mockito.eq("2.2")))
        .thenReturn(placeSellOrderReturn);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    final LimitOrder[] expectedPlacedOrders = expectedPlacedOrders();

    // when
    String placeBuyLimitOrder = tradeService.placeLimitOrder(expectedPlacedOrders[0]);
    String placeSellLimitOrder = tradeService.placeLimitOrder(expectedPlacedOrders[1]);

    // then
    assertThat(placeBuyLimitOrder).isEqualTo("11111");
    assertThat(placeSellLimitOrder).isEqualTo("22222");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulPlaceBuyLimitOrder() throws IOException {
    // given
    BleutradePlaceOrderReturn placeBuyOrderReturn = new BleutradePlaceOrderReturn();
    placeBuyOrderReturn.setSuccess(false);
    placeBuyOrderReturn.setMessage("test message");
    placeBuyOrderReturn.setResult(createBleutradeOrderId("11111"));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.buyLimit(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC_AUD"), Mockito.eq("10.00000000"), Mockito.eq("1.1")))
        .thenReturn(placeBuyOrderReturn);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    final LimitOrder[] expectedPlacedOrders = expectedPlacedOrders();

    // when
    tradeService.placeLimitOrder(expectedPlacedOrders[0]);

    // then
    fail("BleutradeAccountService should throw ExchangeException on unsuccessful place buy limit order request");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulPlaceSellLimitOrder() throws IOException {
    // given
    BleutradePlaceOrderReturn placeSellOrderReturn = new BleutradePlaceOrderReturn();
    placeSellOrderReturn.setSuccess(false);
    placeSellOrderReturn.setMessage("test message");
    placeSellOrderReturn.setResult(createBleutradeOrderId("22222"));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.sellLimit(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC_AUD"), Mockito.eq("20.00000000"), Mockito.eq("2.2")))
        .thenReturn(placeSellOrderReturn);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    final LimitOrder[] expectedPlacedOrders = expectedPlacedOrders();

    // when
    tradeService.placeLimitOrder(expectedPlacedOrders[1]);

    // then
    fail("BleutradeAccountService should throw ExchangeException on unsuccessful place sell limit order request");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnPlaceBuyLimitOrderError() throws IOException {
    // given
    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.buyLimit(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC_AUD"), Mockito.eq("10.00000000"), Mockito.eq("1.1")))
        .thenThrow(BleutradeException.class);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    final LimitOrder[] expectedPlacedOrders = expectedPlacedOrders();

    // when
    tradeService.placeLimitOrder(expectedPlacedOrders[0]);

    // then
    fail("BleutradeAccountService should throw ExchangeException when place buy limit order request throw error");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnPlaceSellLimitOrderError() throws IOException {
    // given
    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.sellLimit(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC_AUD"), Mockito.eq("20.00000000"), Mockito.eq("2.2")))
        .thenThrow(BleutradeException.class);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    final LimitOrder[] expectedPlacedOrders = expectedPlacedOrders();

    // when
    tradeService.placeLimitOrder(expectedPlacedOrders[1]);

    // then
    fail("BleutradeAccountService should throw ExchangeException when place sell limit order request throw error");
  }

  @Test
  public void shouldProcessCancelOrder() throws IOException {
    // given
    BleutradeCancelOrderReturn cancelOrderReturnPassed = new BleutradeCancelOrderReturn();
    cancelOrderReturnPassed.setSuccess(true);
    cancelOrderReturnPassed.setMessage("test message");
    cancelOrderReturnPassed.setResult(Collections.singletonList("12345"));

    BleutradeCancelOrderReturn cancelOrderReturnFailed = new BleutradeCancelOrderReturn();
    cancelOrderReturnFailed.setSuccess(false);
    cancelOrderReturnFailed.setMessage("test message");
    cancelOrderReturnFailed.setResult(Collections.singletonList("11111"));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.cancel(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("12345"))).thenReturn(cancelOrderReturnPassed);
    PowerMockito.when(bleutrade.cancel(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("11111"))).thenReturn(cancelOrderReturnFailed);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    boolean passed1 = tradeService.cancelOrder("12345");
    boolean passed2 = tradeService.cancelOrder("11111");

    // then
    assertThat(passed1).isTrue();
    assertThat(passed2).isFalse();
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailCancelOrderError() throws IOException {
    // given
    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.cancel(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("12345"))).thenThrow(BleutradeException.class);
    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    tradeService.cancelOrder("12345");

    // then
    fail("BleutradeAccountService should throw ExchangeException on cancel order request error");
  }

  @Test
  public void tradeHistoryShouldRequestAllMarketsIfNoneAreSupplied() throws IOException {
    List<BluetradeExecutedTrade> result = new ArrayList<>();
    result.add(aTrade());

    BluetradeExecutedTradesWrapper response = new BluetradeExecutedTradesWrapper(true, "", result);

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getTrades(
        Mockito.eq(SPECIFICATION_API_KEY),
        Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class),
        Mockito.matches("ALL"),
        Mockito.any(String.class),
        Mockito.any(String.class)
    )).thenReturn(response);

    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    UserTrades tradeHistory = tradeService.getTradeHistory(null);
    assertThat(tradeHistory.getUserTrades()).hasSize(1);
  }

  @Test
  public void tradeHistoryShouldUnderstandMarketParams() throws IOException {
    List<BluetradeExecutedTrade> result = new ArrayList<>();
    result.add(aTrade());

    BluetradeExecutedTradesWrapper response = new BluetradeExecutedTradesWrapper(true, "", result);

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getTrades(
        Mockito.eq(SPECIFICATION_API_KEY),
        Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class),
        Mockito.matches("BTC_AUD"),
        Mockito.matches("status"),
        Mockito.matches("type")
    )).thenReturn(response);

    Whitebox.setInternalState(tradeService, "bleutrade", bleutrade);

    // when
    UserTrades tradeHistory = tradeService.getTradeHistory(new BleutradeTradeServiceRaw.BleutradeTradeHistoryParams(CurrencyPair.BTC_AUD, "status", "type"));
    assertThat(tradeHistory.getUserTrades()).hasSize(1);
  }

  @Test(expected = NotAvailableFromExchangeException.class)
  public void shouldFailOnCreateTradeHistoryParams() {
    // when
    tradeService.createTradeHistoryParams();

    // then
    fail("BleutradeAccountService should throw NotAvailableFromExchangeException when createTradeHistoryParams is called");
  }

  private static BleutradeOpenOrder anOrder() {
    BleutradeOpenOrder order = new BleutradeOpenOrder();
    order.setType("buy");
    order.setExchange("BTC_AUD");
    order.setCreated("2000-01-02 01:02:03.456");
    return order;
  }

  private static BluetradeExecutedTrade aTrade() {
    return new BluetradeExecutedTrade(
        "id",
        "BTC_AUD",
        "buy",
        new BigDecimal("99"),
        "0",
        new BigDecimal("10"),
        "",
        "2000-01-02 01:02:03.456",
        new BigDecimal("44"),
        ""
    );
  }
}
