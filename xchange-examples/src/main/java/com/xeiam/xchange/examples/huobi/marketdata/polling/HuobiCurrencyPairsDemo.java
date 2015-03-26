package com.xeiam.xchange.examples.huobi.marketdata.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.huobi.HuobiExchange;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class HuobiCurrencyPairsDemo {

  public static void main(String[] args) throws IOException {

    ExchangeSpecification exSpec = new ExchangeSpecification(HuobiExchange.class);

    Exchange huobiExchange = ExchangeFactory.INSTANCE.createExchange(exSpec);

    generic(huobiExchange);
  }

  private static void generic(Exchange okcoinExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = okcoinExchange.getPollingMarketDataService();
    List<CurrencyPair> currencyPairs = marketDataService.getExchangeSymbols();
    for (CurrencyPair currencyPair : currencyPairs) {
      System.out.println(currencyPair.toString());
    }
  }

}
