package com.xeiam.xchange.examples.yacuna;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.yacuna.YacunaExchange;
import com.xeiam.xchange.yacuna.service.polling.YacunaMarketDataService;

/**
 * Created by Yingzhe on 12/28/2014.
 */
public class YacunaMarketDataDemo {

  public static void main(String[] args) throws Exception {

    YacunaExchange exchange = new YacunaExchange();
    exchange.applySpecification(exchange.getDefaultExchangeSpecification());
    YacunaMarketDataService marketDataService = (YacunaMarketDataService) exchange.getPollingMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("XBT", "EUR"));
    System.out.println(ticker.toString());
  }
}
