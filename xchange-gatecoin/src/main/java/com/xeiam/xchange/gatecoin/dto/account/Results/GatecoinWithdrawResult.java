package com.xeiam.xchange.gatecoin.dto.account.Results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.gatecoin.dto.GatecoinResult;
import com.xeiam.xchange.gatecoin.dto.marketdata.ResponseStatus;

/**
 * @author sumedha
 */
public class GatecoinWithdrawResult extends GatecoinResult {

  @JsonCreator
  public GatecoinWithdrawResult(@JsonProperty("responseStatus") ResponseStatus responseStatus) {
    super(responseStatus);
  }
}
