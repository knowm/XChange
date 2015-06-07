package com.xeiam.xchange.ripple.dto.account;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class RippleAccount {

  @JsonProperty("ledger")
  private String ledger;
  @JsonProperty("validated")
  private Boolean validated;
  @JsonProperty("balances")
  private List<RippleBalance> balances = new ArrayList<RippleBalance>();
  @JsonProperty("success")
  private Boolean success;

  public String getLedger() {
    return ledger;
  }

  public void setLedger(final String value) {
    ledger = value;
  }

  public Boolean getValidated() {
    return validated;
  }

  public void setValidated(final Boolean value) {
    validated = value;
  }

  public List<RippleBalance> getBalances() {
    return balances;
  }

  public void setBalances(final List<RippleBalance> value) {
    balances = value;
  }

  public Boolean isSuccess() {
    return success;
  }

  public void setSuccess(final Boolean value) {
    success = value;
  }

  @Override
  public String toString() {
    return String.format("Account [ledger=%s, validated=%s, success=%s, balances=%s]", //
        ledger, validated, success, balances);
  }
}