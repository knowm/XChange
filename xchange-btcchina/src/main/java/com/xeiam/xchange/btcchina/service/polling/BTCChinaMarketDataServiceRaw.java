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
package com.xeiam.xchange.btcchina.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcchina.BTCChina;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaDepth;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTicker;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTrade;

/**
 * @author ObsessiveOrange
 *         <p>
 *         Implementation of the market data service for BTCChina
 *         </p>
 *         <ul>
 *         <li>Provides access to various market data values</li>
 *         </ul>
 */
public class BTCChinaMarketDataServiceRaw extends BTCChinaBasePollingService {

  private final BTCChina btcChina;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BTCChinaMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.btcChina = RestProxyFactory.createProxy(BTCChina.class, (String) exchangeSpecification.getExchangeSpecificParameters().get("dataSslUri"));
  }

  public BTCChinaTicker getBTCChinaTicker() throws IOException {

    return btcChina.getTicker();
  }

  public BTCChinaDepth getBTCChinaOrderBook() throws IOException {

    return btcChina.getFullDepth();
  }

  public BTCChinaTrade[] getBTCChinaTrades(Integer sinceTransactionID) throws IOException {

    return btcChina.getTrades(sinceTransactionID);
  }

  public BTCChinaTrade[] getBTCChinaTrades() throws IOException {

    return btcChina.getTrades();
  }

}
