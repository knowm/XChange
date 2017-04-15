package org.knowm.xchange.ripple.dto.account;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.ripple.dto.RippleCommon;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class RippleAccountBalances extends RippleCommon {

  @JsonProperty("balances")
  private List<RippleBalance> balances = new ArrayList<>();

  public List<RippleBalance> getBalances() {
    return balances;
  }

  public void setBalances(final List<RippleBalance> value) {
    balances = value;
  }

  @Override
  public String toString() {
    return String.format("%s [ledger=%s, validated=%s, success=%s, balances=%s]", getClass().getSimpleName(), ledger, validated, success, balances);
  }
}