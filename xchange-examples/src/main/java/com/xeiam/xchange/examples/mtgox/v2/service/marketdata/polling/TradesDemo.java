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
package com.xeiam.xchange.examples.mtgox.v2.service.marketdata.polling;

import java.io.IOException;
import java.util.Date;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.mtgox.v2.MtGoxExchange;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxTradesWrapper;
import com.xeiam.xchange.mtgox.v2.service.polling.MtGoxMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * Test requesting trades at MtGox
 */
public class TradesDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get the version 2 MtGox exchange API using default settings
    Exchange mtGoxExchange = ExchangeFactory.INSTANCE.createExchange(MtGoxExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    PollingMarketDataService marketDataService = mtGoxExchange.getPollingMarketDataService();
    generic(marketDataService);
    raw((MtGoxMarketDataServiceRaw) marketDataService);
  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    // Get trades with "since" parameter
    long time = (new Date().getTime() - 1000 * 60 * 60) * 1000;
    Trades trades = marketDataService.getTrades(Currencies.BTC, Currencies.USD, time);
    System.out.println("Current trades size for BTC / USD: " + trades.getTrades().size());
    System.out.println("Trade 0 : " + trades.getTrades().get(trades.getTrades().size() - 1).toString());

    // Get "most recent" trades
    trades = marketDataService.getTrades(Currencies.BTC, Currencies.USD);
    System.out.println("Current trades size for BTC / USD: " + trades.getTrades().size());
    System.out.println("Trade 0 : " + trades.getTrades().get(trades.getTrades().size() - 1).toString());
  }

  private static void raw(MtGoxMarketDataServiceRaw marketDataService) throws IOException {

    // Get trades with "since" parameter
    long time = (new Date().getTime() - 1000 * 60 * 60) * 1000;
    MtGoxTradesWrapper trades = marketDataService.getMtGoxTrades(Currencies.BTC, Currencies.USD, time);
    System.out.println("Current trades size for BTC / USD: " + trades.getMtGoxTrades().length);
    System.out.println("Trade 0 : " + trades.getMtGoxTrades()[trades.getMtGoxTrades().length - 1].toString());

    // Get "most recent" trades
    trades = marketDataService.getMtGoxTrades(Currencies.BTC, Currencies.USD, null);
    System.out.println("Current trades size for BTC / USD: " + trades.getMtGoxTrades().length);
    System.out.println("Trade 0 : " + trades.getMtGoxTrades()[trades.getMtGoxTrades().length - 1].toString());
  }

}
