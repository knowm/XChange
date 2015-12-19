package com.xeiam.xchange.btcmarkets.service.polling;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.btcmarkets.BTCMarketsAuthenticated;
import com.xeiam.xchange.btcmarkets.BTCMarketsExchange;
import com.xeiam.xchange.btcmarkets.dto.BTCMarketsException;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsCancelOrderRequest;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsCancelOrderResponse;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsMyTradingRequest;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsOrder;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsOrders;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsPlaceOrderResponse;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsTradeHistory;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsUserTrade;
import com.xeiam.xchange.btcmarkets.service.BTCMarketsDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamCurrencyPair;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mock;

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
    exchange.getExchangeSpecification().setUserName("admin");
    exchange.getExchangeSpecification().setApiKey("publicKey");
    exchange.getExchangeSpecification().setSecretKey("secretKey");

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

    BTCMarketsOrder btcMarketsOrder = new BTCMarketsOrder(
        new BigDecimal("10.00000000"), BigDecimal.ZERO, "AUD", "BTC", BTCMarketsOrder.Side.Bid,
        BTCMarketsOrder.Type.Market, "generatedReqId");

    BTCMarketsPlaceOrderResponse orderResponse = new BTCMarketsPlaceOrderResponse(true, null, 0, "11111", 12345);

    BTCMarketsAuthenticated btcm = mock(BTCMarketsAuthenticated.class);
    PowerMockito.when(btcm.placeOrder(
        Mockito.eq("publicKey"), Mockito.any(SynchronizedValueFactory.class),
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
    LimitOrder limitOrder = new LimitOrder(Order.OrderType.ASK, new BigDecimal("10.00000000"),
        CurrencyPair.BTC_AUD, "11111", new Date(1234567890L), new BigDecimal("20.00000000"));

    BTCMarketsOrder btcMarketsOrder = new BTCMarketsOrder(
        new BigDecimal("10.00000000"), new BigDecimal("20.00000000"), "AUD", "BTC",
        BTCMarketsOrder.Side.Ask, BTCMarketsOrder.Type.Limit, "generatedReqId");

    BTCMarketsPlaceOrderResponse orderResponse = new BTCMarketsPlaceOrderResponse(true, null, 0, "11111", 12345);

    BTCMarketsAuthenticated btcm = mock(BTCMarketsAuthenticated.class);
    PowerMockito.when(btcm.placeOrder(
        Mockito.eq("publicKey"), Mockito.any(SynchronizedValueFactory.class),
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
    PowerMockito.when(btcm.cancelOrder(Mockito.eq("publicKey"), Mockito.any(SynchronizedValueFactory.class),
        Mockito.any(BTCMarketsDigest.class), Mockito.refEq(cancelOrderRequest))).thenReturn(
        orderResponse);
    Whitebox.setInternalState(marketsTradeService, "btcm", btcm);

    // when
    boolean cancelled = marketsTradeService.cancelOrder("111");

    // then
    assertThat(cancelled).isTrue();
  }


  @Test
  public void shouldGetTradeHistory() throws Exception {
    // given
    BTCMarketsMyTradingRequest defaultRequest = new BTCMarketsMyTradingRequest("AUD", "BTC", null, null);
    BTCMarketsMyTradingRequest pagingRequest = new BTCMarketsMyTradingRequest("AUD", "BTC", 120, null);
    BTCMarketsMyTradingRequest timeSpanRequest = new BTCMarketsMyTradingRequest("AUD", "BTC", null, new Date(1234567890L));

    BTCMarketsUserTrade userTrade1 = createBTCMarketsUserTrade("trade 1", new BigDecimal("10.00000000"),
        new BigDecimal("20.00000000"), new BigDecimal("1"), new Date(111111111L), BTCMarketsOrder.Side.Ask);
    BTCMarketsUserTrade userTrade2 = createBTCMarketsUserTrade("trade 2", new BigDecimal("30.00000000"),
        new BigDecimal("40.00000000"), new BigDecimal("2"), new Date(222222222L), BTCMarketsOrder.Side.Ask);
    BTCMarketsUserTrade userTrade3 = createBTCMarketsUserTrade("trade 3", new BigDecimal("50.00000000"),
        new BigDecimal("60.00000000"), new BigDecimal("3"), new Date(333333333L), BTCMarketsOrder.Side.Bid);
    BTCMarketsUserTrade userTrade4 = createBTCMarketsUserTrade("trade 4", new BigDecimal("70.00000000"),
        new BigDecimal("80.00000000"), new BigDecimal("4"), new Date(444444444L), BTCMarketsOrder.Side.Bid);
    BTCMarketsUserTrade userTrade5 = createBTCMarketsUserTrade("trade 5", new BigDecimal("90.00000000"),
        BigDecimal.ZERO, new BigDecimal("5"), new Date(555555555L), BTCMarketsOrder.Side.Bid);

    Whitebox.setInternalState(userTrade1, "id", 1L);
    Whitebox.setInternalState(userTrade2, "id", 2L);
    Whitebox.setInternalState(userTrade3, "id", 3L);
    Whitebox.setInternalState(userTrade4, "id", 4L);
    Whitebox.setInternalState(userTrade5, "id", 5L);

    BTCMarketsTradeHistory defaultResponse = Whitebox.invokeConstructor(BTCMarketsTradeHistory.class,
        new Class[]{Boolean.class, String.class, Integer.class, List.class},
        new Object[]{true, "", 0, Arrays.asList(userTrade1)});

    BTCMarketsTradeHistory pagingResponse = Whitebox.invokeConstructor(BTCMarketsTradeHistory.class,
        new Class[]{Boolean.class, String.class, Integer.class, List.class},
        new Object[]{true, "", 0, Arrays.asList(userTrade2, userTrade3)});

    BTCMarketsTradeHistory timeSpanResponse = Whitebox.invokeConstructor(BTCMarketsTradeHistory.class,
        new Class[]{Boolean.class, String.class, Integer.class, List.class},
        new Object[]{true, "", 0, Arrays.asList(userTrade3, userTrade4, userTrade5)});

    BTCMarketsAuthenticated btcm = mock(BTCMarketsAuthenticated.class);

    PowerMockito.when(btcm.getTradeHistory(Mockito.eq("publicKey"), Mockito.any(SynchronizedValueFactory.class),
        Mockito.any(BTCMarketsDigest.class), Mockito.refEq(defaultRequest))).thenReturn(defaultResponse);
    PowerMockito.when(btcm.getTradeHistory(Mockito.eq("publicKey"), Mockito.any(SynchronizedValueFactory.class),
        Mockito.any(BTCMarketsDigest.class), Mockito.refEq(pagingRequest))).thenReturn(pagingResponse);
    PowerMockito.when(btcm.getTradeHistory(Mockito.eq("publicKey"), Mockito.any(SynchronizedValueFactory.class),
        Mockito.any(BTCMarketsDigest.class), Mockito.refEq(timeSpanRequest))).thenReturn(timeSpanResponse);

    Whitebox.setInternalState(marketsTradeService, "btcm", btcm);

    // when
    UserTrades defaultTrades = marketsTradeService.getTradeHistory(new DefaultTradeHistoryParamCurrencyPair());
    UserTrades pagingTrades = marketsTradeService.getTradeHistory(new DefaultTradeHistoryParamPaging(120));
    UserTrades timeSpanTrades = marketsTradeService.getTradeHistory(new DefaultTradeHistoryParamsTimeSpan(new Date(1234567890L)));

    // then
    assertThat(defaultTrades.toString()).isEqualTo(String.format("Trades\n" + "lastID= 0\n"
        + "[trade=UserTrade[type=ASK, tradableAmount=20.00000000, currencyPair=BTC/AUD, price=10.00000000, "
        + "timestamp=%s, id=1, orderId='null', feeAmount=1, feeCurrency='AUD']]\n", new Date(111111111L)));

    assertThat(pagingTrades.toString()).isEqualTo(String.format("Trades\n" + "lastID= 0\n"
        + "[trade=UserTrade[type=ASK, tradableAmount=40.00000000, currencyPair=BTC/AUD, price=30.00000000, "
            + "timestamp=%s, id=2, orderId='null', feeAmount=2, feeCurrency='AUD']]\n"
        + "[trade=UserTrade[type=BID, tradableAmount=60.00000000, currencyPair=BTC/AUD, price=50.00000000, "
            + "timestamp=%s, id=3, orderId='null', feeAmount=3, feeCurrency='AUD']]\n",
        new Date(222222222L), new Date(333333333L)));

    assertThat(timeSpanTrades.toString()).isEqualTo(String.format("Trades\n" + "lastID= 0\n"
            + "[trade=UserTrade[type=BID, tradableAmount=60.00000000, currencyPair=BTC/AUD, price=50.00000000, "
            + "timestamp=%s, id=3, orderId='null', feeAmount=3, feeCurrency='AUD']]\n"
            + "[trade=UserTrade[type=BID, tradableAmount=80.00000000, currencyPair=BTC/AUD, price=70.00000000, "
            + "timestamp=%s, id=4, orderId='null', feeAmount=4, feeCurrency='AUD']]\n"
            + "[trade=UserTrade[type=BID, tradableAmount=0, currencyPair=BTC/AUD, price=90.00000000, "
            + "timestamp=%s, id=5, orderId='null', feeAmount=5, feeCurrency='AUD']]\n",
        new Date(333333333L), new Date(444444444L), new Date(555555555L)));
  }


  @Test
  public void shouldGetOpenOrders() throws Exception {
    // given
    BTCMarketsMyTradingRequest request = new BTCMarketsMyTradingRequest("AUD", "BTC", 50, null);

    BTCMarketsOrder marketsOrder1 = new BTCMarketsOrder(new BigDecimal("10.00000000"),
        new BigDecimal("20.00000000"), "AUD", "BTC", BTCMarketsOrder.Side.Ask, BTCMarketsOrder.Type.Market, "11111");
    BTCMarketsOrder marketsOrder2 = new BTCMarketsOrder(new BigDecimal("30.00000000"),
        new BigDecimal("40.00000000"), "AUD", "BTC", BTCMarketsOrder.Side.Bid, BTCMarketsOrder.Type.Limit, "22222");

    Whitebox.setInternalState(marketsOrder1, "id", 1L);
    Whitebox.setInternalState(marketsOrder2, "id", 2L);

    BTCMarketsOrders response = Whitebox.invokeConstructor(BTCMarketsOrders.class,
        new Class[]{Boolean.class, String.class, Integer.class, List.class},
        new Object[]{true, "", 0, Arrays.asList(marketsOrder1, marketsOrder2)});

    BTCMarketsAuthenticated btcm = mock(BTCMarketsAuthenticated.class);
    PowerMockito.when(btcm.getOpenOrders(Mockito.eq("publicKey"), Mockito.any(SynchronizedValueFactory.class),
        Mockito.any(BTCMarketsDigest.class), Mockito.refEq(request))).thenReturn(response);

    Whitebox.setInternalState(marketsTradeService, "btcm", btcm);

    // when
    OpenOrders openOrders = marketsTradeService.getOpenOrders();
    List<LimitOrder> ordersList = openOrders.getOpenOrders();

    // then
    assertThat(ordersList).hasSize(2);
    assertThat(ordersList.get(0).toString()).isEqualTo(
        "LimitOrder [limitPrice=20.00000000, Order [type=ASK, tradableAmount=10.00000000, currencyPair=BTC/AUD, id=1, timestamp=null]]");
    assertThat(ordersList.get(1).toString()).isEqualTo(
        "LimitOrder [limitPrice=40.00000000, Order [type=BID, tradableAmount=30.00000000, currencyPair=BTC/AUD, id=2, timestamp=null]]");
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
