package org.knowm.xchange.bitget.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitget.BitgetExchangeWiremock;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.service.trade.TradeService;

class BitgetTradeServiceTest extends BitgetExchangeWiremock {

  TradeService tradeService = exchange.getTradeService();


  @Test
  void sell_order_details() throws IOException {
    MarketOrder expected =
        new MarketOrder.Builder(OrderType.ASK, new CurrencyPair("GOMINING/USDT"))
            .id("1213530920130613257")
            .userReference("t-valid-market-sell-order")
            .timestamp(Date.from(Instant.parse("2024-08-30T21:43:58.350Z")))
            .originalAmount(new BigDecimal("3"))
            .orderStatus(OrderStatus.FILLED)
            .cumulativeAmount(new BigDecimal("3"))
            .averagePrice(new BigDecimal("0.37846"))
            .fee(new BigDecimal("0.00113538"))
            .build();

    Collection<Order> orders = tradeService.getOrder("1213530920130613257");
    assertThat(orders).hasSize(1);
    assertThat(orders).first()
        .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
        .usingRecursiveComparison().isEqualTo(expected);
  }




}