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
package com.xeiam.xchange.bitbay;

import org.junit.Before;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitbay.service.polling.BitbayMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;

/**
 * @author kpysniak
 */
public class BitbayExchangeTest {

  private BitbayExchange bitbayExchange;
  private BitbayMarketDataServiceRaw bitbayMarketDataServiceRaw;
  private CurrencyPair currencyPair;

  @Before
  public void setUpTest() {

    this.bitbayExchange = (BitbayExchange) ExchangeFactory.INSTANCE.createExchange(BitbayExchange.class.getName());
    this.bitbayMarketDataServiceRaw = (BitbayMarketDataServiceRaw) bitbayExchange.getPollingMarketDataService();
    this.currencyPair = CurrencyPair.BTC_USD;
  }

  /*
   * @Test
   * public void testBitbayRequest() {
   * try {
   * BitbayTicker bitbayTicker = bitbayMarketDataServiceRaw.getBitbayTicker(currencyPair);
   * System.out.println(bitbayTicker.toString());
   * BitbayOrderBook bitbayOrderBook = bitbayMarketDataServiceRaw.getBitbayOrderBook(currencyPair);
   * System.out.println(bitbayOrderBook.toString());
   * BitbayTrade[] trades = bitbayMarketDataServiceRaw.getBitbayTrades(currencyPair);
   * System.out.println(Arrays.toString(trades));
   * Ticker ticker = bitbayExchange.getPollingMarketDataService().getTicker(currencyPair);
   * System.out.println(ticker.toString());
   * OrderBook orderBook = bitbayExchange.getPollingMarketDataService().getOrderBook(currencyPair);
   * System.out.println(orderBook.toString());
   * Trades tradesAll = bitbayExchange.getPollingMarketDataService().getTrades(currencyPair);
   * System.out.println(tradesAll.toString());
   * } catch (IOException e) {
   * Assert.fail();
   * }
   * }
   */

}
