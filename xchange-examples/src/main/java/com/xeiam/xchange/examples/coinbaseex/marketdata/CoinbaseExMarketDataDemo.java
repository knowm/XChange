package com.xeiam.xchange.examples.coinbaseex.marketdata;

import com.xeiam.xchange.coinbaseex.CoinbaseExExchange;
import com.xeiam.xchange.coinbaseex.service.polling.CoinbaseExMarketDataService;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;

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
