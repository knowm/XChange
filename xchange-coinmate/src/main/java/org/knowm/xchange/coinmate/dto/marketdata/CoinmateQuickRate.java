package org.knowm.xchange.coinmate.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinmate.dto.CoinmateBaseResponse;

import java.math.BigDecimal;

public class CoinmateQuickRate extends CoinmateBaseResponse<BigDecimal> {
  public CoinmateQuickRate(
      @JsonProperty("error") boolean error,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("data") BigDecimal data) {
    super(error, errorMessage, data);
  }
}
