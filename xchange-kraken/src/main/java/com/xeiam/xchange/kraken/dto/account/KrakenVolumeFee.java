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
package com.xeiam.xchange.kraken.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenVolumeFee {

  private final BigDecimal fee;
  private final BigDecimal minFee;
  private final BigDecimal maxFee;
  private final BigDecimal nextFee;
  private final BigDecimal nextVolume;
  private final BigDecimal tierVolume;

  public KrakenVolumeFee(@JsonProperty("fee") BigDecimal fee, @JsonProperty("minfee") BigDecimal minFee, @JsonProperty("maxfee") BigDecimal maxFee, @JsonProperty("nextfee") BigDecimal nextFee,
      @JsonProperty("nextvolume") BigDecimal nextVolume, @JsonProperty("tiervolume") BigDecimal tierVolume) {

    this.fee = fee;
    this.minFee = minFee;
    this.maxFee = maxFee;
    this.nextFee = nextFee;
    this.nextVolume = nextVolume;
    this.tierVolume = tierVolume;
  }

  public BigDecimal getFee() {

    return fee;
  }

  public BigDecimal getMinFee() {

    return minFee;
  }

  public BigDecimal getMaxFee() {

    return maxFee;
  }

  public BigDecimal getNextFee() {

    return nextFee;
  }

  public BigDecimal getNextVolume() {

    return nextVolume;
  }

  public BigDecimal getTierVolume() {

    return tierVolume;
  }

  @Override
  public String toString() {

    return "KrakenVolumeFee [fee=" + fee + ", minFee=" + minFee + ", maxFee=" + maxFee + ", nextFee=" + nextFee + ", nextVolume=" + nextVolume + ", tierVolume=" + tierVolume + "]";
  }

}
