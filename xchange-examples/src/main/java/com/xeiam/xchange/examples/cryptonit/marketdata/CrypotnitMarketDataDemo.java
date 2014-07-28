/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.examples.cryptonit.marketdata;

import java.io.IOException;
import java.util.Collection;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitOrders;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitTicker;
import com.xeiam.xchange.cryptonit.v2.service.polling.CryptonitMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.examples.cryptonit.CryptonitExampleUtils;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class CrypotnitMarketDataDemo {

  public static void main(String[] args) throws Exception {

    Exchange cryptonitExchange = CryptonitExampleUtils.createExchange();
    PollingMarketDataService marketDataService = cryptonitExchange.getPollingMarketDataService();

    generic(marketDataService);
    raw((CryptonitMarketDataServiceRaw) marketDataService);
  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    Collection<CurrencyPair> currencyPairs = marketDataService.getExchangeSymbols();
    System.out.println(currencyPairs);

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
    System.out.println(ticker);

    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);
    System.out.println(orderBook);

    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD);
    System.out.println(trades);
  }

  private static void raw(CryptonitMarketDataServiceRaw marketDataService) throws IOException {

    CryptonitTicker ticker = marketDataService.getCryptonitTicker(CurrencyPair.BTC_USD);
    System.out.println(ticker);

    CryptonitOrders marketDepth = marketDataService.getCryptonitAsks(CurrencyPair.BTC_USD, 10);
    System.out.println(marketDepth);

    CryptonitOrders trades = marketDataService.getCryptonitTrades(CurrencyPair.BTC_USD, 10);
    System.out.println(trades);
  }
}
