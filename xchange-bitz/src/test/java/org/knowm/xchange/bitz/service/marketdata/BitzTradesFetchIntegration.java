package org.knowm.xchange.bitz.service.marketdata;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitz.BitZExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BitzTradesFetchIntegration {

  @Test
  public void tradesFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BitZExchange.class);
    MarketDataService marketDataService = exchange.getMarketDataService();
    Trades trades = marketDataService.getTrades(new CurrencyPair("LTC", "BTC"));

    // Verify Not Null Values
    // assertThat(trades).isNotNull();

    // TODO: Logical Verification Of Values
  }
}
