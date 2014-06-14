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
package com.xeiam.xchange.examples.cavirtex.marketdata;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.virtex.v2.VirtExExchange;
import com.xeiam.xchange.virtex.v2.dto.marketdata.VirtExTrade;
import com.xeiam.xchange.virtex.v2.service.polling.VirtExMarketDataServiceRaw;

/**
 * Demonstrate requesting Trades at VirtEx
 */
public class TradesDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get the VirtEx exchange API using default settings
    Exchange cavirtex = ExchangeFactory.INSTANCE.createExchange(VirtExExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = cavirtex.getPollingMarketDataService();

    generic(marketDataService);
    raw((VirtExMarketDataServiceRaw) marketDataService);

  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    // Get the latest trade data for BTC/CAD
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_CAD);
    System.out.println("Trades, Size= " + trades.getTrades().size());
    System.out.println(trades.toString());
  }

  private static void raw(VirtExMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest trade data for BTC/CAD
    List<VirtExTrade> trades = marketDataService.getVirtExTrades(CurrencyPair.BTC_CAD);
    System.out.println("Trades, default. Size= " + trades.size());
    System.out.println(trades.toString());
  }

}
