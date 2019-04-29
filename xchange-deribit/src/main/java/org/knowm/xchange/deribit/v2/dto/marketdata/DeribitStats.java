package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitStats {

  @JsonProperty("volume")
  public BigDecimal volume;

  @JsonProperty("low")
  public BigDecimal low;

  @JsonProperty("high")
  public BigDecimal high;

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getHigh() {
    return high;
  }
}
