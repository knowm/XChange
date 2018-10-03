package org.knowm.xchange.dto.trade;

import java.io.Serializable;
import java.util.List;

/**
 * DTO representing open loan orders Open loan orders are loan order that have placed with the
 * exchange that have not yet been matched to a counterparty.
 */
public final class OpenLoanOrders implements Serializable {

  private final List<FixedRateLoanOrder> openFixedRateLoanOrders;
  private final List<FloatingRateLoanOrder> openFloatingRateLoanOrders;

  /**
   * Constructor
   *
   * @param openFixedRateLoanOrders
   * @param openFloatingRateLoanOrders
   */
  public OpenLoanOrders(
      List<FixedRateLoanOrder> openFixedRateLoanOrders,
      List<FloatingRateLoanOrder> openFloatingRateLoanOrders) {

    this.openFixedRateLoanOrders = openFixedRateLoanOrders;
    this.openFloatingRateLoanOrders = openFloatingRateLoanOrders;
  }

  public List<FixedRateLoanOrder> getOpenFixedRateLoanOrders() {

    return openFixedRateLoanOrders;
  }

  public List<FloatingRateLoanOrder> getOpenFloatingRateLoanOrders() {

    return openFloatingRateLoanOrders;
  }

  @Override
  public String toString() {

    return "OpenLoanOrders [openFixedRateLoanOrders="
        + openFixedRateLoanOrders
        + ", openFloatingRateLoanOrders="
        + openFloatingRateLoanOrders
        + "]";
  }
}
