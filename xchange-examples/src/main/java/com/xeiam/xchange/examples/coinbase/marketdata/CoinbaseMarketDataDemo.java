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
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbasePrice;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseSpotPriceHistory;
import com.xeiam.xchange.coinbase.service.polling.CoinbaseMarketDataService;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author jamespedwards42
 */
public class CoinbaseMarketDataDemo {

  public static void main(String[] args) throws IOException {

    Exchange coinbaseExchange = ExchangeFactory.INSTANCE.createExchange(CoinbaseExchange.class.getName());
    PollingMarketDataService marketDataService = coinbaseExchange.getPollingMarketDataService();

    generic(marketDataService);
    raw((CoinbaseMarketDataService) marketDataService);
  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD, true);
    System.out.println(ticker);
  }

  private static void raw(CoinbaseMarketDataService marketDataService) throws IOException {

    List<CoinbaseCurrency> currencies = marketDataService.getCoinbaseCurrencies();
    System.out.println(currencies);

    Map<String, BigDecimal> exchangeRates = marketDataService.getCoinbaseCurrencyExchangeRates();
    System.out.println("Exchange Rates: " + exchangeRates);

    String amount = "1.57";
    CoinbasePrice buyPrice = marketDataService.getCoinbaseBuyPrice(new BigDecimal(amount));
    System.out.println("Buy Price for " + amount + " BTC: " + buyPrice);

    CoinbasePrice sellPrice = marketDataService.getCoinbaseSellPrice();
    System.out.println("Sell Price: " + sellPrice);

    CoinbaseMoney spotRate = marketDataService.getCoinbaseSpotRate("EUR");
    System.out.println("Spot Rate: " + spotRate);

    int page = 2;
    CoinbaseSpotPriceHistory spotPriceHistory = marketDataService.getCoinbaseHistoricalSpotRates(page);
    List<CoinbaseHistoricalSpotPrice> spotPriceHistoryList = spotPriceHistory.getSpotPriceHistory();
    for (CoinbaseHistoricalSpotPrice coinbaseHistoricalSpotPrice : spotPriceHistoryList) {
      System.out.println(coinbaseHistoricalSpotPrice);
    }
    System.out.println("...Retrieved " + spotPriceHistoryList.size() + " historical spot rates.");
  }
}
