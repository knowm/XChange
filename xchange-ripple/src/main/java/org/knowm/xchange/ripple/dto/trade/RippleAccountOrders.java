package org.knowm.xchange.ripple.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.ripple.dto.RippleCommon;

public class RippleAccountOrders extends RippleCommon {
  @JsonProperty("orders")
  private List<RippleAccountOrdersBody> orders;

  public List<RippleAccountOrdersBody> getOrders() {
    return orders;
  }

  public void setOrder(final List<RippleAccountOrdersBody> value) {
    orders = value;
  }

  @Override
  public String toString() {
    return String.format(
        "%s [success=%b, validated=%b, ledger=%s, order=%s]",
        getClass().getSimpleName(), success, validated, ledger, orders);
  }
}
