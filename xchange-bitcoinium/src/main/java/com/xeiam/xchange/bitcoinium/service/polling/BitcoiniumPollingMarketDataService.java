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
package com.xeiam.xchange.bitcoinium.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoinium.Bitcoinium;
import com.xeiam.xchange.bitcoinium.BitcoiniumUtils;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.utils.Assert;

/**
 * <p>
 * Implementation of the market data service for Bitcoinium
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BitcoiniumPollingMarketDataService extends BasePollingExchangeService {

  private final Bitcoinium bitcoinium;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BitcoiniumPollingMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bitcoinium = RestProxyFactory.createProxy(Bitcoinium.class, exchangeSpecification.getSslUri());
  }

  public BitcoiniumTicker getBitcoiniumTicker(String tradableIdentifier, String currency, String exchange) throws IOException {

    String pair = BitcoiniumUtils.createCurrencyPairString(tradableIdentifier, currency, exchange);

    verify(pair);

    // Request data
    BitcoiniumTicker bitcoiniumTicker = bitcoinium.getTicker(pair);

    // Adapt to XChange DTOs
    return bitcoiniumTicker;
  }

  // public BitcoiniumOrderBook getOrderBook(String tradableIdentifier, String currency, Object... args) {
  //
  // verify(tradableIdentifier, currency);
  // String[] currencyExchange = currency.split("_");
  //
  // // Request data
  // BitcoiniumOrderbook bitcoiniumDepth = bitcoinium.getFullDepth(currencyExchange[0], tradableIdentifier, currencyExchange[1], "20p");
  //
  // // Adapt to XChange DTOs
  // List<LimitOrder> asks = BitcoiniumAdapters.adaptOrders(bitcoiniumDepth, currencyExchange[1], "ask", "");
  // List<LimitOrder> bids = BitcoiniumAdapters.adaptOrders(bitcoiniumDepth, currencyExchange[1], "bid", "");
  //
  // return new OrderBook(null, asks, bids);
  // }

  /**
   * verify
   * 
   * @param pair
   */
  private void verify(String pair) {

    Assert.isTrue(BitcoiniumUtils.isValidCurrencyPair(pair), pair + " is not a valid currency pair!");
  }

}
