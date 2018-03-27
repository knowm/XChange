package org.knowm.xchange.okcoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.okcoin.dto.trade.OkCoinErrorResult;

public class OkCoinFuturesUserInfoCross extends OkCoinErrorResult {

  private final OkCoinFuturesInfoCross info;

  public OkCoinFuturesUserInfoCross(
      @JsonProperty("result") final boolean result,
      @JsonProperty("error_code") final int errorCode,
      @JsonProperty("info") OkCoinFuturesInfoCross info) {

    super(result, errorCode);
    this.info = info;
  }

  public OkCoinFuturesInfoCross getInfo() {

    return info;
  }
}
