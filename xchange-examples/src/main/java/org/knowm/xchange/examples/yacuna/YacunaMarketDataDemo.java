package org.knowm.xchange.examples.yacuna;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.yacuna.YacunaExchange;
import org.knowm.xchange.yacuna.service.polling.YacunaMarketDataService;

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
