package org.knowm.xchange.btcmarkets.service;

// Note:
//    I tried my best to get powermock to work after adding a logback.xml file to suppress verbose
// logging but there are some Java 9* issues
//    arising with powermock that I just cannot resolve. Therefore I decided to comment out all the
// powermock code in this module since it's the
//    only module using powermock. The dependencies have also been removed from the project.
// Hopefully someone will upgrade all these test to use
//    Mokito.
// import static org.assertj.core.api.Assertions.assertThat;
// import static org.powermock.api.mockito.PowerMockito.mock;
//
// import java.io.IOException;
// import java.math.BigDecimal;
// import java.util.*;
// import org.junit.Before;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.knowm.xchange.ExchangeFactory;
// import org.knowm.xchange.btcmarkets.BTCMarketsAuthenticated;
// import org.knowm.xchange.btcmarkets.BTCMarketsAuthenticatedV3;
// import org.knowm.xchange.btcmarkets.BTCMarketsExchange;
// import org.knowm.xchange.btcmarkets.dto.BTCMarketsException;
// import org.knowm.xchange.btcmarkets.dto.trade.*;
// import org.knowm.xchange.btcmarkets.dto.v3.trade.BTCMarketsPlaceOrderRequest;
// import org.knowm.xchange.btcmarkets.dto.v3.trade.BTCMarketsPlaceOrderResponse;
// import org.knowm.xchange.currency.CurrencyPair;
// import org.knowm.xchange.dto.Order;
// import org.knowm.xchange.dto.trade.LimitOrder;
// import org.knowm.xchange.dto.trade.MarketOrder;
// import org.mockito.Matchers;
// import org.mockito.Mockito;
// import org.powermock.api.mockito.PowerMockito;
// import org.powermock.core.classloader.annotations.PowerMockIgnore;
// import org.powermock.core.classloader.annotations.PrepareForTest;
// import org.powermock.modules.junit4.PowerMockRunner;
// import org.powermock.reflect.Whitebox;
// import si.mazi.rescu.SynchronizedValueFactory;
//
// @RunWith(PowerMockRunner.class)
// @PrepareForTest({
//  BTCMarketsAuthenticated.class,
//  BTCMarketsOpenOrdersRequest.class,
//  BTCMarketsCancelOrderRequest.class,
//  BTCMarketsOrder.class
// })
// @PowerMockIgnore("javax.crypto.*")
public class BTCMarketsTradeServiceTest extends BTCMarketsTestSupport {

