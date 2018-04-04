package org.knowm.xchange.okcoin.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkCoinPositionResult extends OkCoinErrorResult {

  private final OkCoinPosition[] positions;

  public OkCoinPositionResult(
      @JsonProperty("result") final boolean result,
      @JsonProperty("errorCode") final int errorCode,
      @JsonProperty("holding") final OkCoinPosition[] positions) {

    super(result, errorCode);
    this.positions = positions;
  }

  public OkCoinPosition[] getPositions() {

    return positions;
  }
}
