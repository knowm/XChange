package com.xeiam.xchange.bter.dto.account;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.bter.dto.BTERBaseResponse;

public class BTERFunds extends BTERBaseResponse {

  private final Map<String, BigDecimal> availableFunds;
  private final Map<String, BigDecimal> lockedFunds;

  /**
   * Constructor
   * 
   * @param aResult
   * @param theAvailableFunds
   * @param theLockedFunds
   */
  public BTERFunds(@JsonProperty("available_funds") Map<String, BigDecimal> theAvailableFunds, @JsonProperty("locked_funds") Map<String, BigDecimal> theLockedFunds,
      @JsonProperty("result") boolean result, @JsonProperty("message") final String message) {

    super(result, message);
    availableFunds = theAvailableFunds;
    lockedFunds = theLockedFunds;

  }

  public Map<String, BigDecimal> getAvailableFunds() {

    return availableFunds;
  }

  public Map<String, BigDecimal> getLockedFunds() {

    return lockedFunds;
  }

  @Override
  public String toString() {

    return "BTERAccountInfoReturn [availableFunds=" + availableFunds + ", lockedFunds=" + lockedFunds + "]";
  }

}
