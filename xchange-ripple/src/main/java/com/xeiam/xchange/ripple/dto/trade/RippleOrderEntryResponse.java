package com.xeiam.xchange.ripple.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class RippleOrderEntryResponse {

  @JsonProperty("success")
  private boolean success;

  @JsonProperty("order")
  private RippleOrderEntryResponseBody order;

  @JsonProperty("hash")
  private String hash;

  @JsonProperty("ledger")
  private String ledger;

  @JsonProperty("state")
  private String state;

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(final boolean value) {
    success = value;
  }

  public RippleOrderEntryResponseBody getOrder() {
    return order;
  }

  public void setOrder(final RippleOrderEntryResponseBody value) {
    order = value;
  }

  public String getHash() {
    return hash;
  }

  public void setHash(final String value) {
    hash = value;
  }

  public String getLedger() {
    return ledger;
  }

  public void setLedger(final String value) {
    ledger = value;
  }

  public String getState() {
    return state;
  }

  public void setState(final String value) {
    state = value;
  }

  @Override
  public String toString() {
    return String.format("%s [success=%b, order=%s, hash=%s, ledger=%s, state=%s]", getClass().getSimpleName(), success, order, hash, ledger, state);
  }
}