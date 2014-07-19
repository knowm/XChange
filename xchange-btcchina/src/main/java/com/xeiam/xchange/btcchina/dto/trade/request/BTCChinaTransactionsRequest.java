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
package com.xeiam.xchange.btcchina.dto.trade.request;

import com.xeiam.xchange.btcchina.dto.BTCChinaRequest;

public class BTCChinaTransactionsRequest extends BTCChinaRequest {

  public static final String TYPE_ALL = "all";

  /**
   * Constructor
   * 
   * @param orderId
   */
  public BTCChinaTransactionsRequest() {

    method = "getTransactions";
    params = "[]";
  }

  /**
   * Constructs a getting transactions log request.
   *
   * @param type Fetch transactions by type.
   * Default is 'all'.
   * Available types 'all | fundbtc | withdrawbtc | fundmoney | withdrawmoney
   * | refundmoney | buybtc | sellbtc | buyltc | sellltc | tradefee | rebate '
   * @param limit Limit the number of transactions, default value is 10.
   * @param offset Start index used for pagination, default value is 0.
   */
  public BTCChinaTransactionsRequest(
      String type, Integer limit, Integer offset) {
    method = "getTransactions";
    params = String.format("[\"%1$s\",%2$d,%3$d]",
      type == null ? TYPE_ALL : type,
      limit == null ? 10 : limit.intValue(),
      offset == null ? 0 : offset.intValue());
  }

  @Override
  public String toString() {

    return String.format("BTCChinaTransactionsRequest{id=%d, method=%s, params=%s}", id, method, params);
  }
}
