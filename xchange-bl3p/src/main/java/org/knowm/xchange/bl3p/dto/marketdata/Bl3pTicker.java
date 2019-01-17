package org.knowm.xchange.bl3p.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class Bl3pTicker {

  @JsonProperty("ask")
  private BigDecimal ask;

  @JsonProperty("bid")
  private BigDecimal bid;

  @JsonProperty("high")
  private BigDecimal high;

  @JsonProperty("last")
  private BigDecimal last;

  @JsonProperty("low")
  private BigDecimal low;

  private Date timestamp;

  @JsonProperty("volume")
  private Bl3pTickerVolume volume;

  public Bl3pTicker(@JsonProperty("timestamp") long timestamp) {
    this.timestamp = new Date(timestamp * 1000l);
  }

  public BigDecimal getAsk() {
    return ask;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLast() {
    return last;
  }

  public BigDecimal getLow() {
    return low;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public Bl3pTickerVolume getVolume() {
    return volume;
  }

  public static class Bl3pTickerVolume {
    @JsonProperty("24h")
    private BigDecimal day;

    @JsonProperty("30d")
    private BigDecimal month;

    public BigDecimal getDay() {
      return day;
    }

    public BigDecimal getMonth() {
      return month;
    }
  }
}
