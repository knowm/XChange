package org.knowm.xchange.okcoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.okcoin.dto.trade.OkCoinErrorResult;

public class OkCoinFuturesUserInfoFixed extends OkCoinErrorResult {
  private final OkCoinFuturesInfoFixed info;

  public OkCoinFuturesUserInfoFixed(
      @JsonProperty("result") final boolean result,
      @JsonProperty("error_code") final int errorCode,
      @JsonProperty("info") OkCoinFuturesInfoFixed info) {

    super(result, errorCode);
    this.info = info;
  }

  public OkCoinFuturesInfoFixed getInfo() {

    return info;
  }
}
