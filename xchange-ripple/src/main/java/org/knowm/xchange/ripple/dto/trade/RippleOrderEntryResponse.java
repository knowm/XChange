package org.knowm.xchange.ripple.dto.trade;

import org.knowm.xchange.ripple.dto.RippleCommon;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class RippleOrderEntryResponse extends RippleCommon {

  @JsonProperty("order")
  private RippleOrderResponseBody order;

  public RippleOrderResponseBody getOrder() {
    return order;
  }

  public void setOrder(final RippleOrderResponseBody value) {
    order = value;
  }

  @Override
  public String toString() {
    return String.format("%s [success=%b, hash=%s, ledger=%s, state=%s, order=%s]", getClass().getSimpleName(), success, hash, ledger, state, order);
  }
}
