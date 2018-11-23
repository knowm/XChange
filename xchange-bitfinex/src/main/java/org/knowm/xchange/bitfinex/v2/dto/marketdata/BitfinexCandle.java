package org.knowm.xchange.bitfinex.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class BitfinexCandle {

  private long timestamp;
  private BigDecimal open;
  private BigDecimal close;
  private BigDecimal high;
  private BigDecimal low;
  private BigDecimal volume;

  public BitfinexCandle() {}

  public BitfinexCandle(
      long timestamp,
      BigDecimal open,
      BigDecimal close,
      BigDecimal high,
      BigDecimal low,
      BigDecimal volume) {

    this.timestamp = timestamp;
    this.open = open;
    this.close = close;
    this.high = high;
    this.low = low;
    this.volume = volume;
  }

  public long getTimestamp() {

    return timestamp;
  }

  public BigDecimal getOpen() {

    return open;
  }

  public BigDecimal getClose() {

    return close;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("BitfinexCandle [timestamp=");
    builder.append(timestamp);
    builder.append(", open=");
    builder.append(open);
    builder.append(", close=");
    builder.append(close);
    builder.append(", high=");
    builder.append(high);
    builder.append(", low=");
    builder.append(low);
    builder.append(", volume=");
    builder.append(volume);
    builder.append("]");
    return builder.toString();
  }
}
