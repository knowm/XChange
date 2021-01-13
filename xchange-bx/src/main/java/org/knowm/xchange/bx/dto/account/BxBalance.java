package org.knowm.xchange.bx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BxBalance {

  private final BigDecimal total;
  private final BigDecimal available;
  private final BigDecimal orders;
  private final BigDecimal withdrawals;
  private final BigDecimal deposits;
  private final BigDecimal options;

  public BxBalance(
      @JsonProperty("total") BigDecimal total,
      @JsonProperty("available") BigDecimal available,
      @JsonProperty("orders") BigDecimal orders,
      @JsonProperty("withdrawals") BigDecimal withdrawals,
      @JsonProperty("deposits") BigDecimal deposits,
      @JsonProperty("options") BigDecimal options) {
    this.total = total;
    this.available = available;
    this.orders = orders;
    this.withdrawals = withdrawals;
    this.deposits = deposits;
    this.options = options;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public BigDecimal getAvailable() {
    return available;
  }

  public BigDecimal getOrders() {
    return orders;
  }

  public BigDecimal getWithdrawals() {
    return withdrawals;
  }

  public BigDecimal getDeposits() {
    return deposits;
  }

  public BigDecimal getOptions() {
    return options;
  }

  @Override
  public String toString() {
    return "BxBalance{"
        + "total="
        + total
        + ", available="
        + available
        + ", orders="
        + orders
        + ", withdrawals="
        + withdrawals
        + ", deposits="
        + deposits
        + ", options="
        + options
        + '}';
  }
}
