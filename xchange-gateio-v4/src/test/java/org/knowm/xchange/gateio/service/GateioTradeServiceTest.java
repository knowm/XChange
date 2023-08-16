package org.knowm.xchange.gateio.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.gateio.GateioExchangeWiremock;
import org.knowm.xchange.gateio.dto.trade.GateioUserTrade;
import org.knowm.xchange.gateio.dto.trade.Role;
import org.knowm.xchange.gateio.service.params.GateioTradeHistoryParams;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderByInstrumentAndIdParams;
import org.knowm.xchange.service.trade.params.orders.DefaultQueryOrderParamInstrument;

class GateioTradeServiceTest extends GateioExchangeWiremock {

  GateioTradeService gateioTradeService = (GateioTradeService) exchange.getTradeService();


  @Test
  void place_order_not_enough_balance() {
    MarketOrder marketOrder = new MarketOrder.Builder(OrderType.BID, CurrencyPair.BTC_USDT)
        .userReference("t-balance-test")
        .originalAmount(BigDecimal.valueOf(100))
        .build();

    assertThatExceptionOfType(FundsExceededException.class)
        .isThrownBy(() -> gateioTradeService.placeMarketOrder(marketOrder));
  }


  @Test
  void valid_market_buy_order() throws IOException {
    MarketOrder marketOrder = new MarketOrder.Builder(OrderType.BID, CurrencyPair.BTC_USDT)
        .userReference("t-valid-market-buy-order")
        .originalAmount(BigDecimal.valueOf(20))
        .build();

    String actualResponse = gateioTradeService.placeMarketOrder(marketOrder);
    assertThat(actualResponse).isEqualTo("342251629898");

  }


  @Test
  void valid_market_sell_order() throws IOException {
    MarketOrder marketOrder = new MarketOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USDT)
        .userReference("t-valid-market-sell-order")
        .originalAmount(new BigDecimal("0.0007"))
        .build();

    String actualResponse = gateioTradeService.placeMarketOrder(marketOrder);
    assertThat(actualResponse).isEqualTo("342260949533");

  }


  @Test
  void valid_limit_sell_order() throws IOException {
    LimitOrder limitOrder = new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USDT)
        .userReference("t-valid-limit-sell-order")
        .originalAmount(new BigDecimal("0.00068"))
        .limitPrice(new BigDecimal("29240.7"))
        .build();

    String actualResponse = gateioTradeService.placeLimitOrder(limitOrder);
    assertThat(actualResponse).isEqualTo("373824296029");
  }


  @Test
  void valid_cancel_order() throws IOException {
    boolean actual = gateioTradeService.cancelOrder(new DefaultCancelOrderByInstrumentAndIdParams(CurrencyPair.BTC_USDT, "376835979523"));
    assertThat(actual).isTrue();
  }


  @Test
  void valid_limit_buy_order() throws IOException {
    LimitOrder limitOrder = new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USDT)
        .userReference("t-valid-limit-buy-order")
        .originalAmount(new BigDecimal("0.00068"))
        .limitPrice(new BigDecimal("10000.7"))
        .build();

    String actualResponse = gateioTradeService.placeLimitOrder(limitOrder);
    assertThat(actualResponse).isEqualTo("376835979523");
  }


  @Test
  void order_details() throws IOException {
    MarketOrder expected = new MarketOrder.Builder(OrderType.BID, CurrencyPair.BTC_USDT)
        .id("342251629898")
        .userReference("t-valid-market-buy-order")
        .timestamp(Date.from(Instant.parse("2023-06-03T22:07:38.451Z")))
        .originalAmount(BigDecimal.valueOf(20))
        .orderStatus(OrderStatus.FILLED)
        .cumulativeAmount(new BigDecimal("18.92681"))
        .averagePrice(new BigDecimal("27038.3"))
        .fee(new BigDecimal("0.0000014"))
        .build();

    Collection<Order> orders = gateioTradeService.getOrder(new DefaultQueryOrderParamInstrument(CurrencyPair.BTC_USDT, "342251629898"));
    assertThat(orders).hasSize(1);
    assertThat(orders).first().usingRecursiveComparison().isEqualTo(expected);
  }


  @Test
  void trade_history() throws IOException {
    UserTrades userTrades = gateioTradeService.getTradeHistory(GateioTradeHistoryParams.builder()
            .currencyPair(CurrencyPair.BTC_USDT)
            .pageLength(2)
            .pageNumber(2)
            .startTime(Date.from(Instant.ofEpochSecond(1691617924)))
            .endTime(Date.from(Instant.ofEpochSecond(1691704324)))
        .build());

    assertThat(userTrades.getUserTrades()).hasSize(2);

    GateioUserTrade expected = new GateioUserTrade(OrderType.ASK, new BigDecimal("0.00005"), CurrencyPair.BTC_USDT,
        new BigDecimal("29447.2"), Date.from(Instant.ofEpochMilli(1691702286356L)), "6068789332",
        "381064942553", new BigDecimal("0.00294472"), Currency.USDT, "-", Role.TAKER);

    UserTrade actual = userTrades.getUserTrades().get(0);

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }


}