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
package com.xeiam.xchange.cexio.dto.account;

import java.text.MessageFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: brox
 * Since: 2/7/14
 */

public class CexIOBalanceInfo {

  private final String error;
  private final long timestamp;
  private final String username;
  private final CexIOBalance balanceBTC;
  private final CexIOBalance balanceNMC;
  private final CexIOBalance balanceIXC;
  private final CexIOBalance balanceDVC;
  private final CexIOBalance balanceGHS;

  /**
   * Constructor
   * 
   * @param error
   * @param timestamp The server time (Unix time)
   * @param username
   * @param balanceBTC
   * @param balanceNMC
   * @param balanceIXC
   * @param balanceDVC
   * @param balanceGHS
   */
  public CexIOBalanceInfo(@JsonProperty("error") String error, @JsonProperty("timestamp") long timestamp, @JsonProperty("username") String username, @JsonProperty("BTC") CexIOBalance balanceBTC,
      @JsonProperty("NMC") CexIOBalance balanceNMC, @JsonProperty("IXC") CexIOBalance balanceIXC, @JsonProperty("DVC") CexIOBalance balanceDVC, @JsonProperty("GHS") CexIOBalance balanceGHS) {

    this.error = error;
    this.timestamp = timestamp;
    this.username = username;
    this.balanceBTC = balanceBTC;
    this.balanceNMC = balanceNMC;
    this.balanceIXC = balanceIXC;
    this.balanceDVC = balanceDVC;
    this.balanceGHS = balanceGHS;
  }

  public String getError() {

    return error;
  }

  public long getTimestamp() {

    return timestamp;
  }

  public String getUsername() {

    return username;
  }

  public CexIOBalance getBalanceBTC() {

    return balanceBTC;
  }

  public CexIOBalance getBalanceNMC() {

    return balanceNMC;
  }

  public CexIOBalance getBalanceIXC() {

    return balanceIXC;
  }

  public CexIOBalance getBalanceDVC() {

    return balanceDVC;
  }

  public CexIOBalance getBalanceGHS() {

    return balanceGHS;
  }

  @Override
  public String toString() {

    return MessageFormat.format("CexIOBalanceInfo[error={0}, timestamp={1}, username={2}, BTC={3}, NMC={4}, IXC={5}, DVC={6}, GHS={7}]", error, timestamp, username, balanceBTC, balanceNMC,
        balanceIXC, balanceDVC, balanceGHS);
  }

}
