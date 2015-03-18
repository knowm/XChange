package com.xeiam.xchange.examples.btc38;

import com.xeiam.xchange.btc38.Btc38Exchange;
import com.xeiam.xchange.btc38.service.polling.Btc38MarketDataService;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;

/**
 * Created by Yingzhe on 12/20/2014.
 */
public class Btc38MarketDataDemo {

  public static void main(String[] args) throws Exception {

    Btc38Exchange exchange = new Btc38Exchange();
    exchange.applySpecification(exchange.getDefaultExchangeSpecification());
    Btc38MarketDataService marketDataService = (Btc38MarketDataService) exchange.getPollingMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("DOGE", "BTC"));
    System.out.println(ticker.toString());
  }
}
