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

/**
 * @author David Yam
 */
public final class BTCChinaGetOrdersRequest extends BTCChinaRequest {

  public static final String DEFAULT_MARKET = "BTCCNY";
  public static final String ALL_MARKET = "ALL";

  public static final int DEFAULT_LIMIT = 1000;

  private static final String METHOD_NAME = "getOrders";

  /**
   * Constructor (Optional parameter, default openOnly = false)
   * 
   * @deprecated Use {@link #BTCChinaGetOrdersRequest(Boolean, String, Integer, Integer)} instead.
   */
  @Deprecated
  public BTCChinaGetOrdersRequest() {

    method = METHOD_NAME;
    params = "[]";
  }

  /**
   * Constructor
   * 
   * @param openOnly
   * @deprecated this constructor is incorrect,
   *             it will fail into `invalid parameter'(error code = -32019).
   */
  @Deprecated
  public BTCChinaGetOrdersRequest(Boolean openOnly) {

    method = METHOD_NAME;
    params = "[" + ((openOnly) ? 1 : 0) + "]";
  }

  /**
   * Constructor.
   *
   * @param openOnly Default is 'true'. Only open orders are returned.
   * @param market Default to "BTCCNY". [ BTCCNY | LTCCNY | LTCBTC | ALL]
   * @param limit Limit the number of transactions, default value is 1000.
   * @param offset Start index used for pagination, default value is 0.
   */
  public BTCChinaGetOrdersRequest(Boolean openOnly, String market, Integer limit, Integer offset) {

    method = METHOD_NAME;
    params =
        String.format("[%1$s,\"%2$s\",%3$d,%4$d]", openOnly == null ? true : openOnly.booleanValue(), market == null ? DEFAULT_MARKET : market, limit == null ? DEFAULT_LIMIT : limit.intValue(),
            offset == null ? 0 : offset.intValue());
  }

  @Override
  public String toString() {

    return String.format("BTCChinaGetOrdersRequest{id=%d, method=%s, params=%s}", id, method, params);
  }

}
