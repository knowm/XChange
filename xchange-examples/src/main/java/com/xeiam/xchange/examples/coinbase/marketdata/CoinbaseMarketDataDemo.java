package com.xeiam.xchange.examples.coinbase.marketdata;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.coinbase.CoinbaseExchange;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseAmount;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseCurrency;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseHistoricalSpotPrice;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbasePrice;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseSpotPriceHistory;
import com.xeiam.xchange.coinbase.service.polling.CoinbaseMarketDataService;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class CoinbaseMarketDataDemo {

  public static void main(String[] args) throws IOException {

    Exchange coinbaseExchange = ExchangeFactory.INSTANCE.createExchange(CoinbaseExchange.class.getName());
    PollingMarketDataService marketDataService = coinbaseExchange.getPollingMarketDataService();

    generic(marketDataService);
    raw((CoinbaseMarketDataService) marketDataService);
  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(Currencies.BTC, Currencies.USD);
    System.out.println(ticker);
  }

  private static void raw(CoinbaseMarketDataService marketDataService) throws IOException {

    List<CoinbaseCurrency> currencies = marketDataService.getCoinbaseCurrencies();
    System.out.println(currencies);

    /*
     * CoinbaseUser newUser = CoinbaseUser.createNewCoinbaseUser("james.p.edwards42@gmail.com", "test1234");
     * CoinbaseUser createdUser = marketDataService.createCoinbaseUser(newUser, "c044110d72cb725bc94ea4361ab37f312eeda8d27df30d2bcc47825723fcfb58");
     * System.out.println(createdUser);
     * CoinbaseToken token = marketDataService.createCoinbaseToken();
     * System.out.println(token);
     */

    Map<String, BigDecimal> exchangeRates = marketDataService.getCoinbaseCurrencyExchangeRates();
    System.out.println("Exchange Rates: " + exchangeRates);

    String amount = "1.57";
    CoinbasePrice buyPrice = marketDataService.getCoinbaseBuyPrice(new BigDecimal(amount));
    System.out.println("Buy Price for " + amount + " BTC: " + buyPrice);

    CoinbasePrice sellPrice = marketDataService.getCoinbaseSellPrice();
    System.out.println("Sell Price: " + sellPrice);

    CoinbaseAmount spotRate = marketDataService.getCoinbaseSpotRate("EUR");
    System.out.println("Spot Rate: " + spotRate);

    int page = 2;
    CoinbaseSpotPriceHistory spotPriceHistory = marketDataService.getCoinbaseHistoricalSpotRates(page);
    List<CoinbaseHistoricalSpotPrice> spotPriceHistoryList = spotPriceHistory.getSpotPriceHistory();
    for (int i = 0; i < 3; i++) {
      CoinbaseHistoricalSpotPrice historicalSpotPrice = spotPriceHistoryList.get(i);
      System.out.println(historicalSpotPrice);
    }
    System.out.println("...Retrieved " + spotPriceHistoryList.size() + " historical spot rates.");
  }
}
