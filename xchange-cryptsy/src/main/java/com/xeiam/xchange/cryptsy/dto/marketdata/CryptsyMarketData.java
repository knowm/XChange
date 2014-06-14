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
package com.xeiam.xchange.cryptsy.dto.marketdata;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptsy.CryptsyUtils;

/**
 * @author ObsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptsyMarketData {

  private final int marketId;
  private final String label;
  private final String priCurrCode;
  private final String priCurrName;
  private final String secCurrCode;
  private final String secCurrName;
  private final BigDecimal volume24h;
  private final BigDecimal volumeBTC;
  private final BigDecimal volumeUSD;
  private final BigDecimal last;
  private final BigDecimal high;
  private final BigDecimal low;
  private final Date created;

  /**
   * Constructor
   * 
   * @throws ParseException
   */
  @JsonCreator
  public CryptsyMarketData(@JsonProperty("marketid") int marketId, @JsonProperty("label") String label, @JsonProperty("primary_currency_code") String priCurrCode,
      @JsonProperty("primary_currency_name") String priCurrName, @JsonProperty("secondary_currency_code") String secCurrCode, @JsonProperty("secondary_currency_name") String secCurrName,
      @JsonProperty("current_volume") BigDecimal volume24h, @JsonProperty("current_volume_btc") BigDecimal volumeBTC, @JsonProperty("current_volume_usd") BigDecimal volumeUSD,
      @JsonProperty("last_trade") BigDecimal last, @JsonProperty("high_trade") BigDecimal high, @JsonProperty("low_trade") BigDecimal low, @JsonProperty("created") String created)
      throws ParseException {

    this.marketId = marketId;
    this.label = label;
    this.priCurrCode = priCurrCode;
    this.priCurrName = priCurrName;
    this.secCurrCode = secCurrCode;
    this.secCurrName = secCurrName;
    this.volume24h = volume24h;
    this.volumeBTC = volumeBTC;
    this.volumeUSD = volumeUSD;
    this.last = last;
    this.high = high;
    this.low = low;
    this.created = created == null ? null : CryptsyUtils.convertDateTime(created);
  }

  public int getMarketId() {

    return marketId;
  }

  public String getLabel() {

    return label;
  }

  public String getPrimaryCurrencyCode() {

    return priCurrCode;
  }

  public String getPrimaryCurrencyName() {

    return priCurrName;
  }

  public String getSecondaryCurrencyCode() {

    return secCurrCode;
  }

  public String getSecondaryCurrencyName() {

    return secCurrName;
  }

  public BigDecimal get24hVolume() {

    return volume24h;
  }

  public BigDecimal get24hBTCVolume() {

    return volumeBTC;
  }

  public BigDecimal get24hUSDVolume() {

    return volumeUSD;
  }

  public BigDecimal getLast() {

    return last;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getLow() {

    return low;
  }

  public Date getCreatedTime() {

    return created;
  }

  @Override
  public String toString() {

    return "CryptsyMarketData [Market ID='" + marketId + "',Label='" + label + "',Primary Currency Code='" + priCurrCode + "',Primary Currency Name='" + priCurrName + "',Secondary Currency Code='"
        + secCurrCode + "',Secondary Currency Name='" + secCurrName + "',24h Volume='" + volume24h + "',Last='" + last + "',High='" + high + "',Low='" + low + "',Created='" + created + "]";
  }

}
