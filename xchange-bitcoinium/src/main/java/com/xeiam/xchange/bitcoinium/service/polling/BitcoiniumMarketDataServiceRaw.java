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
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTickerHistory;
import com.xeiam.xchange.bitcoinium.service.BitcoiniumBaseService;
import com.xeiam.xchange.utils.Assert;

/**
 * <p>
 * Implementation of the raw market data service for Bitcoinium
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BitcoiniumMarketDataServiceRaw extends BitcoiniumBaseService {

  private final Bitcoinium bitcoinium;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BitcoiniumMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bitcoinium = RestProxyFactory.createProxy(Bitcoinium.class, exchangeSpecification.getSslUri());
  }

  /**
   * @param tradableIdentifier
   * @param currency
   * @param exchange
   * @return a Bitcoinium Ticker object
   * @throws IOException
   */
  public BitcoiniumTicker getBitcoiniumTicker(String tradableIdentifier, String currency, String exchange) throws IOException {

    String pair = BitcoiniumUtils.createCurrencyPairString(tradableIdentifier, currency, exchange);

    verify(pair);

    // Request data
    BitcoiniumTicker bitcoiniumTicker = bitcoinium.getTicker(pair, exchangeSpecification.getApiKey());

    // Adapt to XChange DTOs
    return bitcoiniumTicker;
  }

  /**
   * @param tradableIdentifier
   * @param currency
   * @param exchange
   * @param timeWindow - The time period of the requested ticker data. Value can be from set: { "10m", "1h", "3h", "12h", "24h", "3d", "7d", "30d", "2M" }
   * @return
   * @throws IOException
   */
  public BitcoiniumTickerHistory getBitcoiniumTickerHistory(String tradableIdentifier, String currency, String exchange, String timeWindow) throws IOException {

    String pair = BitcoiniumUtils.createCurrencyPairString(tradableIdentifier, currency, exchange);

    verify(pair);
    verifyTimeWindow(timeWindow);

    // Request data
    BitcoiniumTickerHistory bitcoiniumTickerHistory = bitcoinium.getTickerHistory(pair, timeWindow, exchangeSpecification.getApiKey());

    return bitcoiniumTickerHistory;
  }

  /**
   * @param tradableIdentifier
   * @param currency
   * @param exchange
   * @param pricewindow - The width of the Orderbook as a percentage plus and minus the current price. Value can be from set: { 2p, 5p, 10p, 20p, 50p, 100p }
   * @return
   */
  public BitcoiniumOrderbook getBitcoiniumOrderbook(String tradableIdentifier, String currency, String exchange, String pricewindow) throws IOException {

    String pair = BitcoiniumUtils.createCurrencyPairString(tradableIdentifier, currency, exchange);

    verify(pair);
    verifyPriceWindow(pricewindow);

    // Request data
    BitcoiniumOrderbook bitcoiniumDepth = bitcoinium.getDepth(pair, pricewindow, exchangeSpecification.getApiKey());

    return bitcoiniumDepth;
  }

  /**
   * verify
   * 
   * @param pair
   */
  private void verify(String pair) {

    Assert.isTrue(BitcoiniumUtils.isValidCurrencyPair(pair), pair + " is not a valid currency pair!");
  }

  /**
   * verify
   * 
   * @param pair
   */
  private void verifyPriceWindow(String priceWindow) {

    Assert.isTrue(BitcoiniumUtils.isValidPriceWindow(priceWindow), priceWindow + " is not a valid price window!");
  }

  /**
   * verify
   * 
   * @param pair
   */
  private void verifyTimeWindow(String timeWindow) {

    Assert.isTrue(BitcoiniumUtils.isValidTimeWindow(timeWindow), timeWindow + " is not a valid time window!");
  }
}
