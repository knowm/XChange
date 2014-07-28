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
package com.xeiam.xchange.btcchina.dto.trade.request;

import com.xeiam.xchange.btcchina.dto.BTCChinaRequest;

public class BTCChinaGetMarketDepthRequest extends BTCChinaRequest {

  private static final int DEFAULT_LIMIT = 10;

  private static final String METHOD_NAME = "getMarketDepth2";

  /**
   * Constructs request for getting the complete market depth.
   *
   * @param limit Limit number of orders returned. Default is 10 per side.
   * @param market Default to "BTCCNY". [ BTCCNY | LTCCNY | LTCBTC ].
   */
  public BTCChinaGetMarketDepthRequest(Integer limit, String market) {
    this.method = METHOD_NAME;

    if (limit == null && market == null) {
      this.params = "[]";
    } else if (market == null) {
      this.params = String.format("[%d]", limit);
    } else {
      this.params = String.format("[%d,\"%s\"]", limit == null ? DEFAULT_LIMIT : limit, market);
    }
  }

}
