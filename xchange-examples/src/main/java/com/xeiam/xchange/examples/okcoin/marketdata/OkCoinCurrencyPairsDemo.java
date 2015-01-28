package com.xeiam.xchange.examples.okcoin.marketdata;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.okcoin.OkCoinExchange;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class OkCoinCurrencyPairsDemo {

  public static void main(String[] args) throws IOException {

    ExchangeSpecification exSpec = new ExchangeSpecification(OkCoinExchange.class);

    // flag to set Use_Intl (USD) or China (default)
    exSpec.setExchangeSpecificParametersItem("Use_Intl", true);
    Exchange okcoinExchange = ExchangeFactory.INSTANCE.createExchange(exSpec);

    generic(okcoinExchange);
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
