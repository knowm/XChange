package org.knowm.xchange.kraken.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.OpenPosition.Type;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.kraken.KrakenExchangeWiremock;
import org.knowm.xchange.kraken.dto.trade.KrakenUserTrade;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;

public class KrakenTradeServiceTest extends KrakenExchangeWiremock {

  TradeService tradeService = exchange.getTradeService();

  @Test
  void all_open_orders() throws IOException {
    var expected =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.ETH_USDT)
            .id("OKXYTQ-BJLS2-HYJD7R")
            .limitPrice(new BigDecimal("4065.54"))
            .originalAmount(new BigDecimal("0.002"))
            .cumulativeAmount(BigDecimal.ZERO)
            .averagePrice(BigDecimal.ZERO)
            .fee(BigDecimal.ZERO)
            .timestamp(Date.from(Instant.parse("2025-09-03T21:18:20.624Z")))
            .orderStatus(OrderStatus.NEW)
            .userReference("0")
            .build();

    OpenOrders openOrders = tradeService.getOpenOrders();

    assertThat(openOrders.getOpenOrders()).hasSize(2);
    assertThat(openOrders.getOpenOrders())
        .first()
        .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  @Test
  void filtered_open_orders() throws IOException {
    OpenOrders actual =
        tradeService.getOpenOrders(new DefaultOpenOrdersParamCurrencyPair(CurrencyPair.BTC_USDT));

    assertThat(actual.getOpenOrders()).hasSize(1);
    assertThat(actual.getHiddenOrders()).isEmpty();

    assertThat(actual.getAllOpenOrders().get(0).getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
  }

  @Test
  void open_positions() throws IOException {
    var actual = tradeService.getOpenPositions();

    assertThat(actual.getOpenPositions()).hasSize(2);

    var expected =
        OpenPosition.builder()
            .instrument(CurrencyPair.BTC_USDT)
            .type(Type.LONG)
            .size(new BigDecimal("5.51"))
            .price(new BigDecimal("110200"))
            .build();

    assertThat(actual.getOpenPositions().get(1))
        .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  @Test
  void trade_history() throws IOException {
    UserTrades userTrades =
        exchange
            .getTradeService()
            .getTradeHistory(
                KrakenTradeHistoryParams.builder().currencyPair(CurrencyPair.BTC_USDT).build());

    assertThat(userTrades.getUserTrades()).hasSize(2);

    KrakenUserTrade expected =
        KrakenUserTrade.builder()
            .id("TAGKDK-PVBGJ-RZ4N35")
            .orderId("O7XYLB-A7U77-GE4DLL")
            .instrument(CurrencyPair.BTC_USDT)
            .type(OrderType.BID)
            .originalAmount(new BigDecimal("0.00013394"))
            .price(new BigDecimal("111993.3"))
            .timestamp(Date.from(Instant.parse("2025-09-03T21:33:57.276Z")))
            .feeAmount(new BigDecimal("0.06"))
            .feeCurrency(Currency.USDT)
            .cost(new BigDecimal("15"))
            .build();

    assertThat(userTrades.getUserTrades())
        .first()
        .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  @Test
  void place_limit_buy_order() throws IOException {
    LimitOrder limitOrder =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USDT)
            .originalAmount(new BigDecimal("0.0001"))
            .limitPrice(new BigDecimal("10000.5"))
            .build();

    String actualResponse = tradeService.placeLimitOrder(limitOrder);
    assertThat(actualResponse).isEqualTo("O6OP4A-ZRX3T-RBODCB");
  }

  @Test
  void place_limit_sell_order() throws IOException {
    LimitOrder limitOrder =
        new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USDT)
            .originalAmount(new BigDecimal("0.00005"))
            .limitPrice(new BigDecimal("130000.5"))
            .build();

    String actualResponse = tradeService.placeLimitOrder(limitOrder);
    assertThat(actualResponse).isEqualTo("OHMFXD-DVWOT-XTF4AU");
  }

  @Test
  void place_market_buy_order() throws IOException {
    MarketOrder marketOrder =
        new MarketOrder.Builder(OrderType.BID, CurrencyPair.BTC_USDT)
            // exchange requires always asset amount for all orders
            .originalAmount(new BigDecimal("0.00005"))
            .build();

    String actualResponse = tradeService.placeMarketOrder(marketOrder);
    assertThat(actualResponse).isEqualTo("OYNSAY-BFTRZ-RPATWJ");
  }

  @Test
  void place_market_sell_order() throws IOException {
    MarketOrder marketOrder =
        new MarketOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USDT)
            // exchange requires always asset amount for all orders
            .originalAmount(new BigDecimal("0.00005"))
            .build();

    String actualResponse = tradeService.placeMarketOrder(marketOrder);
    assertThat(actualResponse).isEqualTo("OSP6A7-6PRMW-6O2U6Y");
  }

  @Test
  void valid_cancel_order() throws IOException {
    boolean actual = tradeService.cancelOrder("OKXYTQ-BJLS2-HYJD7R");
    assertThat(actual).isTrue();
  }
}
