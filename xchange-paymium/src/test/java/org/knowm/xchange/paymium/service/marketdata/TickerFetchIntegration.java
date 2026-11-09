package org.knowm.xchange.paymium.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.paymium.PaymiumExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * @author timmolter
 */
class TickerFetchIntegration {

  @Test
  void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(PaymiumExchange.class);
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "EUR"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }
}
