package org.knowm.xchange.examples.openexchangerates.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.oer.OERExchange;
import org.knowm.xchange.oer.dto.marketdata.OERRates;
import org.knowm.xchange.oer.service.OERMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Demonstrate requesting Ticker at Open Exchange Rates */
public class TickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get the Open Exchange Rates exchange API
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(OERExchange.class.getName());
    exchangeSpecification.setPlainTextUri("http://openexchangerates.org");
    exchangeSpecification.setApiKey("ab32c922bca749ec9345b4717914ee1f");

    Exchange openExchangeRates = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    generic(openExchangeRates);
    raw(openExchangeRates);
  }

  private static void generic(Exchange openExchangeRates) throws IOException {

    // Interested in the market data feed
    MarketDataService marketDataService = openExchangeRates.getMarketDataService();

    // Get the latest ticker data showing EUR/USD
    Ticker ticker = marketDataService.getTicker(CurrencyPair.EUR_USD);
    System.out.println("Last: " + ticker.getLast().toString());

    // Alternate way to print out ticker currency and amount
    System.out.println("ticker: " + ticker.toString());

    // Request another ticker. it will return a cached object
    ticker = marketDataService.getTicker(CurrencyPair.JPY_USD);
    System.out.println("cached Last: " + ticker.getLast().toString());

    // Request BTC ticker. it will return a cached object
    ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
    System.out.println("cached Last: " + ticker.getLast().toString());
  }

  private static void raw(Exchange openExchangeRates) throws IOException {

    OERMarketDataServiceRaw oERMarketDataServiceRaw =
        (OERMarketDataServiceRaw) openExchangeRates.getMarketDataService();

    // Get the latest ticker data showing BTC to EUR
    OERRates oERRates = oERMarketDataServiceRaw.getOERTicker();

    System.out.println(oERRates.toString());
  }
}
