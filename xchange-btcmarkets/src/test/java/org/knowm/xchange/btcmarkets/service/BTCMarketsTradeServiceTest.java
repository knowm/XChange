package org.knowm.xchange.btcmarkets.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.btcmarkets.BTCMarketsAuthenticated;
import org.knowm.xchange.btcmarkets.BTCMarketsExchange;
import org.knowm.xchange.btcmarkets.dto.BTCMarketsException;
import org.knowm.xchange.btcmarkets.dto.trade.*;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import si.mazi.rescu.SynchronizedValueFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
  BTCMarketsAuthenticated.class,
  BTCMarketsOpenOrdersAndTradeHistoryRequest.class,
  BTCMarketsCancelOrderRequest.class,
  BTCMarketsOrder.class
})
@PowerMockIgnore("javax.crypto.*")
public class BTCMarketsTradeServiceTest extends BTCMarketsTestSupport {

  private BTCMarketsExchange exchange;
  private BTCMarketsTradeService marketsTradeService;

  @Before
  public void setUp() {
    exchange =
        (BTCMarketsExchange)
            ExchangeFactory.INSTANCE.createExchange(BTCMarketsExchange.class.getCanonicalName());

    exchange
        .getExchangeSpecification()
        .getExchangeSpecificParameters()
        .put(BTCMarketsExchange.CURRENCY_PAIR, CurrencyPair.BTC_AUD);
    exchange.getExchangeSpecification().setUserName(SPECIFICATION_USERNAME);
    exchange.getExchangeSpecification().setApiKey(SPECIFICATION_API_KEY);
    exchange.getExchangeSpecification().setSecretKey(SPECIFICATION_SECRET_KEY);

    marketsTradeService = new BTCMarketsTradeService(exchange);
  }

  @Test
  public void shouldPlaceMarketOrder() throws IOException {
    // given
    MarketOrder marketOrder =
        new MarketOrder(Order.OrderType.BID, new BigDecimal("10.00000000"), CurrencyPair.BTC_AUD);

    BTCMarketsOrder btcMarketsOrder =
        new BTCMarketsOrder(
            new BigDecimal("10.00000000"),
            BigDecimal.ZERO,
            "AUD",
            "BTC",
            BTCMarketsOrder.Side.Bid,
            BTCMarketsOrder.Type.Market,
            "generatedReqId");

    BTCMarketsPlaceOrderResponse orderResponse =
        new BTCMarketsPlaceOrderResponse(true, null, 0, "11111", 12345);

    BTCMarketsAuthenticated btcm = mock(BTCMarketsAuthenticated.class);
    PowerMockito.when(
            btcm.placeOrder(
                Mockito.eq(SPECIFICATION_API_KEY),
                Mockito.any(SynchronizedValueFactory.class),
                Mockito.any(BTCMarketsDigest.class),
                Mockito.refEq(btcMarketsOrder, "clientRequestId")))
        .thenReturn(orderResponse);

    Whitebox.setInternalState(marketsTradeService, "btcm", btcm);

    // when
    String placed = marketsTradeService.placeMarketOrder(marketOrder);

    // then
    assertThat(placed).isEqualTo("12345");
  }

  @Test
  public void shouldPlaceLimitOrder() throws IOException {
    // given
    LimitOrder limitOrder =
        new LimitOrder(
            Order.OrderType.ASK,
            new BigDecimal("10.00000000"),
            CurrencyPair.BTC_AUD,
            "11111",
            new Date(1234567890L),
            new BigDecimal("20.00000000"));

    BTCMarketsOrder btcMarketsOrder =
        new BTCMarketsOrder(
            new BigDecimal("10.00000000"),
            new BigDecimal("20.00000000"),
            "AUD",
            "BTC",
            BTCMarketsOrder.Side.Ask,
            BTCMarketsOrder.Type.Limit,
            "generatedReqId");

    BTCMarketsPlaceOrderResponse orderResponse =
        new BTCMarketsPlaceOrderResponse(true, null, 0, "11111", 12345);

    BTCMarketsAuthenticated btcm = mock(BTCMarketsAuthenticated.class);
    PowerMockito.when(
            btcm.placeOrder(
                Mockito.eq(SPECIFICATION_API_KEY),
                Mockito.any(SynchronizedValueFactory.class),
                Mockito.any(BTCMarketsDigest.class),
                Mockito.refEq(btcMarketsOrder, "clientRequestId")))
        .thenReturn(orderResponse);

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

    BTCMarketsCancelOrderResponse orderResponse =
        new BTCMarketsCancelOrderResponse(
            true,
            null,
            0,
            Arrays.asList(new BTCMarketsException(true, null, 0, "12345", 111L, null)));

    BTCMarketsAuthenticated btcm = mock(BTCMarketsAuthenticated.class);
    PowerMockito.when(
            btcm.cancelOrder(
                Mockito.eq(SPECIFICATION_API_KEY),
                Mockito.any(SynchronizedValueFactory.class),
                Mockito.any(BTCMarketsDigest.class),
                Mockito.refEq(cancelOrderRequest)))
        .thenReturn(orderResponse);
    Whitebox.setInternalState(marketsTradeService, "btcm", btcm);

    // when
    boolean cancelled = marketsTradeService.cancelOrder("111");

    // then
    assertThat(cancelled).isTrue();
  }

  @Test
  public void shouldCreateHistoryParams() {
    // when
    BTCMarketsTradeService.HistoryParams historyParams =
        marketsTradeService.createTradeHistoryParams();

    // then
    assertThat(historyParams.getPageLength()).isEqualTo(200);
  }

  @Test
  public void shouldGetOrderDetails() throws IOException {
    // given
    List<Long> orderIds = new ArrayList<>();
    orderIds.add(1000L);
    BTCMarketsOrderDetailsRequest btcMarketsOrderDetailsRequest =
        new BTCMarketsOrderDetailsRequest(orderIds);
    BTCMarketsOrders btcMarketsOrders = new BTCMarketsOrders(true, "", 0, new ArrayList<>());

    BTCMarketsAuthenticated btcm = mock(BTCMarketsAuthenticated.class);
    PowerMockito.when(
            btcm.getOrderDetails(
                Mockito.eq(SPECIFICATION_API_KEY),
                Mockito.any(SynchronizedValueFactory.class),
                Mockito.any(BTCMarketsDigest.class),
                Matchers.eq(btcMarketsOrderDetailsRequest)))
        .thenReturn(btcMarketsOrders);
    Whitebox.setInternalState(marketsTradeService, "btcm", btcm);
    // when
    Collection<Order> orders = marketsTradeService.getOrder("1000");
    assertThat(orders).hasSize(0);
  }
}
