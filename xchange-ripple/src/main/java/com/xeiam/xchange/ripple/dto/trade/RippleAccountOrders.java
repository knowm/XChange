package com.xeiam.xchange.ripple.dto.trade;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RippleAccountOrders {
  @JsonProperty("success")
  private boolean success;

  @JsonProperty("ledger")
  private String ledger;

  @JsonProperty("validated")
  private boolean validated;

  @JsonProperty("orders")
  private List<RippleAccountOrdersBody> orders;

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(final boolean value) {
    success = value;
  }

  public String getLedger() {
    return ledger;
  }

  public void setLedger(final String value) {
    ledger = value;
  }

  public boolean isValidated() {
    return validated;
  }

  public void setValidated(final boolean value) {
    validated = value;
  }

  public List<RippleAccountOrdersBody> getOrders() {
    return orders;
  }

  public void setOrder(final List<RippleAccountOrdersBody> value) {
    orders = value;
  }

  @Override
  public String toString() {
    return String.format("%s [success=%b, validated=%b, ledger=%s, order=%s]", getClass().getSimpleName(), success, validated, ledger, orders);
  }

}
