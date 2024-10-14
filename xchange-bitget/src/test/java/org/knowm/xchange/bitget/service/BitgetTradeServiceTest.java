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
import org.knowm.xchange.service.trade.params.orders.DefaultQueryOrderParamInstrument;

class BitgetTradeServiceTest extends BitgetExchangeWiremock {

  TradeService tradeService = exchange.getTradeService();

  @Test
  void sell_order_details() throws IOException {
    MarketOrder expected =
        new MarketOrder.Builder(OrderType.ASK, new CurrencyPair("BGB/USDT"))
            .id("1214193970718347264")
            .userReference("t-valid-market-sell-order")
            .timestamp(Date.from(Instant.parse("2024-09-01T17:38:41.929Z")))
            .originalAmount(new BigDecimal("2"))
            .orderStatus(OrderStatus.FILLED)
            .cumulativeAmount(new BigDecimal("2"))
            .averagePrice(new BigDecimal("0.9649"))
            .fee(new BigDecimal("0.0019298"))
            .build();

    Collection<Order> orders = tradeService.getOrder("1214193970718347264");
    assertThat(orders).hasSize(1);
    assertThat(orders)
        .first()
        .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  @Test
  void buy_order_details() throws IOException {
    MarketOrder expected =
        new MarketOrder.Builder(OrderType.BID, new CurrencyPair("BGB/USDT"))
            .id("1214189703404097539")
            .userReference("t-valid-market-buy-order")
            .timestamp(Date.from(Instant.parse("2024-09-01T17:21:44.522Z")))
            .originalAmount(new BigDecimal("2"))
            .orderStatus(OrderStatus.FILLED)
            .cumulativeAmount(new BigDecimal("1.9999925400000000"))
            .averagePrice(new BigDecimal("0.9659000000000000"))
            .fee(new BigDecimal("0.0020706"))
            .build();

    Collection<Order> orders =
        tradeService.getOrder(new DefaultQueryOrderParamInstrument(null, "1214189703404097539"));
    assertThat(orders).hasSize(1);
    assertThat(orders)
        .first()
        .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  @Test
  void place_market_buy_order() throws IOException {
    MarketOrder marketOrder =
        new MarketOrder.Builder(OrderType.BID, new CurrencyPair("BGB/USDT"))
            .userReference("t-valid-market-buy-order")
            .originalAmount(BigDecimal.valueOf(2))
            .build();

    String actualResponse = exchange.getTradeService().placeMarketOrder(marketOrder);
    assertThat(actualResponse).isEqualTo("1214189703404097539");
  }

  @Test
  void place_market_sell_order() throws IOException {
    MarketOrder marketOrder =
        new MarketOrder.Builder(OrderType.ASK, new CurrencyPair("BGB/USDT"))
            .userReference("t-valid-market-sell-order")
            .originalAmount(BigDecimal.valueOf(2))
            .build();

    String actualResponse = exchange.getTradeService().placeMarketOrder(marketOrder);
    assertThat(actualResponse).isEqualTo("1214193970718347264");
  }
}
