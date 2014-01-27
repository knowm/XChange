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
package com.xeiam.xchange.bitcurex.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcurex.Bitcurex;
import com.xeiam.xchange.bitcurex.BitcurexUtils;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexDepth;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTicker;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.utils.Assert;

/**
 * <p>
 * Implementation of the raw market data service for Bitcurex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BitcurexMarketDataServiceRaw extends BasePollingExchangeService {

  private Bitcurex bitcurex;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BitcurexMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  public BitcurexTicker getBitcurexTicker(String tradableIdentifier, String currency) throws IOException {

    verify(tradableIdentifier, currency);
    this.bitcurex = RestProxyFactory.createProxy(Bitcurex.class, "https://" + currency + ".bitcurex.com");
    // Request data
    BitcurexTicker bitcurexTicker = bitcurex.getTicker();

    return bitcurexTicker;
  }

  public BitcurexDepth getBitcurexOrderBook(String tradableIdentifier, String currency) throws IOException {

    verify(tradableIdentifier, currency);
    this.bitcurex = RestProxyFactory.createProxy(Bitcurex.class, "https://" + currency + ".bitcurex.com");
    // Request data
    BitcurexDepth bitcurexDepth = bitcurex.getFullDepth();

    return bitcurexDepth;
  }

  public BitcurexTrade[] getBitcurexTrades(String tradableIdentifier, String currency) throws IOException {

    verify(tradableIdentifier, currency);
    this.bitcurex = RestProxyFactory.createProxy(Bitcurex.class, "https://" + currency + ".bitcurex.com");
    // Request data
    BitcurexTrade[] bitcurexTrades = bitcurex.getTrades();

    return bitcurexTrades;
  }

  /**
   * Verify
   * 
   * @param tradableIdentifier The tradable identifier (e.g. BTC in BTC/USD)
   * @param currency
   */
  private void verify(String tradableIdentifier, String currency) {

    Assert.notNull(tradableIdentifier, "tradableIdentifier cannot be null");
    Assert.notNull(currency, "currency cannot be null");
    Assert.isTrue(BitcurexUtils.isValidCurrencyPair(new CurrencyPair(tradableIdentifier, currency)), "currencyPair is not valid:" + tradableIdentifier + " " + currency);

  }

}
