package org.knowm.xchange.anx.v2.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.anx.v2.ANXExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author timmolter */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(ANXExchange.class.getName());
    exchange.remoteInit();
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }
}
