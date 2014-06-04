package com.xeiam.xchange.examples.bitfinex.marketdata;

import java.io.IOException;
import java.util.Collection;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitfinex.v1.BitfinexExchange;
import com.xeiam.xchange.bitfinex.v1.service.polling.BitfinexMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.ExchangeInfo;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class SymbolsDemo {

  public static void main(String[] args) throws Exception {

    // Use the factory to get Bitfinex exchange API using default settings
    Exchange bitfinex = ExchangeFactory.INSTANCE.createExchange(BitfinexExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = bitfinex.getPollingMarketDataService();

    generic(marketDataService);
    raw((BitfinexMarketDataServiceRaw) marketDataService);

  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    // Get the latest ticker data showing BTC to USD
    ExchangeInfo exchangeInfo = marketDataService.getExchangeInfo();

    System.out.println(exchangeInfo.toString());
  }

  private static void raw(BitfinexMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest ticker data showing BTC to USD
    Collection<String> symbols = marketDataService.getBitfinexSymbols();

    System.out.println(symbols);

    Collection<CurrencyPair> currencyPairs = marketDataService.getExchangeSymbols();

    System.out.println(currencyPairs);

  }
}
