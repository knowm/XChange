package org.xchange.bitz.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.xchange.bitz.BitZExchange;

public class BitzTickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BitZExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(CurrencyPair.LTC_BTC);
    
    // Verify Not Null Values
    assertThat(ticker).isNotNull();
    
    assertThat(ticker.getLast()).isNotNull();
    assertThat(ticker.getAsk()).isNotNull();
    assertThat(ticker.getBid()).isNotNull();
    assertThat(ticker.getHigh()).isNotNull();
    assertThat(ticker.getLow()).isNotNull();
    assertThat(ticker.getVolume()).isNotNull();
    
    // TODO: Logical Verification Of Values
  }

}
