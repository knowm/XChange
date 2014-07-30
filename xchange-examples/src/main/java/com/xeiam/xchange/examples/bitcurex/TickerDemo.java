package com.xeiam.xchange.examples.bitcurex;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitcurex.BitcurexExchange;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTicker;
import com.xeiam.xchange.bitcurex.service.polling.BitcurexMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * Demonstrate requesting Ticker at Bitcurex
 */
public class TickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get the Bitcurex exchange API using default settings
    Exchange bitcurexEur = ExchangeFactory.INSTANCE.createExchange(BitcurexExchange.class.getName());
    requestData(bitcurexEur, CurrencyPair.BTC_EUR);

    Exchange bitcurexPln = ExchangeFactory.INSTANCE.createExchange(BitcurexExchange.class.getName());
    bitcurexPln.applySpecification(((BitcurexExchange) bitcurexPln).getDefaultExchangePLNSpecification());
    requestData(bitcurexPln, CurrencyPair.BTC_PLN);
  }

  private static void requestData(Exchange bitcurex, CurrencyPair pair) throws IOException {

    generic(bitcurex, pair);
    raw(bitcurex, pair.counterSymbol);
  }

  private static void generic(Exchange bitcurex, CurrencyPair pair) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = bitcurex.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to EUR
    Ticker ticker = marketDataService.getTicker(pair);
    double value = ticker.getLast().doubleValue();

    System.out.println("Last: " + ticker.getLast().toString());
    System.out.println("Volume: " + ticker.getVolume().toString());
    System.out.println("High: " + ticker.getHigh().toString());
    System.out.println("Low: " + ticker.getLow().toString());
  }

  private static void raw(Exchange bitcurex, String currency) throws IOException {

    BitcurexMarketDataServiceRaw bitcurexMarketDataServiceRaw = (BitcurexMarketDataServiceRaw) bitcurex.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to EUR
    BitcurexTicker ticker = bitcurexMarketDataServiceRaw.getBitcurexTicker(currency);

    System.out.println("Last: " + ticker.getLast().toString());
    System.out.println("Vol: " + ticker.getVol());
    System.out.println("High: " + ticker.getHigh().toString());
    System.out.println("Low: " + ticker.getLow().toString());
  }
}
