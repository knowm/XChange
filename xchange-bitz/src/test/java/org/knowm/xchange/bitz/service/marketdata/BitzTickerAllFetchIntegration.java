package org.knowm.xchange.bitz.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitz.BitZExchange;
import org.knowm.xchange.bitz.service.BitZMarketDataService;
import org.knowm.xchange.dto.marketdata.Ticker;

public class BitzTickerAllFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {
    // Get Specific Exchange
    BitZExchange exchange =
        (BitZExchange) ExchangeFactory.INSTANCE.createExchange(BitZExchange.class);
    BitZMarketDataService marketDataService =
        (BitZMarketDataService) exchange.getMarketDataService();

    // Poll All Tickers
    List<Ticker> tickers = marketDataService.getTickers();

    // Verify Not Null Values
    assertThat(tickers).isNotNull();
    assertThat(tickers).isNotEmpty();

    // TODO: Logical Verification Of Values
  }
}
