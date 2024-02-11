package org.knowm.xchange.upbit.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class UpbitCandleStickData {

  private final String market;
  private final String candleDateTimeUtc;
  private final String candleDateTimeKst;
  private final BigDecimal openingPrice;
  private final BigDecimal highPrice;
  private final BigDecimal lowPrice;
  private final BigDecimal tracePrice;
  private final Long timestamp;
  private final BigDecimal candleAccTradePrice;
  private final BigDecimal candleAccTradeVolume;
  private final String firstDayOfPeriod;

  public UpbitCandleStickData(
      @JsonProperty("market") String market,
      @JsonProperty("candle_date_time_utc") String candleDateTimeUtc,
      @JsonProperty("candle_date_time_kst") String candleDateTimeKst,
      @JsonProperty("opening_price") BigDecimal openingPrice,
      @JsonProperty("high_price") BigDecimal highPrice,
      @JsonProperty("low_price") BigDecimal lowPrice,
      @JsonProperty("trade_price") BigDecimal tracePrice,
      @JsonProperty("timestamp") Long timestamp,
      @JsonProperty("candle_acc_trade_price") BigDecimal candleAccTradePrice,
      @JsonProperty("candle_acc_trade_volume") BigDecimal candleAccTradeVolume,
      @JsonProperty("first_day_of_period") String firstDayOfPeriod) {
    this.market = market;
    this.candleDateTimeUtc = candleDateTimeUtc;
    this.candleDateTimeKst = candleDateTimeKst;
    this.openingPrice = openingPrice;
    this.highPrice = highPrice;
    this.lowPrice = lowPrice;
    this.tracePrice = tracePrice;
    this.timestamp = timestamp;
    this.candleAccTradePrice = candleAccTradePrice;
    this.candleAccTradeVolume = candleAccTradeVolume;
    this.firstDayOfPeriod = firstDayOfPeriod;
  }

  public String getMarket() {
    return market;
  }

  public String getCandleDateTimeUtc() {
    return candleDateTimeUtc;
  }

  public String getCandleDateTimeKst() {
    return candleDateTimeKst;
  }

  public BigDecimal getOpeningPrice() {
    return openingPrice;
  }

  public BigDecimal getHighPrice() {
    return highPrice;
  }

  public BigDecimal getLowPrice() {
    return lowPrice;
  }

  public BigDecimal getTracePrice() {
    return tracePrice;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public BigDecimal getCandleAccTradePrice() {
    return candleAccTradePrice;
  }

  public BigDecimal getCandleAccTradeVolume() {
    return candleAccTradeVolume;
  }

  public String getFirstDayOfPeriod() {
    return firstDayOfPeriod;
  }

  @Override
  public String toString() {
    return "UpbitCandleStickData{"
        + "market='"
        + market
        + '\''
        + ", candleDateTimeUtc='"
        + candleDateTimeUtc
        + '\''
        + ", candleDateTimeKst='"
        + candleDateTimeKst
        + '\''
        + ", openingPrice="
        + openingPrice
        + ", highPrice="
        + highPrice
        + ", lowPrice="
        + lowPrice
        + ", tracePrice="
        + tracePrice
        + ", timestamp="
        + timestamp
        + ", candleAccTradePrice="
        + candleAccTradePrice
        + ", candleAccTradeVolume="
        + candleAccTradeVolume
        + ", firstDayOfPeriod='"
        + firstDayOfPeriod
        + '\''
        + '}';
  }
}
