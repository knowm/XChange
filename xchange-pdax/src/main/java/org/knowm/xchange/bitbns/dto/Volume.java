package org.knowm.xchange.bitbns.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class Volume {
  private BigDecimal max;
  private BigDecimal min;
  private BigDecimal volume;

  public Volume(
      @JsonProperty("max") BigDecimal max,
      @JsonProperty("min") BigDecimal min,
      @JsonProperty("volume") BigDecimal volume) {
    this.max = max;
    this.min = min;
    this.volume = volume;
  }

  public BigDecimal getMax() {
    return max;
  }

  public void setMax(BigDecimal max) {
    this.max = max;
  }

  public BigDecimal getMin() {
    return min;
  }

  public void setMin(BigDecimal min) {
    this.min = min;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public void setVolume(BigDecimal volume) {
    this.volume = volume;
  }

  @Override
  public String toString() {
    return "Volume [max=" + max + ", min=" + min + ", volume=" + volume + "]";
  }
}
