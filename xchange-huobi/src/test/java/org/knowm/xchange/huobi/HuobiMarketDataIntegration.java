package org.knowm.xchange.huobi;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trade;

class HuobiMarketDataIntegration {

  @Test
  void checkTradesSortingByDate() throws Exception {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(HuobiExchange.class);

    List<Trade> trades =
        exchange.getMarketDataService().getTrades(new CurrencyPair("BTC/USDT")).getTrades();
    assertThat(trades.get(0).getTimestamp()).isAfterOrEqualTo(trades.get(1).getTimestamp());
  }
}
