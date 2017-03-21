package org.knowm.xchange.examples.btc38;

import org.knowm.xchange.btc38.Btc38Exchange;
import org.knowm.xchange.btc38.service.Btc38MarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

/**
 * Created by Yingzhe on 12/20/2014.
 */
public class Btc38MarketDataDemo {

  public static void main(String[] args) throws Exception {

    Btc38Exchange exchange = new Btc38Exchange();
    exchange.applySpecification(exchange.getDefaultExchangeSpecification());
    Btc38MarketDataService marketDataService = (Btc38MarketDataService) exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("DOGE", "BTC"));
    System.out.println(ticker.toString());
  }
}
