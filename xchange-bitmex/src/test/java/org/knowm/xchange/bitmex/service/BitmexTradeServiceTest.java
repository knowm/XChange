package org.knowm.xchange.bitmex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitmex.BitmexExchangeWiremock;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.service.trade.TradeService;

class BitmexTradeServiceTest extends BitmexExchangeWiremock {

  TradeService tradeService = exchange.getTradeService();

  @Test
  void filled_market_buy_order_details() throws IOException {
    MarketOrder expected =
        new MarketOrder.Builder(OrderType.BID, CurrencyPair.ETH_USDT)
            .id("5d873440-48c6-48ed-86d9-e8e4259b0e7a")
            .userReference("filled-market-buy-order")
            .timestamp(Date.from(Instant.parse("2024-12-09T09:54:20.367Z")))
            .originalAmount(new BigDecimal("0.001"))
            .orderStatus(OrderStatus.FILLED)
            .cumulativeAmount(new BigDecimal("0.001"))
            .averagePrice(new BigDecimal("3879.35"))
            .build();

    Collection<Order> orders = tradeService.getOrder("5d873440-48c6-48ed-86d9-e8e4259b0e7a");
    assertThat(orders).hasSize(1);
    assertThat(orders)
        .first()
        .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }


  @Test
  void filled_market_sell_order_details() throws IOException {
    MarketOrder expected =
        new MarketOrder.Builder(OrderType.ASK, new CurrencyPair("SOL/USDT"))
            .id("4245a5fc-b7aa-400f-a11d-c4f50d24be13")
            .userReference("filled-market-sell-order")
            .timestamp(Date.from(Instant.parse("2024-12-11T23:20:29.099Z")))
            .originalAmount(new BigDecimal("0.022"))
            .orderStatus(OrderStatus.FILLED)
            .cumulativeAmount(new BigDecimal("0.022"))
            .averagePrice(new BigDecimal("227.72"))
            .build();

    Collection<Order> orders = tradeService.getOrder("4245a5fc-b7aa-400f-a11d-c4f50d24be13");
    assertThat(orders).hasSize(1);
    assertThat(orders)
        .first()
        .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }


  @Test
  void pending_limit_buy_order_details() throws IOException {
    LimitOrder expected =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USDT)
            .limitPrice(new BigDecimal("101300"))
            .id("fcfe266b-540c-4f06-be26-6b540cef0695")
            .userReference("pending-limit-buy-order")
            .timestamp(Date.from(Instant.parse("2024-12-11T23:29:08.347Z")))
            .originalAmount(new BigDecimal("0.0001"))
            .orderStatus(OrderStatus.NEW)
            .cumulativeAmount(BigDecimal.ZERO)
            .build();

    Collection<Order> orders = tradeService.getOrder("fcfe266b-540c-4f06-be26-6b540cef0695");
    assertThat(orders).hasSize(1);
    assertThat(orders)
        .first()
        .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }


  @Test
  void pending_limit_sell_order_details() throws IOException {
    LimitOrder expected =
        new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USDT)
            .limitPrice(new BigDecimal("101301"))
            .id("2c866db3-baa5-4e83-866d-b3baa5be8377")
            .userReference("pending-limit-sell-order")
            .timestamp(Date.from(Instant.parse("2024-12-11T23:48:29.109Z")))
            .originalAmount(new BigDecimal("0.0001"))
            .orderStatus(OrderStatus.NEW)
            .cumulativeAmount(BigDecimal.ZERO)
            .build();

    Collection<Order> orders = tradeService.getOrder("2c866db3-baa5-4e83-866d-b3baa5be8377");
    assertThat(orders).hasSize(1);
    assertThat(orders)
        .first()
        .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }


  @Test
  void filled_limit_buy_order_details() throws IOException {
    LimitOrder expected =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USDT)
            .limitPrice(new BigDecimal("101300"))
            .id("2efd2937-00de-401b-a9d8-dcdc7636c310")
            .userReference("filled-limit-buy-order")
            .timestamp(Date.from(Instant.parse("2024-12-11T23:32:24.579Z")))
            .originalAmount(new BigDecimal("0.0001"))
            .orderStatus(OrderStatus.FILLED)
            .cumulativeAmount(new BigDecimal("0.0001"))
            .averagePrice(new BigDecimal("101300"))
            .build();

    Collection<Order> orders = tradeService.getOrder("2efd2937-00de-401b-a9d8-dcdc7636c310");
    assertThat(orders).hasSize(1);
    assertThat(orders)
        .first()
        .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }


  @Test
  void filled_limit_sell_order_details() throws IOException {
    LimitOrder expected =
        new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USDT)
            .limitPrice(new BigDecimal("101145"))
            .id("1f2698bb-36c3-489a-b44c-b3f7e094350d")
            .userReference("filled-limit-sell-order")
            .timestamp(Date.from(Instant.parse("2024-12-12T00:00:05.803Z")))
            .originalAmount(new BigDecimal("0.0001"))
            .orderStatus(OrderStatus.FILLED)
            .cumulativeAmount(new BigDecimal("0.0001"))
            .averagePrice(new BigDecimal("101145"))
            .build();

    Collection<Order> orders = tradeService.getOrder("1f2698bb-36c3-489a-b44c-b3f7e094350d");
    assertThat(orders).hasSize(1);
    assertThat(orders)
        .first()
        .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }


}