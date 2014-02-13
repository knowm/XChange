package com.xeiam.xchange.examples.coinbase.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.coinbase.CoinbaseExchange;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseSpotPriceHistory;
import com.xeiam.xchange.coinbase.service.polling.CoinbaseMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class CoinbaseMarketDataRawDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Justcoin exchange API using default settings
    Exchange coinbaseExchange = ExchangeFactory.INSTANCE.createExchange(CoinbaseExchange.class.getName());
    // generic(coinbaseExchange);
    raw(coinbaseExchange);
  }

  private static void generic(Exchange coinbaseExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService coinbaseGenericMarketDataService = coinbaseExchange.getPollingMarketDataService();

  }

  private static void raw(Exchange coinbaseExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    CoinbaseMarketDataServiceRaw coinbaseRawMarketDataService = (CoinbaseMarketDataServiceRaw) coinbaseExchange.getPollingMarketDataService();

    /*
     * List<CoinbaseCurrency> currencies = coinbaseRawMarketDataService.getCurrencies();
     * System.out.println(currencies);
     * Map<String, BigDecimal> exchangeRates = coinbaseRawMarketDataService.getCurrencyExchangeRates();
     * System.out.println(exchangeRates);
     * CoinbasePrice buyPrice = coinbaseRawMarketDataService.getBuyPrice(new BigDecimal("1.57"));
     * System.out.println(buyPrice);
     * CoinbasePrice sellPrice = coinbaseRawMarketDataService.getSellPrice();
     * System.out.println(sellPrice);
     * CoinbaseRate spotRate = coinbaseRawMarketDataService.getSpotRate("EUR");
     * System.out.println(spotRate);
     */
    
    int page = 3;
    CoinbaseSpotPriceHistory spotPriceHistory = coinbaseRawMarketDataService.getHistoricalSpotRates(page);
    System.out.println(spotPriceHistory);
    System.out.println("Retrieved " + spotPriceHistory.getSpotPriceHistory().size() + " historical spot rates.");
  }
}
