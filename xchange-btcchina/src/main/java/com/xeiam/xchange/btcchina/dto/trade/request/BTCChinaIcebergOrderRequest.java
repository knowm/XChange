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
import com.xeiam.xchange.btcchina.dto.BTCChinaRequest;

public class BTCChinaIcebergOrderRequest extends BTCChinaRequest {

  /**
   * Construct a buy/sell iceberg order request.
   *
   * @param price The price in quote currency to buy 1 base currency.
   *          Max 2 decimals for BTC/CNY and LTC/CNY markets.
   *          4 decimals for LTC/BTC market.
   *          Market iceberg order is executed by setting price to 'null'.
   * @param amount The total amount of LTC/BTC to buy.
   *          Supports 4 decimal places for BTC and 3 decimal places for LTC.
   * @param disclosedAmount The disclosed amount of LTC/BTC to buy.
   *          Supports 4 decimal places for BTC and 3 decimal places for LTC.
   * @param variance Default to 0. Must be less than 1.
   *          When given, used as variance to the disclosed amount
   *          when the order is created.
   * @param market Default to “BTCCNY”. [ BTCCNY | LTCCNY | LTCBTC ]
   */
  public BTCChinaIcebergOrderRequest(String method, BigDecimal price, BigDecimal amount, BigDecimal disclosedAmount, BigDecimal variance, String market) {

    this.method = method;
    this.params = String.format("[%1$s,%2$s,%3$s,%4$s,\"%5$s\"]", price == null ? "null" : price.stripTrailingZeros().toPlainString(), amount.stripTrailingZeros().toPlainString(),
        disclosedAmount.stripTrailingZeros().toPlainString(), variance.stripTrailingZeros().toPlainString(), market == null ? BTCChinaExchange.DEFAULT_MARKET : market);
  }

}
