/**
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.bitcoincentral.dto.trade;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Matija Mazi
 */
public class BitcoinCentralTrade extends BitcoinCentralTradeData {

  private final BigDecimal tradedBtc;
  private final BigDecimal tradedCurrency;
  private Date createdTime;

  public BitcoinCentralTrade(@JsonProperty("ppc") BigDecimal ppc, @JsonProperty("category") Category category, @JsonProperty("currency") String currency, @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("created_at") String createdAt, @JsonProperty("id") int id, @JsonProperty("traded_btc") BigDecimal tradedBtc, @JsonProperty("traded_currency") BigDecimal tradedCurrency)
      throws ParseException {

    super(ppc, category, currency, amount, id, createdAt);
    this.tradedBtc = tradedBtc;
    this.tradedCurrency = tradedCurrency;
  }

  public BigDecimal getTradedBtc() {

    return tradedBtc;
  }

  public BigDecimal getTradedCurrency() {

    return tradedCurrency;
  }
}
