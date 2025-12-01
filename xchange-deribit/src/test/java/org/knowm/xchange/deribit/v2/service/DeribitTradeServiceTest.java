package org.knowm.xchange.deribit.v2.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.deribit.DeribitExchangeWiremock;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.OpenPosition.MarginMode;
import org.knowm.xchange.dto.account.OpenPosition.Type;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamInstrument;

class DeribitTradeServiceTest extends DeribitExchangeWiremock {

  TradeService tradeService = exchange.getTradeService();

  @Test
  void open_orders_by_symbol() throws IOException {
    DefaultOpenOrdersParamInstrument params =
        (DefaultOpenOrdersParamInstrument) tradeService.createOpenOrdersParams();
    params.setInstrument(new CurrencyPair("XRP/USDC"));
    OpenOrders actual = tradeService.getOpenOrders(params);

    LimitOrder expected =
        new LimitOrder.Builder(OrderType.BID, new CurrencyPair("XRP/USDC"))
            .id("XRP_USDC-6476136171")
            .userReference("018af0fd-9ad8-4dec-94ce-90ce41306ce1")
            .limitPrice(new BigDecimal("2.008"))
            .originalAmount(new BigDecimal("2.0"))
            .cumulativeAmount(new BigDecimal("0.0"))
            .timestamp(Date.from(Instant.parse("2025-11-23T11:16:08.815Z")))
            .orderStatus(OrderStatus.OPEN)
            .averagePrice(new BigDecimal("0.0"))
            .build();

    assertThat(actual.getOpenOrders()).hasSize(1);
    assertThat(actual.getHiddenOrders()).isEmpty();

    assertThat(actual.getOpenOrders()).first().usingRecursiveComparison().isEqualTo(expected);
  }


  @Test
  void open_positions() throws IOException {
    var expected =
        OpenPosition.builder()
            .instrument(new FuturesContract(new CurrencyPair("BTC/USDC"), "PERPETUAL"))
            .type(Type.LONG)
            .marginMode(MarginMode.CROSS)
            .size(new BigDecimal("0.0001"))
            .price(new BigDecimal("85295.36"))
            .liquidationPrice(new BigDecimal("1904.376197"))
            .unRealisedPnl(new BigDecimal("0.005986"))
            .build();

    var actual = tradeService.getOpenPositions();

    assertThat(actual.getOpenPositions()).hasSize(1);

    assertThat(actual.getOpenPositions()).first().usingRecursiveComparison().isEqualTo(expected);
  }


  @Test
  void place_limit_buy_order() throws IOException {
    LimitOrder limitOrder =
        new LimitOrder.Builder(OrderType.BID, new CurrencyPair("XRP/USDC"))
            .originalAmount(new BigDecimal("1"))
            .limitPrice(new BigDecimal("1.8"))
            .userReference("c1d14121-c076-4c13-974b-c80cda36e09a")
            .build();

    String actualResponse = tradeService.placeLimitOrder(limitOrder);
    assertThat(actualResponse).isEqualTo("XRP_USDC-6476518126");
  }

  @Test
  void buy_order_details() throws IOException {
    var expected =
        new MarketOrder.Builder(OrderType.BID, new CurrencyPair("USDC/USDT"))
            .id("USDC_USDT-6470719424")
            .userReference("bfa2eaeb-7586-4552-9525-c0c7b4bc5df2")
            .timestamp(Date.from(Instant.parse("2025-11-22T23:38:35.497Z")))
            .originalAmount(new BigDecimal("2.0"))
            .orderStatus(OrderStatus.FILLED)
            .cumulativeAmount(new BigDecimal("2.0"))
            .averagePrice(new BigDecimal("1.0008"))
            .build();

    Collection<Order> orders = tradeService.getOrder("USDC_USDT-6470719424");
    assertThat(orders).hasSize(1);
    assertThat(orders)
        .first()
//        .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  @Test
  void trade_history() throws IOException {
    UserTrades userTrades =
        exchange
            .getTradeService()
            .getTradeHistory(
                DeribitTradeHistoryParams.builder()
                    .currency(Currency.USDT)
                    .build());

    assertThat(userTrades.getUserTrades()).hasSize(1);

    UserTrade expected =
        UserTrade.builder()
            .type(OrderType.BID)
            .originalAmount(new BigDecimal("2.0"))
            .instrument(new CurrencyPair("USDC/USDT"))
            .price(new BigDecimal("1.0008"))
            .timestamp(Date.from(Instant.parse("2025-11-22T23:38:35.497Z")))
            .id("USDC_USDT-21102496")
            .orderId("USDC_USDT-6470719424")
            .feeAmount(new BigDecimal("0.0"))
            .feeCurrency(Currency.USDC)
            .build();
    assertThat(userTrades.getUserTrades()).first().usingRecursiveComparison().isEqualTo(expected);
  }


}
