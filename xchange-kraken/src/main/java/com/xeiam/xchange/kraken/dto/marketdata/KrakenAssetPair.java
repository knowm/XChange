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
package com.xeiam.xchange.kraken.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenAssetPair {

  private final String altName;
  private final String classBase;
  private final String base;
  private final String classQuote;
  private final String quote;
  private final String volumeLotSize;
  private final int pairScale;
  private final int volumeLotScale;
  private final BigDecimal volumeMultiplier;
  private final List<String> leverage;
  private final List<KrakenFee> fees;
  private final String feeVolumeCurrency;
  private final BigDecimal marginCall;
  private final BigDecimal marginStop;

  public KrakenAssetPair(@JsonProperty("altname") String altName, @JsonProperty("aclass_base") String classBase, @JsonProperty("base") String base, @JsonProperty("aclass_quote") String classQuote,
      @JsonProperty("quote") String quote, @JsonProperty("lot") String volumeLotSize, @JsonProperty("pair_decimals") int pairScale, @JsonProperty("lot_decimals") int volumeLotScale,
      @JsonProperty("lot_multiplier") BigDecimal volumeMultiplier, @JsonProperty("leverage") List<String> leverage, @JsonProperty("fees") List<KrakenFee> fees,
      @JsonProperty("fee_volume_currency") String feeVolumeCurrency, @JsonProperty("margin_call") BigDecimal marginCall, @JsonProperty("margin_stop") BigDecimal marginStop) {

    this.altName = altName;
    this.classBase = classBase;
    this.base = base;
    this.classQuote = classQuote;
    this.quote = quote;
    this.volumeLotSize = volumeLotSize;
    this.pairScale = pairScale;
    this.volumeLotScale = volumeLotScale;
    this.volumeMultiplier = volumeMultiplier;
    this.leverage = leverage;
    this.fees = fees;
    this.feeVolumeCurrency = feeVolumeCurrency;
    this.marginCall = marginCall;
    this.marginStop = marginStop;
  }

  public String getAltName() {

    return altName;
  }

  public String getClassBase() {

    return classBase;
  }

  public String getBase() {

    return base;
  }

  public String getClassQuote() {

    return classQuote;
  }

  public String getQuote() {

    return quote;
  }

  public String getVolumeLotSize() {

    return volumeLotSize;
  }

  public int getPairScale() {

    return pairScale;
  }

  public int getVolumeLotScale() {

    return volumeLotScale;
  }

  public BigDecimal getVolumeMultiplier() {

    return volumeMultiplier;
  }

  public List<String> getLeverage() {

    return leverage;
  }

  public List<KrakenFee> getFees() {

    return fees;
  }

  public String getFeeVolumeCurrency() {

    return feeVolumeCurrency;
  }

  public BigDecimal getMarginCall() {

    return marginCall;
  }

  public BigDecimal getMarginStop() {

    return marginStop;
  }

  @Override
  public String toString() {

    return "KrakenAssetPairInfo [altName=" + altName + ", classBase=" + classBase + ", base=" + base + ", classQuote=" + classQuote + ", quote=" + quote + ", volumeLotSize=" + volumeLotSize
        + ", pairScale=" + pairScale + ", volumeLotScale=" + volumeLotScale + ", volumeMultiplier=" + volumeMultiplier + ", leverage=" + leverage + ", fees=" + fees + ", feeVolumeCurrency="
        + feeVolumeCurrency + ", marginCall=" + marginCall + ", marginStop=" + marginStop + "]";
  }

}
