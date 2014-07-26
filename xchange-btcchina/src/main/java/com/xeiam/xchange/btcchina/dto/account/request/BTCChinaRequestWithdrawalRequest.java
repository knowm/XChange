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
package com.xeiam.xchange.btcchina.dto.account.request;

import java.math.BigDecimal;

import com.xeiam.xchange.btcchina.dto.BTCChinaRequest;
import com.xeiam.xchange.currency.Currencies;

/**
 * @author David Yam
 */
public final class BTCChinaRequestWithdrawalRequest extends BTCChinaRequest {

  private static final String METHOD_NAME = "requestWithdrawal";

  /**
   * Constructor
   * 
   * @param currencyUnit
   * @param amount
   * @deprecated user {@link #BTCChinaRequestWithdrawalRequest(String, BigDecimal)} instead.
   */
  @Deprecated
  public BTCChinaRequestWithdrawalRequest(BigDecimal amount) {

    method = METHOD_NAME;
    params = "[\"" + Currencies.BTC + "\"," + amount.doubleValue() + "]";
  }

  /**
   * @param currency [ BTC | LTC ].
   * @param amount amount to withdraw.
   */
  public BTCChinaRequestWithdrawalRequest(String currency, BigDecimal amount) {

    method = METHOD_NAME;
    params = String.format("[\"%1$s\",%2$s]", currency, amount.toPlainString());
  }

  @Override
  public String toString() {

    return String.format("BTCChinaRequestWithdrawalRequest{id=%d, method=%s, params=%s}", id, method, params.toString());
  }

}
