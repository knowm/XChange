package org.knowm.xchange.bleutrade.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bleutrade.BleutradeExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.utils.CertHelper;

/** @author timmolter */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    CertHelper.trustAllCerts();

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BleutradeExchange.class.getName());
    exchange.remoteInit();
    MarketDataService marketDataService = exchange.getMarketDataService();
    CurrencyPair market =
        exchange.getExchangeMetaData().getCurrencyPairs().keySet().iterator().next();
    Ticker ticker = marketDataService.getTicker(market);
    //    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }
}
