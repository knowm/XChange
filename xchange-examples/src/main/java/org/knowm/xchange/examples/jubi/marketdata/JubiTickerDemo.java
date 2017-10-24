package org.knowm.xchange.examples.jubi.marketdata;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.jubi.JubiExchange;
import org.knowm.xchange.jubi.service.JubiMarketDataService;

/**
 * Created by Yingzhe on 3/19/2015.
 */
public class JubiTickerDemo {

  public static void main(String[] args) throws Exception {

    JubiExchange exchange = new JubiExchange();
    exchange.applySpecification(exchange.getDefaultExchangeSpecification());
    JubiMarketDataService marketDataService = (JubiMarketDataService) exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "CNY"));
    System.out.println(ticker.toString());
  }
}
