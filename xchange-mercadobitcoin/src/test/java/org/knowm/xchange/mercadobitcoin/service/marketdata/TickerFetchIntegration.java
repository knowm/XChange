package org.knowm.xchange.mercadobitcoin.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.mercadobitcoin.MercadoBitcoinExchange;
import org.knowm.xchange.mercadobitcoin.MercadoBitcoinUtils;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author timmolter */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(MercadoBitcoinExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();

    Ticker ticker;
    for (CurrencyPair pair : MercadoBitcoinUtils.availablePairs) {
      ticker = marketDataService.getTicker(pair);
      System.out.println(ticker.toString());
      assertThat(ticker).isNotNull();
    }
  }
}
