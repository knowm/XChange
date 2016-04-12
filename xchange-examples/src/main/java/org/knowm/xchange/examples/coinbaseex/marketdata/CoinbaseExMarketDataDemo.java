package org.knowm.xchange.examples.coinbaseex.marketdata;

import org.knowm.xchange.coinbaseex.CoinbaseExExchange;
import org.knowm.xchange.coinbaseex.service.polling.CoinbaseExMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

/**
 * Created by Yingzhe on 4/12/2015.
 */
public class CoinbaseExMarketDataDemo {

  public static void main(String[] args) throws Exception {

    CoinbaseExExchange exchange = new CoinbaseExExchange();
    exchange.applySpecification(exchange.getDefaultExchangeSpecification());
    CoinbaseExMarketDataService marketDataService = (CoinbaseExMarketDataService) exchange.getPollingMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "USD"));
    System.out.println(ticker.toString());
  }
}
