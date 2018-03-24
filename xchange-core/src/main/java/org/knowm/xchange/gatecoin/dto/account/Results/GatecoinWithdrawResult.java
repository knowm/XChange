package org.knowm.xchange.gatecoin.dto.account.Results;

import org.knowm.xchange.gatecoin.dto.GatecoinResult;
import org.knowm.xchange.gatecoin.dto.marketdata.ResponseStatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author sumedha
 */
public class GatecoinWithdrawResult extends GatecoinResult {

  @JsonCreator
  public GatecoinWithdrawResult(@JsonProperty("responseStatus") ResponseStatus responseStatus) {
    super(responseStatus);
  }
}
