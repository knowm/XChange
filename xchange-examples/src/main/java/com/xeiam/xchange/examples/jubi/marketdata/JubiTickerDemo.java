package com.xeiam.xchange.examples.jubi.marketdata;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.jubi.JubiExchange;
import com.xeiam.xchange.jubi.service.polling.JubiMarketDataService;

/**
 * Created by Yingzhe on 3/19/2015.
 */
public class JubiTickerDemo {

  public static void main(String[] args) throws Exception {

    JubiExchange exchange = new JubiExchange();
    exchange.applySpecification(exchange.getDefaultExchangeSpecification());
    JubiMarketDataService marketDataService = (JubiMarketDataService) exchange.getPollingMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "CNY"));
    System.out.println(ticker.toString());
  }
}
