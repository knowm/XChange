package com.xeiam.xchange.examples.kraken.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.kraken.KrakenExchange;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenTicker;
import com.xeiam.xchange.kraken.service.polling.KrakenMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class KrakenTickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Kraken exchange API using default settings
    Exchange krakenExchange = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());

    generic(krakenExchange);
    raw(krakenExchange);
  }

  private static void generic(Exchange krakenExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = krakenExchange.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to EUR
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_EUR);

    System.out.println("Ticker: " + ticker.toString());
    System.out.println("Currency: " + Currencies.EUR);
    System.out.println("Last: " + ticker.getLast().toString());
    System.out.println("Volume: " + ticker.getVolume().toString());
    System.out.println("High: " + ticker.getHigh().toString());
    System.out.println("Low: " + ticker.getLow().toString());
  }

  private static void raw(Exchange krakenExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    KrakenMarketDataServiceRaw krakenMarketDataService = (KrakenMarketDataServiceRaw) krakenExchange.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to EUR
    KrakenTicker ticker = krakenMarketDataService.getKrakenTicker(CurrencyPair.BTC_EUR);

    System.out.println("Ticker: " + ticker.toString());
    System.out.println("Currency: " + Currencies.EUR);
    System.out.println("Last: " + ticker.getClose());
    System.out.println("Volume: " + ticker.get24HourVolume().toString());
    System.out.println("High: " + ticker.get24HourHigh().toString());
    System.out.println("Low: " + ticker.get24HourLow().toString());
  }
}
