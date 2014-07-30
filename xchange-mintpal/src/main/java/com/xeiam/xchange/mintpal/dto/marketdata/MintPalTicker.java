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

  public MintPalTicker(@JsonProperty("market_id") int marketId, @JsonProperty("coin") String coin, @JsonProperty("code") String code, @JsonProperty("exchange") String exchange,
      @JsonProperty("last_price") BigDecimal lastPrice, @JsonProperty("yesterday_price") BigDecimal yesterdayPrice, @JsonProperty("change") BigDecimal change,
      @JsonProperty("24hhigh") BigDecimal high24Hour, @JsonProperty("24hlow") BigDecimal low24Hour, @JsonProperty("24hvol") BigDecimal volume24Hour, @JsonProperty("top_bid") BigDecimal topBid,
      @JsonProperty("top_ask") BigDecimal topAsk) {

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
