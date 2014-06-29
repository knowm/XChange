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
package com.xeiam.xchange.vaultofsatoshi.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.vaultofsatoshi.VaultOfSatoshi;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VaultOfSatoshiDepth;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VaultOfSatoshiTicker;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VaultOfSatoshiTrade;

/**
 * <p>
 * Implementation of the market data service for VaultOfSatoshi
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class VaultOfSatoshiMarketDataServiceRaw extends VaultOfSatoshiBasePollingService {

  private final VaultOfSatoshi vaultOfSatoshi;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public VaultOfSatoshiMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.vaultOfSatoshi = RestProxyFactory.createProxy(VaultOfSatoshi.class, exchangeSpecification.getSslUri());
  }

  public VaultOfSatoshiTicker getVosTicker(CurrencyPair pair) throws IOException {

    // Request data
    VaultOfSatoshiTicker vosTicker = vaultOfSatoshi.getTicker(pair.baseSymbol, pair.counterSymbol).getTicker();

    // Adapt to XChange DTOs
    return vosTicker;
  }

  public VaultOfSatoshiDepth getVosOrderBook(CurrencyPair pair) throws IOException {

    // Request data
    VaultOfSatoshiDepth vosDepth = vaultOfSatoshi.getFullDepth(pair.baseSymbol, pair.counterSymbol).getDepth();

    return vosDepth;
  }

  public List<VaultOfSatoshiTrade> getVosTrades(CurrencyPair pair) throws IOException {

    // Request data
    List<VaultOfSatoshiTrade> vosTrades = vaultOfSatoshi.getTrades(pair.baseSymbol, pair.counterSymbol, null, 100).getTrades();

    return vosTrades;
  }

  public List<VaultOfSatoshiTrade> getVosTrades(CurrencyPair pair, Long sinceId, int count) throws IOException {

    // Request data
    List<VaultOfSatoshiTrade> vosTrades = vaultOfSatoshi.getTrades(pair.baseSymbol, pair.counterSymbol, sinceId, count).getTrades();

    return vosTrades;
  }

}
