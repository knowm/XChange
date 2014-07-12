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
package com.xeiam.xchange.mintpal.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author jamespedwards42
 */
public class MintPalTicker {

  private final int marketId;
  private final String coin;
  private final String code;
  private final String exchange;
  private final BigDecimal lastPrice;
  private final BigDecimal yesterdayPrice;
  private final BigDecimal change;
  private final BigDecimal high24Hour;
  private final BigDecimal low24Hour;
  private final BigDecimal volume24Hour;
  private final BigDecimal topBid;
  private final BigDecimal topAsk;

  public MintPalTicker(@JsonProperty("market_id") int marketId, @JsonProperty("coin") String coin, @JsonProperty("code") String code, @JsonProperty("exchange") String exchange, @JsonProperty("last_price") BigDecimal lastPrice,
      @JsonProperty("yesterday_price") BigDecimal yesterdayPrice, @JsonProperty("change") BigDecimal change, @JsonProperty("24hhigh") BigDecimal high24Hour,
      @JsonProperty("24hlow") BigDecimal low24Hour, @JsonProperty("24hvol") BigDecimal volume24Hour, @JsonProperty("top_bid") BigDecimal topBid, @JsonProperty("top_ask") BigDecimal topAsk) {

    this.marketId = marketId;
    this.coin = coin;
    this.code = code;
    this.exchange = exchange;
    this.lastPrice = lastPrice;
    this.yesterdayPrice = yesterdayPrice;
    this.change = change;
    this.high24Hour = high24Hour;
    this.low24Hour = low24Hour;
    this.volume24Hour = volume24Hour;
    this.topBid = topBid;
    this.topAsk = topAsk;
  }

  public int getMarketId() {

    return marketId;
  }

  public String getCoin() {

    return coin;
  }

  public String getCode() {

    return code;
  }

  public String getExchange() {

    return exchange;
  }

  public BigDecimal getLastPrice() {

    return lastPrice;
  }

  public BigDecimal getYesterdayPrice() {

    return yesterdayPrice;
  }

  public BigDecimal getChange() {

    return change;
  }

  public BigDecimal getHigh24Hour() {

    return high24Hour;
  }

  public BigDecimal getLow24Hour() {

    return low24Hour;
  }

  public BigDecimal getVolume24Hour() {

    return volume24Hour;
  }

  public BigDecimal getTopBid() {

    return topBid;
  }

  public BigDecimal getTopAsk() {

    return topAsk;
  }

  @Override
  public String toString() {

    return "MintPalTicker [marketId=" + marketId + ", coin=" + coin + ", code=" + code + ", exchange=" + exchange + ", lastPrice=" + lastPrice + ", yesterdayPrice=" + yesterdayPrice + ", change="
        + change + ", high24Hour=" + high24Hour + ", low24Hour=" + low24Hour + ", volume24Hour=" + volume24Hour + ", topBid=" + topBid + ", topAsk=" + topAsk + "]";
  }

}
