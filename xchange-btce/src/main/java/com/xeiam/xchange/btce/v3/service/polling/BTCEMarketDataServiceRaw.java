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
package com.xeiam.xchange.btce.v3.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v3.BTCE;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEDepthWrapper;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEExchangeInfo;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCETickerWrapper;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCETradesWrapper;

/**
 * Author: brox
 * Since: 2014-02-12
 */
public class BTCEMarketDataServiceRaw extends BTCEBasePollingService<BTCE> {

  protected static final int FULL_SIZE = 2000;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public BTCEMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BTCE.class, exchangeSpecification);
  }

  /**
   * @param pairs Dash-delimited string of currency pairs to retrieve (e.g. "btc_usd-ltc_btc")
   * @return BTCETickerWrapper object
   * @throws IOException
   */
  public BTCETickerWrapper getBTCETicker(String pairs) throws IOException {

    return btce.getTicker(pairs.toLowerCase(), 1);
  }

  /**
   * Get market depth from exchange
   * 
   * @param pairs Dash-delimited string of currency pairs to retrieve (e.g. "btc_usd-ltc_btc")
   * @param size Integer value from 1 to 2000 -> get corresponding number of items
   * @return BTCEDepthWrapper object
   * @throws IOException
   */
  public BTCEDepthWrapper getBTCEDepth(String pairs, int size) throws IOException {

    if (size < 1) {
      size = 1;
    }

    if (size > FULL_SIZE) {
      size = FULL_SIZE;
    }

    return btce.getDepth(pairs.toLowerCase(), size, 1);
  }

  /**
   * Get recent trades from exchange
   * 
   * @param pairs Dash-delimited string of currency pairs to retrieve (e.g. "btc_usd-ltc_btc")
   * @param size Integer value from 1 to 2000 -> get corresponding number of items
   * @return BTCETradesWrapper object
   * @throws IOException
   */
  public BTCETradesWrapper getBTCETrades(String pairs, int size) throws IOException {

    if (size < 1) {
      size = 1;
    }

    if (size > FULL_SIZE) {
      size = FULL_SIZE;
    }

    return btce.getTrades(pairs.toLowerCase(), size, 1);
  }

  /**
   * Get BTC-e exchange info
   * 
   * @return BTCEExchangeInfo object
   * @throws IOException
   */
  public BTCEExchangeInfo getBTCEInfo() throws IOException {

    return btce.getInfo();
  }

}
