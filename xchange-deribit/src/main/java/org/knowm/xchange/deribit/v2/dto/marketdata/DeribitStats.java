package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitStats {

  @JsonProperty("volume")
  private BigDecimal volume;

  @JsonProperty("low")
  private BigDecimal low;

  @JsonProperty("high")
  private BigDecimal high;

  public BigDecimal getVolume() {
    return volume;
  }

  public void setVolume(BigDecimal volume) {
    this.volume = volume;
  }

  public BigDecimal getLow() {
    return low;
  }

  public void setLow(BigDecimal low) {
    this.low = low;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public void setHigh(BigDecimal high) {
    this.high = high;
  }

  @Override
  public String toString() {
    return "DeribitStats{" +
            "volume=" + volume +
            ", low=" + low +
            ", high=" + high +
            '}';
  }
}
