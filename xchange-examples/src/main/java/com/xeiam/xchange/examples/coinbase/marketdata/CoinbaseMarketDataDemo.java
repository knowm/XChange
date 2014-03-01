/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

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

    Ticker ticker = marketDataService.getTicker(Currencies.BTC, Currencies.USD, true);
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
    for (int i = 0; i < 3; i++) {
      CoinbaseHistoricalSpotPrice historicalSpotPrice = spotPriceHistoryList.get(i);
      System.out.println(historicalSpotPrice);
    }
    System.out.println("...Retrieved " + spotPriceHistoryList.size() + " historical spot rates.");
  }
}
