package org.knowm.xchange.coinex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.coinex.CoinexExchangeWiremock;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamInstrument;

class CoinexTradeServiceTest extends CoinexExchangeWiremock {

  TradeService tradeService = exchange.getTradeService();

  @Test
  void all_open_orders() throws IOException {
    OpenOrders actual = tradeService.getOpenOrders();

    assertThat(actual.getOpenOrders()).hasSize(2);
    assertThat(actual.getHiddenOrders()).isEmpty();

    assertThat(actual.getAllOpenOrders().get(0).getInstrument())
        .isEqualTo(CurrencyPair.ETH_USDT);
    assertThat(actual.getAllOpenOrders().get(1).getInstrument())
        .isEqualTo(CurrencyPair.BTC_USDT);
  }

  @Test
  void filtered_open_orders() throws IOException {
    OpenOrders actual = tradeService.getOpenOrders(new DefaultOpenOrdersParamInstrument(CurrencyPair.BTC_USDT));

    assertThat(actual.getOpenOrders()).hasSize(1);
    assertThat(actual.getHiddenOrders()).isEmpty();

    assertThat(actual.getAllOpenOrders().get(0).getInstrument())
        .isEqualTo(CurrencyPair.BTC_USDT);
  }

}