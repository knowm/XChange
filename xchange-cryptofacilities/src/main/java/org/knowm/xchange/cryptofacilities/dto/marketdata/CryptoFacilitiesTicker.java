package org.knowm.xchange.cryptofacilities.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import org.knowm.xchange.cryptofacilities.Util;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/** @author Jean-Christophe Laruelle */
public class CryptoFacilitiesTicker extends CryptoFacilitiesResult {

  private final BigDecimal bid;
  private final BigDecimal ask;
  private final BigDecimal last;
  private final BigDecimal askSize;
  private final String symbol;
  private final Date lastTime;
  private final BigDecimal low24H;
  private final BigDecimal bidSize;
  private final boolean suspended;
  private final BigDecimal open24H;
  private final BigDecimal high24H;
  private final BigDecimal markPrice;
  private final BigDecimal lastSize;
  private final BigDecimal vol24H;
  private final String tag;
  private final String pair;
  private final BigDecimal fundingRate;
  private final BigDecimal fundingRatePrediction;

  public CryptoFacilitiesTicker(
      @JsonProperty("result") String result,
      @JsonProperty("error") String error,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("askSize") BigDecimal askSize,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("lastTime") String strLastTime,
      @JsonProperty("low24h") BigDecimal low24H,
      @JsonProperty("bidSize") BigDecimal bidSize,
      @JsonProperty("suspended") boolean suspended,
      @JsonProperty("open24h") BigDecimal open24H,
      @JsonProperty("high24h") BigDecimal high24H,
      @JsonProperty("markPrice") BigDecimal markPrice,
      @JsonProperty("lastSize") BigDecimal lastSize,
      @JsonProperty("vol24h") BigDecimal vol24H,
      @JsonProperty("tag") String tag,
      @JsonProperty("pair") String pair,
      @JsonProperty("fundingRate") BigDecimal fundingRate,
      @JsonProperty("fundingRatePrediction") BigDecimal fundingRatePrediction)
      throws ParseException {

    super(result, error);

    this.bid = bid;
    this.ask = ask;
    this.last = last;
    this.askSize = askSize;
    this.symbol = symbol;
    this.lastTime = Util.parseDate(strLastTime);
    this.low24H = low24H;
    this.bidSize = bidSize;
    this.suspended = suspended;
    this.open24H = open24H;
    this.high24H = high24H;
    this.markPrice = markPrice;
    this.lastSize = lastSize;
    this.vol24H = vol24H;
    this.tag = tag;
    this.pair = pair;
    this.fundingRate = fundingRate;
    this.fundingRatePrediction = fundingRatePrediction;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  public BigDecimal getLast() {
    return last;
  }

  public BigDecimal getAskSize() {
    return askSize;
  }

  public String getSymbol() {
    return symbol;
  }

  public Date getLastTime() {
    return lastTime;
  }

  public BigDecimal getLow24H() {
    return low24H;
  }

  public BigDecimal getBidSize() {
    return bidSize;
  }

  public boolean getSuspended() {
    return suspended;
  }

  public BigDecimal getOpen24H() {
    return open24H;
  }

  public BigDecimal getHigh24H() {
    return high24H;
  }

  public BigDecimal getMarkPrice() {
    return markPrice;
  }

  public BigDecimal getLastSize() {
    return lastSize;
  }

  public BigDecimal getVol24H() {
    return vol24H;
  }

  public String getTag() {
    return tag;
  }

  public String getPair() {
    return pair;
  }

  public BigDecimal getFundingRate() {
    return fundingRate;
  }

  public BigDecimal getFundingRatePrediction() {
    return fundingRatePrediction;
  }

  @Override
  public String toString() {

    return "CryptoFacilitiesTicker ["
        + "symbol="
        + symbol
        + ", bid="
        + bid
        + ", bidSize="
        + bidSize
        + ", ask="
        + ask
        + ", askSize="
        + askSize
        + ", last="
        + last
        + ", lastSize="
        + lastSize
        + ", lastTime="
        + lastTime
        + ", open24H="
        + open24H
        + ", low24H="
        + low24H
        + ", high24H="
        + high24H
        + ", vol24H="
        + vol24H
        + ", markPrice="
        + markPrice
        + ", suspended="
        + suspended
        + ", tag="
        + tag
        + ", pair="
        + pair
        + ", fundingRate="
        + fundingRate
        + ", fundingRatePrediction="
        + fundingRatePrediction
        + "]";
  }
}
