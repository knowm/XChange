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
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.service.trade.TradeService;

class BitmexTradeServiceTest extends BitmexExchangeWiremock {

  TradeService tradeService = exchange.getTradeService();

  @Test
  void filled_market_buy_order_details() throws IOException {
    MarketOrder expected =
        new MarketOrder.Builder(OrderType.BID, CurrencyPair.ETH_USDT)
            .id("5d873440-48c6-48ed-86d9-e8e4259b0e7a")
            .userReference("valid-filled-market-sell-order")
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


}