  //  private BTCMarketsExchange exchange;
  //  private BTCMarketsTradeService marketsTradeService;
  //
  //  @Before
  //  public void setUp() {
  //    exchange =
  //        (BTCMarketsExchange)
  //
  // ExchangeFactory.INSTANCE.createExchange(BTCMarketsExchange.class.getCanonicalName());
  //
  //    exchange.getExchangeSpecification().getExchangeSpecificParameters();
  //    exchange.getExchangeSpecification().setUserName(SPECIFICATION_USERNAME);
  //    exchange.getExchangeSpecification().setApiKey(SPECIFICATION_API_KEY);
  //    exchange.getExchangeSpecification().setSecretKey(SPECIFICATION_SECRET_KEY);
  //
  //    marketsTradeService = new BTCMarketsTradeService(exchange);
  //  }
  //
  //  @Test
  //  public void shouldPlaceMarketOrder() throws IOException {
  //    // given
  //    MarketOrder marketOrder =
  //        new MarketOrder(Order.OrderType.BID, new BigDecimal("10.00000000"),
  // CurrencyPair.BTC_AUD);
  //
  //    BTCMarketsPlaceOrderRequest btcMarketsOrder =
  //        new BTCMarketsPlaceOrderRequest(
  //            "BTC-AUD", "0", "10.00000000", "Market", "Bid", null, null, "GTC", null, null,
  // null);
  //
  //    BTCMarketsPlaceOrderResponse orderResponse = new BTCMarketsPlaceOrderResponse("11111");
  //
  //    BTCMarketsAuthenticatedV3 btcm = mock(BTCMarketsAuthenticatedV3.class);
  //    PowerMockito.when(
  //            btcm.placeOrder(
  //                Mockito.eq(SPECIFICATION_API_KEY),
  //                Mockito.any(SynchronizedValueFactory.class),
  //                Mockito.any(BTCMarketsDigestV3.class),
  //                Mockito.refEq(btcMarketsOrder)))
  //        .thenReturn(orderResponse);
  //
  //    Whitebox.setInternalState(marketsTradeService, "btcmv3", btcm);
  //
  //    // when
  //    String placed = marketsTradeService.placeMarketOrder(marketOrder);
  //
  //    // then
  //    assertThat(placed).isEqualTo("11111");
  //  }
  //
  //  @Test
  //  public void shouldPlaceLimitOrder() throws IOException {
  //    // given
  //    LimitOrder limitOrder =
  //        new LimitOrder(
  //            Order.OrderType.ASK,
  //            new BigDecimal("10.00000000"),
  //            CurrencyPair.BTC_AUD,
  //            "11111",
  //            new Date(1234567890L),
  //            new BigDecimal("20.00000000"));
  //
  //    BTCMarketsPlaceOrderRequest request =
  //        new BTCMarketsPlaceOrderRequest(
  //            "BTC-AUD",
  //            "20.00000000",
  //            "10.00000000",
  //            "Limit",
  //            "Ask",
  //            null,
  //            null,
  //            "GTC",
  //            null,
  //            null,
  //            null);
  //
  //    BTCMarketsPlaceOrderResponse orderResponse = new BTCMarketsPlaceOrderResponse("11111");
  //
  //    BTCMarketsAuthenticatedV3 btcm = mock(BTCMarketsAuthenticatedV3.class);
  //    PowerMockito.when(
  //            btcm.placeOrder(
  //                Mockito.eq(SPECIFICATION_API_KEY),
  //                Mockito.any(SynchronizedValueFactory.class),
  //                Mockito.any(BTCMarketsDigestV3.class),
  //                Mockito.refEq(request)))
  //        .thenReturn(orderResponse);
  //
  //    Whitebox.setInternalState(marketsTradeService, "btcmv3", btcm);
  //
  //    // when
  //    String placed = marketsTradeService.placeLimitOrder(limitOrder);
  //
  //    // then
  //    assertThat(placed).isEqualTo("11111");
  //  }
  //
  //  @Test
  //  public void shouldCancelOrder() throws IOException {
  //    // given
  //    BTCMarketsCancelOrderRequest cancelOrderRequest = new BTCMarketsCancelOrderRequest(111L);
  //
  //    BTCMarketsCancelOrderResponse orderResponse =
  //        new BTCMarketsCancelOrderResponse(
  //            true,
  //            null,
  //            0,
  //            Arrays.asList(new BTCMarketsException(true, null, 0, "12345", 111L, null)));
  //
  //    BTCMarketsAuthenticated btcm = mock(BTCMarketsAuthenticated.class);
  //    PowerMockito.when(
  //            btcm.cancelOrder(
  //                Mockito.eq(SPECIFICATION_API_KEY),
  //                Mockito.any(SynchronizedValueFactory.class),
  //                Mockito.any(BTCMarketsDigest.class),
  //                Mockito.refEq(cancelOrderRequest)))
  //        .thenReturn(orderResponse);
  //    Whitebox.setInternalState(marketsTradeService, "btcm", btcm);
  //
  //    // when
  //    boolean cancelled = marketsTradeService.cancelOrder("111");
  //
  //    // then
  //    assertThat(cancelled).isTrue();
  //  }
  //
  //  @Test
  //  public void shouldCreateHistoryParams() {
  //    // when
  //    BTCMarketsTradeService.HistoryParams historyParams =
  //        marketsTradeService.createTradeHistoryParams();
  //
  //    // then
  //    assertThat(historyParams.getPageLength()).isEqualTo(200);
  //  }
  //
  //  @Test
  //  public void shouldGetOrderDetails() throws IOException {
  //    // given
  //    List<Long> orderIds = new ArrayList<>();
  //    orderIds.add(1000L);
  //    BTCMarketsOrderDetailsRequest btcMarketsOrderDetailsRequest =
  //        new BTCMarketsOrderDetailsRequest(orderIds);
  //    BTCMarketsOrders btcMarketsOrders = new BTCMarketsOrders(true, "", 0, new ArrayList<>());
  //
  //    BTCMarketsAuthenticated btcm = mock(BTCMarketsAuthenticated.class);
  //    PowerMockito.when(
  //            btcm.getOrderDetails(
  //                Mockito.eq(SPECIFICATION_API_KEY),
  //                Mockito.any(SynchronizedValueFactory.class),
  //                Mockito.any(BTCMarketsDigest.class),
  //                Matchers.eq(btcMarketsOrderDetailsRequest)))
  //        .thenReturn(btcMarketsOrders);
  //    Whitebox.setInternalState(marketsTradeService, "btcm", btcm);
  //    // when
  //    Collection<Order> orders = marketsTradeService.getOrder("1000");
  //    assertThat(orders).hasSize(0);
  //  }
}
