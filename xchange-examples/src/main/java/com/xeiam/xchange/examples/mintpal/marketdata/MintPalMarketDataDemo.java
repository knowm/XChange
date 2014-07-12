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
package com.xeiam.xchange.examples.mintpal.marketdata;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.examples.mintpal.MintPalDemoUtils;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicOrders;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicTrade;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalTicker;
import com.xeiam.xchange.mintpal.service.polling.MintPalMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * @author jamespedwards42
 */
public class MintPalMarketDataDemo {

  public static void main(String[] args) throws IOException {

    Exchange mintPalExchange = MintPalDemoUtils.createExchange();
    generic(mintPalExchange);
    raw(mintPalExchange);
  }

  private static void generic(Exchange mintPal) throws IOException {

    PollingMarketDataService mintPalGenericMarketDataService = mintPal.getPollingMarketDataService();

    Collection<CurrencyPair> currencyPairs = mintPalGenericMarketDataService.getExchangeSymbols();
    System.out.println(currencyPairs);

    Ticker ticker = mintPalGenericMarketDataService.getTicker(CurrencyPair.LTC_BTC);
    System.out.println(ticker);

    OrderBook orderBook = mintPalGenericMarketDataService.getOrderBook(CurrencyPair.LTC_BTC);
    System.out.println(orderBook);

    Trades trades = mintPalGenericMarketDataService.getTrades(CurrencyPair.LTC_BTC);
    System.out.println(trades);
  }

  private static void raw(Exchange mintPal) throws IOException {

    MintPalMarketDataServiceRaw mintPalSpecificMarketDataService = (MintPalMarketDataServiceRaw) mintPal.getPollingMarketDataService();

    List<MintPalTicker> tickers = mintPalSpecificMarketDataService.getAllMintPalTickers();
    System.out.println(tickers);

    MintPalTicker ticker = mintPalSpecificMarketDataService.getMintPalTicker(CurrencyPair.LTC_BTC);
    System.out.println(ticker);

    List<MintPalPublicOrders> orders = mintPalSpecificMarketDataService.getMintPalOrders(CurrencyPair.LTC_BTC);
    System.out.println(orders);

    List<MintPalPublicTrade> trades = mintPalSpecificMarketDataService.getMintPalTrades(CurrencyPair.LTC_BTC);
    System.out.println(trades);
  }

}
