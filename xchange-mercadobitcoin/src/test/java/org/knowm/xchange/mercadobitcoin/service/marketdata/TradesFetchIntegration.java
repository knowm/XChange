package org.knowm.xchange.mercadobitcoin.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.mercadobitcoin.MercadoBitcoinExchange;
import org.knowm.xchange.mercadobitcoin.MercadoBitcoinUtils;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class TradesFetchIntegration {

  @Test
  public void orderbookFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(MercadoBitcoinExchange.class);
    MarketDataService marketDataService = exchange.getMarketDataService();

    Trades trades;

    for (CurrencyPair pair : MercadoBitcoinUtils.availablePairs) {
      trades = marketDataService.getTrades(pair);
      System.out.println(trades.toString());
      assertThat(trades).isNotNull();
    }
  }
}
