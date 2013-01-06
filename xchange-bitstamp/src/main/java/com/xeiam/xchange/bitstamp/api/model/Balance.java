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
package com.xeiam.xchange.bitstamp.api.model;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Matija Mazi <br/>
 * @created 4/20/12 7:30 PM
 */
public class Balance {

  @JsonProperty("usd_balance")
  private BigDecimal usdBalance;
  @JsonProperty("btc_balance")
  private BigDecimal btcBalance;
  @JsonProperty("usd_reserved")
  private BigDecimal usdReserved;
  @JsonProperty("btc_reserved")
  private BigDecimal btcReserved;
  @JsonProperty("usd_available")
  private BigDecimal usdAvailable;
  @JsonProperty("btc_available")
  private BigDecimal btcAvailable;

  private BigDecimal fee;

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
