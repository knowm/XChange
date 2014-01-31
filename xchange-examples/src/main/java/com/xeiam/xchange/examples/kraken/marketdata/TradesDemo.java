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
package com.xeiam.xchange.examples.kraken.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.kraken.KrakenExchange;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenTrades;
import com.xeiam.xchange.kraken.service.polling.KrakenMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class TradesDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Kraken exchange API using default settings
    Exchange krakenExchange = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());

    generic(krakenExchange);
    raw(krakenExchange);
  }

  private static void generic(Exchange krakenExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = krakenExchange.getPollingMarketDataService();

    // Get the latest trade data for BTC/XRP
    Trades trades = marketDataService.getTrades(Currencies.BTC, Currencies.XRP);
    System.out.println(trades.toString());
    System.out.println("Trades size: " + trades.getTrades().size());

    // Get the latest trade data for BTC/XRP
    trades = marketDataService.getTrades(Currencies.BTC, Currencies.XRP, 1385579655033171108L);
    System.out.println(trades.toString());
    System.out.println("Trades size: " + trades.getTrades().size());
  }
  
  private static void raw(Exchange krakenExchange) throws IOException {
    
    // Interested in the public polling market data feed (no authentication)
    KrakenMarketDataServiceRaw krakenMarketDataService = (KrakenMarketDataServiceRaw) krakenExchange.getPollingMarketDataService();

    // Get the latest trade data for BTC/XRP
    KrakenTrades trades = krakenMarketDataService.getKrakenTrades(Currencies.BTC, Currencies.XRP);
    long last = trades.getLast();
    printKrakenTrades(trades.getTradesPerCurrencyPair(Currencies.BTC, Currencies.XRP));

    System.out.println("Trades size: " + trades.getTradesPerCurrencyPair(Currencies.BTC, Currencies.XRP).length);
    System.out.println("Last: " + last);
    
    // Poll for any new trades since last id
    trades = krakenMarketDataService.getKrakenTrades(Currencies.BTC, Currencies.XRP, last);
    printKrakenTrades(trades.getTradesPerCurrencyPair(Currencies.BTC, Currencies.XRP));
    
    System.out.println("Trades size: " + trades.getTradesPerCurrencyPair(Currencies.BTC, Currencies.XRP).length);
  }
  
  private static void printKrakenTrades(final String[][] krakenTrades) {
    for (String[] krakenTradeData : krakenTrades) {
      System.out.print("price=" + krakenTradeData[0]);
      System.out.print(", volume=" + krakenTradeData[1]);
      System.out.print(", time=" + krakenTradeData[2]);
      System.out.print(", type=" + krakenTradeData[3]);
      System.out.print(", orderType=" + krakenTradeData[4]);
      System.out.println(", miscellaneous=" + krakenTradeData[5]);     
    }
  }
}
