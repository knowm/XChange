package org.knowm.xchange.btcmarkets.service;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.btcmarkets.BTCMarketsAuthenticated;
import org.knowm.xchange.btcmarkets.BTCMarketsExchange;
import org.knowm.xchange.btcmarkets.BtcMarketsAssert;
import org.knowm.xchange.btcmarkets.dto.BTCMarketsException;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsCancelOrderRequest;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsCancelOrderResponse;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsMyTradingRequest;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrder;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrders;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsPlaceOrderResponse;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsTradeHistory;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsUserTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import si.mazi.rescu.SynchronizedValueFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest({BTCMarketsAuthenticated.class, BTCMarketsMyTradingRequest.class, BTCMarketsCancelOrderRequest.class, BTCMarketsOrder.class})
@PowerMockIgnore("javax.crypto.*")
public class BTCMarketsTradeServiceTest extends BTCMarketsTestSupport {

  private BTCMarketsExchange exchange;
  private BTCMarketsTradeService marketsTradeService;

  @Before
  public void setUp() {
    exchange = (BTCMarketsExchange) ExchangeFactory.INSTANCE.createExchange(BTCMarketsExchange.class.getCanonicalName());

    exchange.getExchangeSpecification().getExchangeSpecificParameters().put(BTCMarketsExchange.CURRENCY_PAIR, CurrencyPair.BTC_AUD);
    exchange.getExchangeSpecification().setUserName(SPECIFICATION_USERNAME);
    exchange.getExchangeSpecification().setApiKey(SPECIFICATION_API_KEY);
    exchange.getExchangeSpecification().setSecretKey(SPECIFICATION_SECRET_KEY);

    marketsTradeService = new BTCMarketsTradeService(exchange);
  }

