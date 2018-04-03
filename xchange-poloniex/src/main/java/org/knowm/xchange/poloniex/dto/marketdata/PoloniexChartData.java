package org.knowm.xchange.poloniex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.poloniex.PoloniexUtils;

public class PoloniexChartData {
  private Date date;
  private BigDecimal high;
  private BigDecimal low;
  private BigDecimal open;
  private BigDecimal close;
  private BigDecimal volume;
  private BigDecimal quoteVolume;
  private BigDecimal weightedAverage;

  @JsonCreator
  public PoloniexChartData(
      @JsonProperty(value = "date", required = true)
          @JsonDeserialize(using = PoloniexUtils.UnixTimestampDeserializer.class)
          Date date,
      @JsonProperty(value = "high", required = true) BigDecimal high,
      @JsonProperty(value = "low", required = true) BigDecimal low,
      @JsonProperty(value = "open", required = true) BigDecimal open,
      @JsonProperty(value = "close", required = true) BigDecimal close,
      @JsonProperty(value = "volume", required = true) BigDecimal volume,
      @JsonProperty(value = "quoteVolume", required = true) BigDecimal quoteVolume,
      @JsonProperty(value = "weightedAverage", required = true) BigDecimal weightedAverage) {
    this.date = date;
    this.high = high;
    this.low = low;
    this.open = open;
    this.close = close;
    this.volume = volume;
    this.quoteVolume = quoteVolume;
    this.weightedAverage = weightedAverage;
  }

  public Date getDate() {
    return date;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal getClose() {
    return close;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getQuoteVolume() {
    return quoteVolume;
  }

  public BigDecimal getWeightedAverage() {
    return weightedAverage;
  }

  @Override
  public String toString() {
    return "PoloniexChartData ["
        + "date="
        + date
        + ", high="
        + high
        + ", low="
        + low
        + ", open="
        + open
        + ", close="
        + close
        + ", volume="
        + volume
        + ", quoteVolume="
        + quoteVolume
        + ", weightedAverage="
        + weightedAverage
        + ']';
  }
}
