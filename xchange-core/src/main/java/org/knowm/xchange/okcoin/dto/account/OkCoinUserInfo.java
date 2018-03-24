package org.knowm.xchange.okcoin.dto.account;

import org.knowm.xchange.okcoin.dto.trade.OkCoinErrorResult;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkCoinUserInfo extends OkCoinErrorResult {

  private final OkCoinInfo info;

  public OkCoinUserInfo(@JsonProperty("result") final boolean result, @JsonProperty("error_code") final int errorCode,
      @JsonProperty("info") OkCoinInfo info) {

    super(result, errorCode);
    this.info = info;
  }

  public OkCoinInfo getInfo() {

    return info;
  }
}
