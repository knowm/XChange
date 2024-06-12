package org.knowm.xchange.poloniex.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * @author timmolter
 */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class);
    exchange.remoteInit();
    MarketDataService marketDataService = exchange.getMarketDataService();
    Instrument currencyPair =
        exchange.getExchangeMetaData().getInstruments().keySet().iterator().next();
    Ticker ticker = marketDataService.getTicker(currencyPair);
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }
}
