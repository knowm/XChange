package org.knowm.xchange.examples.coinbase.marketdata;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinbase.CoinbaseExchange;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseCurrency;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseHistoricalSpotPrice;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbasePrice;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseSpotPriceHistory;
import org.knowm.xchange.coinbase.service.CoinbaseMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author jamespedwards42 */
public class CoinbaseMarketDataDemo {

  public static void main(String[] args) throws IOException {

    Exchange coinbaseExchange =
        ExchangeFactory.INSTANCE.createExchange(CoinbaseExchange.class.getName());
    MarketDataService marketDataService = coinbaseExchange.getMarketDataService();

    generic(marketDataService);
    raw((CoinbaseMarketDataService) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

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
    CoinbaseSpotPriceHistory spotPriceHistory =
        marketDataService.getCoinbaseHistoricalSpotRates(page);
    List<CoinbaseHistoricalSpotPrice> spotPriceHistoryList = spotPriceHistory.getSpotPriceHistory();
    for (CoinbaseHistoricalSpotPrice coinbaseHistoricalSpotPrice : spotPriceHistoryList) {
      System.out.println(coinbaseHistoricalSpotPrice);
    }
    System.out.println("...Retrieved " + spotPriceHistoryList.size() + " historical spot rates.");
  }
}
