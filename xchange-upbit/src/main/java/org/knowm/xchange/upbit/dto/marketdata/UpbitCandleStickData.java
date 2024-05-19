package org.knowm.xchange.upbit.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
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
}
