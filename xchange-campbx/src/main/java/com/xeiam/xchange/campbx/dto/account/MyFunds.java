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
package com.xeiam.xchange.campbx.dto.account;

/**
 * @author Matija Mazi 
 */
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.campbx.dto.CampBXResponse;

public final class MyFunds extends CampBXResponse {

  @JsonProperty("Total USD")
  private BigDecimal totalUSD;
  @JsonProperty("Total BTC")
  private BigDecimal totalBTC;
  @JsonProperty("Liquid USD")
  private BigDecimal liquidUSD;
  @JsonProperty("Liquid BTC")
  private BigDecimal liquidBTC;
  @JsonProperty("Margin Account USD")
  private BigDecimal marginAccountUSD;
  @JsonProperty("Margin Account BTC")
  private BigDecimal marginAccountBTC;

  public BigDecimal getTotalUSD() {

    return totalUSD;
  }

  public void setTotalUSD(BigDecimal totalUSD) {

    this.totalUSD = totalUSD;
  }

  public BigDecimal getTotalBTC() {

    return totalBTC;
  }

  public void setTotalBTC(BigDecimal totalBTC) {

    this.totalBTC = totalBTC;
  }

  public BigDecimal getLiquidUSD() {

    return liquidUSD;
  }

  public void setLiquidUSD(BigDecimal liquidUSD) {

    this.liquidUSD = liquidUSD;
  }

  public BigDecimal getLiquidBTC() {

    return liquidBTC;
  }

  public void setLiquidBTC(BigDecimal liquidBTC) {

    this.liquidBTC = liquidBTC;
  }

  public BigDecimal getMarginAccountUSD() {

    return marginAccountUSD;
  }

  public void setMarginAccountUSD(BigDecimal marginAccountUSD) {

    this.marginAccountUSD = marginAccountUSD;
  }

  public BigDecimal getMarginAccountBTC() {

    return marginAccountBTC;
  }

  public void setMarginAccountBTC(BigDecimal marginAccountBTC) {

    this.marginAccountBTC = marginAccountBTC;
  }

  @Override
  public String toString() {

    return "MyFunds [totalUSD=" + totalUSD + ", totalBTC=" + totalBTC + ", liquidUSD=" + liquidUSD + ", liquidBTC=" + liquidBTC + ", marginAccountUSD=" + marginAccountUSD + ", marginAccountBTC="
        + marginAccountBTC + ", getSuccess()=" + getSuccess() + ", getInfo()=" + getInfo() + ", getError()=" + getError() + "]";
  }

}