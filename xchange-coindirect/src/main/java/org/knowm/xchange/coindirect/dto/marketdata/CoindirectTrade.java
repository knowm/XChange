package org.knowm.xchange.coindirect.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoindirectTrade {
  public final long time;
  public final BigDecimal price;
  public final BigDecimal volume;

  public CoindirectTrade(
      @JsonProperty("time") long time,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("volume") BigDecimal volume) {
    this.time = time;
    this.price = price;
    this.volume = volume;
  }

  @Override
  public String toString() {
    return "CoindirectTrade{" + "time=" + time + ", price=" + price + ", volume=" + volume + '}';
  }
}
