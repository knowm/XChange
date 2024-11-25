package org.knowm.xchange.bitget.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.currency.CurrencyPair.BTC_USDT;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitget.BitgetExchangeWiremock;
import org.knowm.xchange.bitget.service.params.BitgetTradeHistoryParams;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
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

  @Test
  void trade_history() throws IOException {
    UserTrades userTrades =
        exchange
            .getTradeService()
            .getTradeHistory(
                BitgetTradeHistoryParams.builder()
                    .instrument(BTC_USDT)
                    .limit(2)
                    .orderId("1225475622585147396")
                    .startTime(Date.from(Instant.ofEpochMilli(1727902077418L)))
                    .endTime(Date.from(Instant.ofEpochMilli(1727902077420L)))
                    .endId("1225475622852575236")
                    .build());

    assertThat(userTrades.getUserTrades()).hasSize(1);

    UserTrade expected =
        new UserTrade(
            OrderType.BID,
            new BigDecimal("0.000246"),
            BTC_USDT,
            new BigDecimal("60775.01"),
            Date.from(Instant.ofEpochMilli(1727902077419L)),
            "1225475622852575236",
            "1225475622585147396",
            new BigDecimal("0.000000246"),
            Currency.BTC,
            null);

    assertThat(userTrades.getUserTrades()).first().usingRecursiveComparison().isEqualTo(expected);
  }
}
