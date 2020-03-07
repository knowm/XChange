package org.knowm.xchange.bittrex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BittrexAccountVolume {

  private String updated;
  private BigDecimal volume30days;

  public BittrexAccountVolume(
      @JsonProperty("updated") String updated,
      @JsonProperty("volume30days") BigDecimal volume30days) {

    super();
    this.updated = updated;
    this.volume30days = volume30days;
  }

  public String getUpdated() {
    return updated;
  }

  public void setUpdated(String updated) {
    this.updated = updated;
  }

  public BigDecimal getVolume30days() {
    return volume30days;
  }

  public void setVolume30days(BigDecimal volume30days) {
    this.volume30days = volume30days;
  }

  @Override
  public String toString() {

    return "BittrexAccountVolume [updated=" + updated + ", volume30days=" + volume30days + "]";
  }
}
