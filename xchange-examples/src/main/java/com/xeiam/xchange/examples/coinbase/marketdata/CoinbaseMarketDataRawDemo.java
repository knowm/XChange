package com.xeiam.xchange.examples.coinbase.marketdata;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.coinbase.CoinbaseExchange;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseCurrency;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseHistoricalSpotPrice;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbasePrice;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseAmount;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseSpotPriceHistory;
import com.xeiam.xchange.coinbase.service.polling.CoinbaseMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class CoinbaseMarketDataRawDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Coinbase exchange API using default settings
    Exchange coinbaseExchange = ExchangeFactory.INSTANCE.createExchange(CoinbaseExchange.class.getName());

    generic(coinbaseExchange);
    raw(coinbaseExchange);
  }

  private static void generic(Exchange coinbaseExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService coinbaseGenericMarketDataService = coinbaseExchange.getPollingMarketDataService();

  }

  private static void raw(Exchange coinbaseExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    CoinbaseMarketDataServiceRaw coinbaseRawMarketDataService = (CoinbaseMarketDataServiceRaw) coinbaseExchange.getPollingMarketDataService();

    List<CoinbaseCurrency> currencies = coinbaseRawMarketDataService.getCurrencies();
    System.out.println(currencies);

    Map<String, BigDecimal> exchangeRates = coinbaseRawMarketDataService.getCurrencyExchangeRates();
    System.out.println(exchangeRates);

    CoinbasePrice buyPrice = coinbaseRawMarketDataService.getBuyPrice(new BigDecimal("1.57"));
    System.out.println(buyPrice);

    CoinbasePrice sellPrice = coinbaseRawMarketDataService.getSellPrice();
    System.out.println(sellPrice);

    CoinbaseAmount spotRate = coinbaseRawMarketDataService.getSpotRate("EUR");
    System.out.println(spotRate);

    int page = 3;
    CoinbaseSpotPriceHistory spotPriceHistory = coinbaseRawMarketDataService.getHistoricalSpotRates(page);
    List<CoinbaseHistoricalSpotPrice> spotPriceHistoryList = spotPriceHistory.getSpotPriceHistory();
    for (int i = 0; i < 3; i++) {
      CoinbaseHistoricalSpotPrice historicalSpotPrice = spotPriceHistoryList.get(i);
      System.out.println(historicalSpotPrice);
    }
    System.out.println("Retrieved " + spotPriceHistoryList.size() + " historical spot rates.");
  }
}
