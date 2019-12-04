package org.knowm.xchange.dsx.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.DSXExchangeV3;
import org.knowm.xchange.dsx.service.DSXMarketDataServiceV3;
import org.knowm.xchange.dto.marketdata.Ticker;

/** @author Mikhail Wall */
public class TickerFetchIntegrationV3 {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(DSXExchangeV3.class);
    DSXMarketDataServiceV3 marketDataService =
        (DSXMarketDataServiceV3) exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "USD"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }
}
