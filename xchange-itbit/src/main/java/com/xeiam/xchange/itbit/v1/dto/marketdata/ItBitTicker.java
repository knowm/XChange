package com.xeiam.xchange.itbit.v1.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.currency.CurrencyPair;

public class ItBitTicker {

  private final CurrencyPair currencyPair;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final double bidAmt;
  private final double askAmt;
  private final BigDecimal lastPrice;
  private final double lastAmt;
  private final BigDecimal volume24h;
  private final BigDecimal highToday;
  private final BigDecimal lowToday;
  private final double openToday;
  private final double vwapToday;
  private final double vwap24h;
  private final String timestamp;

  public ItBitTicker(@JsonProperty("pair") CurrencyPair currencyPair, @JsonProperty("bid") BigDecimal bid, @JsonProperty("ask") BigDecimal ask, @JsonProperty("bidAmt") double bidAmt,
      @JsonProperty("askAmt") double askAmt, @JsonProperty("lastPrice") BigDecimal lastPrice, @JsonProperty("lastAmt") double lastAmt, @JsonProperty("volume24h") BigDecimal volume24h,
      @JsonProperty("highToday") BigDecimal highToday, @JsonProperty("lowToday") BigDecimal lowToday, @JsonProperty("openToday") double openToday, @JsonProperty("vwapToday") double vwapToday,
      @JsonProperty("vwap24h") double vwap24h, @JsonProperty("servertimeUTC") String timestamp) {

    super();
    this.currencyPair = currencyPair;
    this.bid = bid;
    this.ask = ask;
    this.bidAmt = bidAmt;
    this.askAmt = askAmt;
    this.lastPrice = lastPrice;
    this.lastAmt = lastAmt;
    this.volume24h = volume24h;
    this.highToday = highToday;
    this.lowToday = lowToday;
    this.openToday = openToday;
    this.vwapToday = vwapToday;
    this.vwap24h = vwap24h;
    this.timestamp = timestamp;
  }

  public CurrencyPair getCurrencyPair() {

    return currencyPair;
  }

  public BigDecimal getBid() {

    return bid;
  }

  public BigDecimal getAsk() {

    return ask;
  }

  public double getBidAmt() {

    return bidAmt;
  }

  public double getAskAmt() {

    return askAmt;
  }

  public BigDecimal getLastPrice() {

    return lastPrice;
  }

  public double getLastAmt() {

    return lastAmt;
  }

  public BigDecimal getVolume24h() {

    return volume24h;
  }

  public BigDecimal getHighToday() {

    return highToday;
  }

  public BigDecimal getLowToday() {

    return lowToday;
  }

  public double getOpenToday() {

    return openToday;
  }

  public double getVwapToday() {

    return vwapToday;
  }

  public double getVwap24h() {

    return vwap24h;
  }

  public String getTimestamp() {

    return timestamp;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("ItBitTicker [currencyPair=");
    builder.append(currencyPair);
    builder.append(", bid=");
    builder.append(bid);
    builder.append(", ask=");
    builder.append(ask);
    builder.append(", volume24h=");
    builder.append(volume24h);
    builder.append(", highToday=");
    builder.append(highToday);
    builder.append(", lowToday=");
    builder.append(lowToday);
    builder.append("]");
    return builder.toString();
  }
}
