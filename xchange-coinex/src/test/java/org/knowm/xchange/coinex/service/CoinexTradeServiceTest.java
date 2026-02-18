package org.knowm.xchange.coinex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.coinex.CoinexExchangeWiremock;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderByInstrumentAndIdParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamInstrument;

class CoinexTradeServiceTest extends CoinexExchangeWiremock {

  TradeService tradeService = exchange.getTradeService();

  @Test
  void all_open_orders() throws IOException {
    OpenOrders actual = tradeService.getOpenOrders();

    assertThat(actual.getOpenOrders()).hasSize(2);
    assertThat(actual.getHiddenOrders()).isEmpty();

    assertThat(actual.getAllOpenOrders().get(0).getInstrument()).isEqualTo(CurrencyPair.ETH_USDT);
    assertThat(actual.getAllOpenOrders().get(1).getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
  }

  @Test
  void filtered_open_orders() throws IOException {
    OpenOrders actual =
        tradeService.getOpenOrders(new DefaultOpenOrdersParamInstrument(CurrencyPair.BTC_USDT));

    assertThat(actual.getOpenOrders()).hasSize(1);
    assertThat(actual.getHiddenOrders()).isEmpty();

    assertThat(actual.getAllOpenOrders().get(0).getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
  }

  @Test
  void valid_cancel_order() throws IOException {
    boolean actual =
        tradeService.cancelOrder(
            new DefaultCancelOrderByInstrumentAndIdParams(CurrencyPair.BTC_USDT, "136215219959"));
    assertThat(actual).isTrue();
  }

  @Test
  void place_stop_buy_limit_order() throws IOException {
    StopOrder stopOrder =
        new StopOrder.Builder(OrderType.BID, CurrencyPair.BTC_USDT)
            .userReference("valid_stop_buy_limit_order")
            .stopPrice(BigDecimal.ONE)
            .originalAmount(BigDecimal.ONE)
            .limitPrice(BigDecimal.ONE)
            .build();

    String actual = tradeService.placeStopOrder(stopOrder);

    assertThat(actual).isEqualTo("155261952040");
  }

  @Test
  void place_stop_buy_market_order() throws IOException {
    StopOrder stopOrder =
        new StopOrder.Builder(OrderType.BID, CurrencyPair.BTC_USDT)
            .userReference("valid_stop_buy_market_order")
            .stopPrice(BigDecimal.ONE)
            .originalAmount(BigDecimal.ONE)
            .build();

    String actual = tradeService.placeStopOrder(stopOrder);

    assertThat(actual).isEqualTo("155262133674");
  }

  @Test
  void place_stop_sell_limit_order() throws IOException {
    StopOrder stopOrder =
        new StopOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USDT)
            .userReference("valid_stop_sell_limit_order")
            .stopPrice(BigDecimal.ONE)
            .originalAmount(BigDecimal.ONE)
            .limitPrice(BigDecimal.ONE)
            .build();

    String actual = tradeService.placeStopOrder(stopOrder);

    assertThat(actual).isEqualTo("155262192688");
  }

  @Test
  void place_stop_sell_market_order() throws IOException {
    StopOrder stopOrder =
        new StopOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USDT)
            .userReference("valid_stop_sell_market_order")
            .stopPrice(BigDecimal.ONE)
            .originalAmount(BigDecimal.ONE)
            .build();

    String actual = tradeService.placeStopOrder(stopOrder);

    assertThat(actual).isEqualTo("155262200343");
  }
}
