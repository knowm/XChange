package org.knowm.xchange.bitfinex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitfinex.BitfinexExchangeWiremock;
import org.knowm.xchange.bitfinex.service.trade.params.BitfinexOpenOrdersParams;
import org.knowm.xchange.bitfinex.service.trade.params.BitfinexOrderQueryParams;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.OpenPosition.MarginMode;
import org.knowm.xchange.dto.account.OpenPosition.Type;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrencyPair;

class BitfinexTradeServiceTest extends BitfinexExchangeWiremock {

  TradeService tradeService = exchange.getTradeService();

  @Test
  void trade_history() throws IOException {
    UserTrades userTrades = exchange.getTradeService().getTradeHistory(null);

    assertThat(userTrades.getUserTrades()).hasSize(2);

    UserTrade expected =
        UserTrade.builder()
            .type(OrderType.ASK)
            .instrument(new CurrencyPair("GOMINING/USDT"))
            .id("1793448778")
            .orderId("214237248399")
            .originalAmount(new BigDecimal("59.43"))
            .price(new BigDecimal("0.54204"))
            .feeAmount(new BigDecimal("0.0644268744"))
            .feeCurrency(Currency.USDT)
            .timestamp(Date.from(Instant.parse("2025-08-09T14:37:18.579Z")))
            .build();

    assertThat(userTrades.getUserTrades())
        .first()
        .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  @Test
  void trade_history_by_symbol() throws IOException {
    UserTrades userTrades =
        exchange
            .getTradeService()
            .getTradeHistory(
                DefaultTradeHistoryParamCurrencyPair.builder()
                    .currencyPair(new CurrencyPair("LIFIII/USDT"))
                    .build());

    assertThat(userTrades.getUserTrades()).hasSize(2);

    UserTrade expected =
        UserTrade.builder()
            .type(OrderType.ASK)
            .instrument(new CurrencyPair("LIFIII/USDT"))
            .id("1798130174")
            .orderId("215550069744")
            .originalAmount(new BigDecimal("5804.17721836"))
            .price(new BigDecimal("0.01625"))
            .feeAmount(new BigDecimal("0.1886357595967"))
            .feeCurrency(Currency.USDT)
            .timestamp(Date.from(Instant.parse("2025-08-22T19:47:39.320Z")))
            .build();

    assertThat(userTrades.getUserTrades())
        .first()
        .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  @Test
  void sell_order_details_filled_and_active() throws IOException {
    BitfinexOrderQueryParams[] bitfinexOrderQueryParams = {
      BitfinexOrderQueryParams.builder()
          .from(Instant.ofEpochMilli(1698000000000L))
          .to(Instant.ofEpochMilli(1698770975000L))
          .limit(2L)
          .orderId("129183574940")
          .build(),
      BitfinexOrderQueryParams.builder().orderId("129194763959").build()
    };

    Collection<Order> actual = tradeService.getOrder(bitfinexOrderQueryParams);

    List<Order> expected =
        Arrays.asList(
            new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USD)
                .id("129183574940")
                .userReference("1698769385317")
                .limitPrice(new BigDecimal("34300"))
                .originalAmount(new BigDecimal("0.00020832"))
                .cumulativeAmount(new BigDecimal("0.00020832"))
                .timestamp(Date.from(Instant.parse("2023-10-31T16:23:05.317Z")))
                .orderStatus(OrderStatus.FILLED)
                .averagePrice(new BigDecimal("34348"))
                .build(),
            new LimitOrder.Builder(OrderType.ASK, new CurrencyPair("USDT/USD"))
                .id("129194763959")
                .userReference("1698745903367")
                .limitPrice(new BigDecimal("1.1"))
                .originalAmount(new BigDecimal("10"))
                .cumulativeAmount(new BigDecimal("0"))
                .timestamp(Date.from(Instant.parse("2023-10-31T09:51:43.367Z")))
                .orderStatus(OrderStatus.OPEN)
                .averagePrice(new BigDecimal("0"))
                .build());

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void buy_order_details_filled_and_active() throws IOException {
    BitfinexOrderQueryParams[] bitfinexOrderQueryParams = {
      BitfinexOrderQueryParams.builder()
          .currencyPair(CurrencyPair.BTC_USD)
          .orderId("129312251575")
          .build(),
      BitfinexOrderQueryParams.builder().orderId("129234528984").build()
    };

    Collection<Order> actual = tradeService.getOrder(bitfinexOrderQueryParams);

    List<Order> expected =
        Arrays.asList(
            new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
                .id("129234528984")
                .userReference("1698757804772")
                .limitPrice(new BigDecimal("34332"))
                .originalAmount(new BigDecimal("7.15141728"))
                .cumulativeAmount(new BigDecimal("7.15141728"))
                .timestamp(Date.from(Instant.parse("2023-10-31T13:10:04.772Z")))
                .orderStatus(OrderStatus.FILLED)
                .averagePrice(new BigDecimal("34329"))
                .build(),
            new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
                .id("129312251575")
                .userReference("1698833715477")
                .limitPrice(new BigDecimal("34000"))
                .originalAmount(new BigDecimal("0.000219"))
                .cumulativeAmount(new BigDecimal("0"))
                .timestamp(Date.from(Instant.parse("2023-11-01T10:15:15.477Z")))
                .orderStatus(OrderStatus.OPEN)
                .averagePrice(new BigDecimal("0"))
                .build());

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  @Disabled
  void open_orders() throws IOException {
    OpenOrders actual = tradeService.getOpenOrders();

    LimitOrder expected =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
            .id("129136592506")
            .limitPrice(new BigDecimal("30000"))
            .originalAmount(new BigDecimal("0.0002"))
            .orderStatus(OrderStatus.OPEN)
            .timestamp(Date.from(Instant.parse("2023-10-30T14:35:13.151Z")))
            .userReference("1698676513150")
            .build();

    assertThat(actual.getOpenOrders()).hasSize(1);
    assertThat(actual.getHiddenOrders()).isEmpty();

    assertThat(actual.getOpenOrders()).first().usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void open_orders_by_symbol() throws IOException {
    BitfinexOpenOrdersParams params =
        (BitfinexOpenOrdersParams) tradeService.createOpenOrdersParams();
    params.setCurrencyPair(CurrencyPair.BTC_USD);
    params.setIds(Arrays.asList(129312251575L, 129234528984L));
    OpenOrders actual = tradeService.getOpenOrders(params);

    LimitOrder expected =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
            .id("129312251575")
            .userReference("1698833715477")
            .limitPrice(new BigDecimal("34000"))
            .originalAmount(new BigDecimal("0.000219"))
            .cumulativeAmount(new BigDecimal("0.000219"))
            .timestamp(Date.from(Instant.parse("2023-11-01T10:15:15.477Z")))
            .orderStatus(OrderStatus.OPEN)
            .averagePrice(new BigDecimal("0"))
            .build();

    assertThat(actual.getOpenOrders()).hasSize(1);
    assertThat(actual.getHiddenOrders()).isEmpty();

    assertThat(actual.getOpenOrders()).first().usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void open_positions() throws IOException {
    var expected =
        OpenPosition.builder()
            .id("185023623")
            .instrument(new FuturesContract(CurrencyPair.BTC_USDT, "PERP"))
            .type(Type.LONG)
            .marginMode(MarginMode.CROSS)
            .size(new BigDecimal("0.00004"))
            .price(new BigDecimal("108470"))
            .liquidationPrice(new BigDecimal("54504.81225"))
            .unRealisedPnl(new BigDecimal("0.028"))
            .build();

    var actual = exchange.getTradeService().getOpenPositions();

    assertThat(actual.getOpenPositions()).hasSize(2);
    assertThat(actual.getOpenPositions().get(1).getInstrument())
        .isEqualTo(new FuturesContract(new CurrencyPair("XTZ/USDT"), "PERP"));

    assertThat(actual.getOpenPositions()).first().usingRecursiveComparison().isEqualTo(expected);
  }
}