  @Test
  public void shouldConstructObject() {
    assertThat(Whitebox.getInternalState(marketsTradeService, "currencyPair")).isEqualTo(CurrencyPair.BTC_AUD);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldFailToConstructObjectOnCorruptedContext() {
    // given
    exchange.getExchangeSpecification().getExchangeSpecificParameters().put(BTCMarketsExchange.CURRENCY_PAIR, "BTC/AUD");

    // when
    marketsTradeService = new BTCMarketsTradeService(exchange);

    // then
    fail("BTCMarketsTradeService should throw IllegalArgumentException when exchange context contains CurrencyPair parameter of wrong type");
  }

  @Test
  public void shouldPlaceMarketOrder() throws IOException {
    // given
    MarketOrder marketOrder = new MarketOrder(Order.OrderType.BID, new BigDecimal("10.00000000"), CurrencyPair.BTC_AUD);

    BTCMarketsOrder btcMarketsOrder = new BTCMarketsOrder(new BigDecimal("10.00000000"), BigDecimal.ZERO, "AUD", "BTC", BTCMarketsOrder.Side.Bid,
        BTCMarketsOrder.Type.Market, "generatedReqId");

    BTCMarketsPlaceOrderResponse orderResponse = new BTCMarketsPlaceOrderResponse(true, null, 0, "11111", 12345);

    BTCMarketsAuthenticated btcm = mock(BTCMarketsAuthenticated.class);
    PowerMockito.when(btcm.placeOrder(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(SynchronizedValueFactory.class),
        Mockito.any(BTCMarketsDigest.class), Mockito.refEq(btcMarketsOrder, "clientRequestId"))).thenReturn(orderResponse);

    Whitebox.setInternalState(marketsTradeService, "btcm", btcm);

    // when
    String placed = marketsTradeService.placeMarketOrder(marketOrder);

    // then
    assertThat(placed).isEqualTo("12345");
  }

  @Test
  public void shouldPlaceLimitOrder() throws IOException {
    // given
    LimitOrder limitOrder = new LimitOrder(Order.OrderType.ASK, new BigDecimal("10.00000000"), CurrencyPair.BTC_AUD, "11111", new Date(1234567890L),
        new BigDecimal("20.00000000"));

    BTCMarketsOrder btcMarketsOrder = new BTCMarketsOrder(new BigDecimal("10.00000000"), new BigDecimal("20.00000000"), "AUD", "BTC",
        BTCMarketsOrder.Side.Ask, BTCMarketsOrder.Type.Limit, "generatedReqId");

    BTCMarketsPlaceOrderResponse orderResponse = new BTCMarketsPlaceOrderResponse(true, null, 0, "11111", 12345);

    BTCMarketsAuthenticated btcm = mock(BTCMarketsAuthenticated.class);
    PowerMockito.when(btcm.placeOrder(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(SynchronizedValueFactory.class),
        Mockito.any(BTCMarketsDigest.class), Mockito.refEq(btcMarketsOrder, "clientRequestId"))).thenReturn(orderResponse);

    Whitebox.setInternalState(marketsTradeService, "btcm", btcm);

    // when
    String placed = marketsTradeService.placeLimitOrder(limitOrder);

    // then
    assertThat(placed).isEqualTo("12345");
  }

  @Test
  public void shouldCancelOrder() throws IOException {
    // given
    BTCMarketsCancelOrderRequest cancelOrderRequest = new BTCMarketsCancelOrderRequest(111L);

    BTCMarketsCancelOrderResponse orderResponse = new BTCMarketsCancelOrderResponse(true, null, 0,
        Arrays.asList(new BTCMarketsException(true, null, 0, "12345", 111L, null)));

    BTCMarketsAuthenticated btcm = mock(BTCMarketsAuthenticated.class);
    PowerMockito.when(btcm.cancelOrder(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(SynchronizedValueFactory.class),
        Mockito.any(BTCMarketsDigest.class), Mockito.refEq(cancelOrderRequest))).thenReturn(orderResponse);
    Whitebox.setInternalState(marketsTradeService, "btcm", btcm);

    // when
    boolean cancelled = marketsTradeService.cancelOrder("111");

    // then
    assertThat(cancelled).isTrue();
  }

  @Test
  public void shouldGetTradeHistory() throws Exception {
    // given
    final List<BTCMarketsUserTrade> expectedBtcMarketsUserTrades = expectedBtcMarketsUserTrades();
    final UserTrade[] expectedUserTrades = expectedUserTrades();

    BTCMarketsMyTradingRequest defaultRequest = new BTCMarketsMyTradingRequest("AUD", "BTC", null, null);
    BTCMarketsMyTradingRequest pagingRequest = new BTCMarketsMyTradingRequest("AUD", "BTC", 120, null);
    BTCMarketsMyTradingRequest timeSpanRequest = new BTCMarketsMyTradingRequest("AUD", "BTC", null, new Date(1234567890L));

    BTCMarketsTradeHistory defaultResponse = Whitebox.invokeConstructor(BTCMarketsTradeHistory.class,
        new Class[]{Boolean.class, String.class, Integer.class, List.class},
        new Object[]{true, "", 0, Collections.singletonList(expectedBtcMarketsUserTrades.get(0))});

    BTCMarketsTradeHistory pagingResponse = Whitebox.invokeConstructor(BTCMarketsTradeHistory.class,
        new Class[]{Boolean.class, String.class, Integer.class, List.class}, new Object[]{true, "", 0, expectedBtcMarketsUserTrades.subList(1, 3)});

    BTCMarketsTradeHistory timeSpanResponse = Whitebox.invokeConstructor(BTCMarketsTradeHistory.class,
        new Class[]{Boolean.class, String.class, Integer.class, List.class}, new Object[]{true, "", 0, expectedBtcMarketsUserTrades.subList(2, 4)});

    BTCMarketsAuthenticated btcm = mock(BTCMarketsAuthenticated.class);

    PowerMockito.when(btcm.getTradeHistory(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(SynchronizedValueFactory.class),
        Mockito.any(BTCMarketsDigest.class), Mockito.refEq(defaultRequest))).thenReturn(defaultResponse);
    PowerMockito.when(btcm.getTradeHistory(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(SynchronizedValueFactory.class),
        Mockito.any(BTCMarketsDigest.class), Mockito.refEq(pagingRequest))).thenReturn(pagingResponse);
    PowerMockito.when(btcm.getTradeHistory(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(SynchronizedValueFactory.class),
        Mockito.any(BTCMarketsDigest.class), Mockito.refEq(timeSpanRequest))).thenReturn(timeSpanResponse);

    Whitebox.setInternalState(marketsTradeService, "btcm", btcm);

    // when
    UserTrades defaultTrades = marketsTradeService.getTradeHistory(new DefaultTradeHistoryParamCurrencyPair());
    UserTrades pagingTrades = marketsTradeService.getTradeHistory(new DefaultTradeHistoryParamPaging(120));
    UserTrades timeSpanTrades = marketsTradeService.getTradeHistory(new DefaultTradeHistoryParamsTimeSpan(new Date(1234567890L)));

    List<UserTrade> defaultUserTrades = defaultTrades.getUserTrades();
    List<UserTrade> pagingUserTrades = pagingTrades.getUserTrades();
    List<UserTrade> timeSpanUserTrades = timeSpanTrades.getUserTrades();

    // then
    assertThat(defaultUserTrades).hasSize(1);
    BtcMarketsAssert.assertEquals(defaultUserTrades.get(0), expectedUserTrades[0]);

    assertThat(pagingUserTrades).hasSize(2);
    for (int i = 0; i < pagingUserTrades.size(); i++) {
      BtcMarketsAssert.assertEquals(pagingUserTrades.get(i), expectedUserTrades[i + 1]);
    }

    assertThat(timeSpanUserTrades).hasSize(2);
    for (int i = 0; i < timeSpanUserTrades.size(); i++) {
      BtcMarketsAssert.assertEquals(timeSpanUserTrades.get(i), expectedUserTrades[i + 2]);
    }
  }

  @Test
  public void shouldGetOpenOrders() throws Exception {
    // given
    final BTCMarketsOrder[] expectedBtcMarketsOrders = expectedBtcMarketsOrders();
    final LimitOrder[] expectedOrders = expectedOrders();

    BTCMarketsMyTradingRequest request = new BTCMarketsMyTradingRequest("AUD", "BTC", 50, null);
    BTCMarketsOrders response = Whitebox.invokeConstructor(BTCMarketsOrders.class,
        new Class[]{Boolean.class, String.class, Integer.class, List.class},
        new Object[]{true, "", 0, Arrays.asList(expectedBtcMarketsOrders[0], expectedBtcMarketsOrders[1])});

    BTCMarketsAuthenticated btcm = mock(BTCMarketsAuthenticated.class);
    PowerMockito.when(btcm.getOpenOrders(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(SynchronizedValueFactory.class),
        Mockito.any(BTCMarketsDigest.class), Mockito.refEq(request))).thenReturn(response);

    Whitebox.setInternalState(marketsTradeService, "btcm", btcm);

    // when
    OpenOrders openOrders = marketsTradeService.getOpenOrders();
    List<LimitOrder> ordersList = openOrders.getOpenOrders();

    // then
    assertThat(ordersList).hasSize(2);

    for (int i = 0; i < ordersList.size(); i++) {
      BtcMarketsAssert.assertEquals(ordersList.get(i), expectedOrders[i]);
    }
  }

  @Test
  public void shouldCreateHistoryParams() {
    // when
    BTCMarketsTradeService.HistoryParams historyParams = marketsTradeService.createTradeHistoryParams();

    // then
    assertThat(historyParams.getPageLength()).isEqualTo(100);
    assertThat(historyParams.getStartTime()).isNull();
  }
}
