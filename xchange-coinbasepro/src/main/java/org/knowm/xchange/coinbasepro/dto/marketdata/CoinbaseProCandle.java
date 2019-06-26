package org.knowm.xchange.coinbasepro.dto.marketdata;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.Date;

@JsonDeserialize(using = CoinbaseProCandleDeserializer.class)
public class CoinbaseProCandle {

  private final Date time;
  private final BigDecimal open, high, low, close, volume;

  public CoinbaseProCandle(
      Date time,
      BigDecimal open,
      BigDecimal high,
      BigDecimal low,
      BigDecimal close,
      BigDecimal volume) {
    this.time = time;
    this.open = open;
    this.high = high;
    this.low = low;
    this.close = close;
    this.volume = volume;
  }

  public Date getTime() {
    return time;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getClose() {
    return close;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  @Override
  public String toString() {
    return "CoinbaseProCandle [time="
        + time
        + ", open="
        + open
        + ", high="
        + high
        + ", low="
        + low
        + ", close="
        + close
        + ", volume="
        + volume
        + "]";
  }
}
