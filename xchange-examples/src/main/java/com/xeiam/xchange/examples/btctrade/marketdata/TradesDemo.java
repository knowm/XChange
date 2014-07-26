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
package com.xeiam.xchange.examples.btctrade.marketdata;

import java.io.IOException;
import java.util.Date;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.btctrade.BTCTradeExchange;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeTrade;
import com.xeiam.xchange.btctrade.service.polling.BTCTradeMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * Demonstrate requesting trades at BTCTrade.
 */
public class TradesDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get BTCTrade exchange API using the default settings.
    Exchange btcTrade = ExchangeFactory.INSTANCE.createExchange(BTCTradeExchange.class.getName());
    generic(btcTrade);
    raw(btcTrade);
  }

  private static void generic(Exchange exchange) throws IOException {

    // Interested in the public polling market data feed (no authentication).
    PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();

    // Get the latest 500 trade data for BTC/CNY.
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_CNY);
    System.out.println(trades);
    System.out.println("Trades count: " + trades.getTrades().size() + ", LastID: " + trades.getlastID());

    // Get 500 trades which tid > 0
    trades = marketDataService.getTrades(CurrencyPair.BTC_CNY, 0);
    System.out.println(trades);
    System.out.println("Trades count: " + trades.getTrades().size() + ", LastID: " + trades.getlastID());
  }

  private static void raw(Exchange exchange) {

    // Interested in the public polling market data feed (no authentication).
    BTCTradeMarketDataServiceRaw marketDataService = (BTCTradeMarketDataServiceRaw) exchange.getPollingMarketDataService();

    // Get the latest 500 trade data for BTC/CNY.
    BTCTradeTrade[] trades = marketDataService.getBTCTradeTrades();
    System.out.println("Trades count: " + trades.length);
    for (BTCTradeTrade trade : trades) {
      System.out.println(trade.getTid() + "\t" + new Date(trade.getDate() * 1000) + "\t" + trade.getType() + "\t" + trade.getPrice() + "\t" + trade.getAmount());
    }

    // Get 500 trades which tid > 0
    trades = marketDataService.getBTCTradeTrades(0);
    System.out.println("Trades count: " + trades.length);
    for (BTCTradeTrade trade : trades) {
      System.out.println(trade.getTid() + "\t" + new Date(trade.getDate() * 1000) + "\t" + trade.getType() + "\t" + trade.getPrice() + "\t" + trade.getAmount());
    }
  }

}
