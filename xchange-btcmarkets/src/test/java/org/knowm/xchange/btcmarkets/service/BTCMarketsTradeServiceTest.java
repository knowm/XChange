package org.knowm.xchange.btcmarkets.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import org.junit.Test;
import org.knowm.xchange.btcmarkets.dto.BTCMarketsException;
import org.knowm.xchange.btcmarkets.dto.BTCMarketsOrderFlags;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsCancelOrderRequest;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsCancelOrderResponse;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrderDetailsRequest;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrders;
import org.knowm.xchange.btcmarkets.dto.v3.trade.BTCMarketsPlaceOrderRequest;
import org.knowm.xchange.btcmarkets.dto.v3.trade.BTCMarketsPlaceOrderResponse;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.mockito.Matchers;
import org.mockito.Mockito;
import si.mazi.rescu.SynchronizedValueFactory;

public class BTCMarketsTradeServiceTest extends BTCMarketsServiceTest {

  @Test
  public void shouldPlaceMarketOrder() throws IOException {
    MarketOrder marketOrder =
        new MarketOrder(Order.OrderType.BID, new BigDecimal("10.00000000"), CurrencyPair.BTC_AUD);

    BTCMarketsPlaceOrderRequest btcMarketsOrder =
        new BTCMarketsPlaceOrderRequest(
            "BTC-AUD", "0", "10.00000000", "Market", "Bid", null, null, "GTC", false, null, null);

    BTCMarketsPlaceOrderResponse orderResponse = new BTCMarketsPlaceOrderResponse("11111");

    when(btcMarketsAuthenticatedV3.placeOrder(
            Mockito.eq(SPECIFICATION_API_KEY),
            Mockito.any(SynchronizedValueFactory.class),
            Mockito.any(BTCMarketsDigestV3.class),
            Mockito.refEq(btcMarketsOrder)))
        .thenReturn(orderResponse);

    // when
    String placed = btcMarketsTradeService.placeMarketOrder(marketOrder);

    // then
    assertThat(placed).isEqualTo("11111");
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

    BTCMarketsPlaceOrderRequest request =
        new BTCMarketsPlaceOrderRequest(
            "BTC-AUD",
            "20.00000000",
            "10.00000000",
            "Limit",
            "Ask",
            null,
            null,
            "GTC",
            false,
            null,
            null);

    BTCMarketsPlaceOrderResponse orderResponse = new BTCMarketsPlaceOrderResponse("11111");

    when(btcMarketsAuthenticatedV3.placeOrder(
            Mockito.eq(SPECIFICATION_API_KEY),
            Mockito.any(SynchronizedValueFactory.class),
            Mockito.any(BTCMarketsDigestV3.class),
            Mockito.refEq(request)))
        .thenReturn(orderResponse);

    // when
    String placed = btcMarketsTradeService.placeLimitOrder(limitOrder);

    // then
    assertThat(placed).isEqualTo("11111");
  }

  @Test
  public void shouldPlaceLimitOrderWithPostOnlyFlag() throws IOException {
    // given
    LimitOrder limitOrder =
        new LimitOrder(
            Order.OrderType.ASK,
            new BigDecimal("10.00000000"),
            CurrencyPair.BTC_AUD,
            "11111",
            new Date(1234567890L),
            new BigDecimal("20.00000000"));
    limitOrder.getOrderFlags().add(BTCMarketsOrderFlags.POST_ONLY);

    BTCMarketsPlaceOrderRequest expectedRequest =
        new BTCMarketsPlaceOrderRequest(
            "BTC-AUD",
            "20.00000000",
            "10.00000000",
            "Limit",
            "Ask",
            null,
            null,
            "GTC",
            true,
            null,
            null);

    BTCMarketsPlaceOrderResponse orderResponse = new BTCMarketsPlaceOrderResponse("11111");

    when(btcMarketsAuthenticatedV3.placeOrder(
            Mockito.eq(SPECIFICATION_API_KEY),
            Mockito.any(SynchronizedValueFactory.class),
            Mockito.any(BTCMarketsDigestV3.class),
            Mockito.refEq(expectedRequest)))
        .thenReturn(orderResponse);

    // when
    String placed = btcMarketsTradeService.placeLimitOrder(limitOrder);

    // then
    assertThat(placed).isEqualTo("11111");
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

    when(btcMarketsAuthenticated.cancelOrder(
            Mockito.eq(SPECIFICATION_API_KEY),
            Mockito.any(SynchronizedValueFactory.class),
            Mockito.any(BTCMarketsDigest.class),
            Mockito.refEq(cancelOrderRequest)))
        .thenReturn(orderResponse);

    // when
    boolean cancelled = btcMarketsTradeService.cancelOrder("111");

    // then
    assertThat(cancelled).isTrue();
  }

  @Test
  public void shouldCreateHistoryParams() {
    // when
    BTCMarketsTradeService.HistoryParams historyParams =
        btcMarketsTradeService.createTradeHistoryParams();

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

    when(btcMarketsAuthenticated.getOrderDetails(
            Mockito.eq(SPECIFICATION_API_KEY),
            Mockito.any(SynchronizedValueFactory.class),
            Mockito.any(BTCMarketsDigest.class),
            Matchers.eq(btcMarketsOrderDetailsRequest)))
        .thenReturn(btcMarketsOrders);
    // when
    Collection<Order> orders = btcMarketsTradeService.getOrder("1000");
    assertThat(orders).hasSize(0);
  }
}
