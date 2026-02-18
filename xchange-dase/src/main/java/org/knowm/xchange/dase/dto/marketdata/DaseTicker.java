package org.knowm.xchange.dase.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class DaseTicker {

  private final long time;
  private final BigDecimal ask;
  private final BigDecimal bid;
  private final BigDecimal volume;
  private final BigDecimal price;
  private final BigDecimal size;

  @JsonCreator
  public DaseTicker(
      @JsonProperty("time") Number time,
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("size") BigDecimal size) {
    this.time = time == null ? 0L : time.longValue();
    this.ask = ask;
    this.bid = bid;
    this.volume = volume;
    this.price = price;
    this.size = size;
  }

  public long getTime() {
    return time;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getSize() {
    return size;
  }
}
