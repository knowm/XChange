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
package com.xeiam.xchange.bitstamp.dto.account;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Matija Mazi <br/>
 * @immutable
 */
public class BitstampBalance {

  private final BigDecimal usdBalance;
  private final BigDecimal btcBalance;
  private final BigDecimal usdReserved;
  private final BigDecimal btcReserved;
  private final BigDecimal usdAvailable;
  private final BigDecimal btcAvailable;
  private final BigDecimal fee;

  public BitstampBalance(@JsonProperty("usd_balance") BigDecimal usdBalance, @JsonProperty("btc_balance") BigDecimal btcBalance, @JsonProperty("usd_reserved") BigDecimal usdReserved,
      @JsonProperty("btc_reserved") BigDecimal btcReserved, @JsonProperty("usd_available") BigDecimal usdAvailable, @JsonProperty("btc_available") BigDecimal btcAvailable,
      @JsonProperty("fee") BigDecimal fee) {

    this.usdBalance = usdBalance;
    this.btcBalance = btcBalance;
    this.usdReserved = usdReserved;
    this.btcReserved = btcReserved;
    this.usdAvailable = usdAvailable;
    this.btcAvailable = btcAvailable;
    this.fee = fee;
  }

  public BigDecimal getUsdBalance() {

    return usdBalance;
  }

  public BigDecimal getBtcBalance() {

    return btcBalance;
  }

  public BigDecimal getUsdReserved() {

    return usdReserved;
  }

  public BigDecimal getBtcReserved() {

    return btcReserved;
  }

  public BigDecimal getUsdAvailable() {

    return usdAvailable;
  }

  public BigDecimal getBtcAvailable() {

    return btcAvailable;
  }

  public BigDecimal getFee() {

    return fee;
  }

  @Override
  public String toString() {

    return String.format("Balance{usdBalance=%s, btcBalance=%s, usdReserved=%s, btcReserved=%s, usdAvailable=%s, btcAvailable=%s, fee=%s}", usdBalance, btcBalance, usdReserved, btcReserved,
        usdAvailable, btcAvailable, fee);
  }
}
