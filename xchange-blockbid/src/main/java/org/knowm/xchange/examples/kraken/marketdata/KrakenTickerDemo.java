package org.knowm.xchange.examples.kraken.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.kraken.dto.marketdata.KrakenTicker;
import org.knowm.xchange.kraken.service.KrakenMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class KrakenTickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Kraken exchange API using default settings
    Exchange krakenExchange =
        ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());

    generic(krakenExchange);
    raw(krakenExchange);
  }

  private static void generic(Exchange krakenExchange) throws IOException {

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = krakenExchange.getMarketDataService();

    // Get the latest ticker data showing BTC to EUR
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_EUR);

    System.out.println("Ticker: " + ticker.toString());
    System.out.println("Currency: " + Currency.EUR);
    System.out.println("Last: " + ticker.getLast().toString());
    System.out.println("Volume: " + ticker.getVolume().toString());
    System.out.println("High: " + ticker.getHigh().toString());
    System.out.println("Low: " + ticker.getLow().toString());
  }

  private static void raw(Exchange krakenExchange) throws IOException {

    // Interested in the public market data feed (no authentication)
    KrakenMarketDataServiceRaw krakenMarketDataService =
        (KrakenMarketDataServiceRaw) krakenExchange.getMarketDataService();

    // Get the latest ticker data showing BTC to EUR
    KrakenTicker ticker = krakenMarketDataService.getKrakenTicker(CurrencyPair.BTC_EUR);

    System.out.println("Ticker: " + ticker.toString());
    System.out.println("Currency: " + Currency.EUR);
    System.out.println("Last: " + ticker.getClose());
    System.out.println("Volume: " + ticker.get24HourVolume().toString());
    System.out.println("High: " + ticker.get24HourHigh().toString());
    System.out.println("Low: " + ticker.get24HourLow().toString());
  }
}
