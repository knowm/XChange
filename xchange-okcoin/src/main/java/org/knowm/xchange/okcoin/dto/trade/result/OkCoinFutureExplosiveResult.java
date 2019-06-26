package org.knowm.xchange.okcoin.dto.trade.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.okcoin.dto.trade.OkCoinErrorResult;
import org.knowm.xchange.okcoin.dto.trade.OkCoinFutureExplosiveData;

public class OkCoinFutureExplosiveResult extends OkCoinErrorResult {
  private final OkCoinFutureExplosiveData[] futureExplosive;

  public OkCoinFutureExplosiveResult(
      @JsonProperty("result") final boolean result,
      @JsonProperty("error_code") final int errorCode,
      @JsonProperty("") final OkCoinFutureExplosiveData[] futureExplosive) {

    super(result, errorCode);
    this.futureExplosive = futureExplosive;
  }

  public OkCoinFutureExplosiveData[] getFutureExplosive() {

    return futureExplosive;
  }
}
