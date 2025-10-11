package org.knowm.xchange.dase.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public class DaseCandlesResponse {

  private final List<List<BigDecimal>> candles;

  @JsonCreator
  public DaseCandlesResponse(@JsonProperty("candles") List<List<BigDecimal>> candles) {
    this.candles = candles;
  }

  public List<List<BigDecimal>> getCandles() {
    return candles;
  }
}
