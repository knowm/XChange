package org.knowm.xchange.gatecoin.dto.account.Results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.gatecoin.dto.GatecoinResult;
import org.knowm.xchange.gatecoin.dto.marketdata.ResponseStatus;

/** @author sumedha */
public class GatecoinWithdrawResult extends GatecoinResult {

  @JsonCreator
  public GatecoinWithdrawResult(@JsonProperty("responseStatus") ResponseStatus responseStatus) {
    super(responseStatus);
  }
}
