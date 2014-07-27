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

import java.math.BigDecimal;

import com.xeiam.xchange.btcchina.BTCChinaExchange;
import com.xeiam.xchange.btcchina.BTCChinaUtils;

/**
 * @author David Yam
 */
public final class BTCChinaSellOrderRequest extends BTCChinaOrderRequest {

  private static final String METHOD_NAME = "sellOrder2";

  /**
   * Constructor
   * 
   * @param price unit price to sell at
   * @param amount number of units to sell
   * @deprecated use {@link #BTCChinaSellOrderRequest(BigDecimal, BigDecimal, String)} instead.
   */
  @Deprecated
  public BTCChinaSellOrderRequest(BigDecimal price, BigDecimal amount) {

    super(METHOD_NAME, price, amount, BTCChinaExchange.DEFAULT_MARKET);
    params = "[" + price.toPlainString() + "," + BTCChinaUtils.truncateAmount(amount).toPlainString() + "]";
  }

  public BTCChinaSellOrderRequest(BigDecimal price, BigDecimal amount, String market) {

    super(METHOD_NAME, price, amount, market);
  }

  @Override
  public String toString() {

    return String.format("BTCChinaSellOrderRequest{id=%d, method=%s, params=%s}", id, method, params.toString());
  }

}
