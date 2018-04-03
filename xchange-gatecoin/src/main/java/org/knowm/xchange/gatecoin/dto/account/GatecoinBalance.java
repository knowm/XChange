package org.knowm.xchange.gatecoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author sumedha */
public class GatecoinBalance {
  private final String currency;
  private final BigDecimal balance;
  private final BigDecimal availableBalance;
  private final BigDecimal pendingIncoming;
  private final BigDecimal pendingOutgoing;
  private final BigDecimal openOrder;
  private final Boolean isDigital;

  public GatecoinBalance(
      @JsonProperty("currency") String currency,
      @JsonProperty("balance") BigDecimal balance,
      @JsonProperty("availableBalance") BigDecimal availableBalance,
      @JsonProperty("pendingIncoming") BigDecimal pendingIncoming,
      @JsonProperty("pendingOutgoing") BigDecimal pendingOutgoing,
      @JsonProperty("openOrder") BigDecimal openOrder,
      @JsonProperty("isDigital") Boolean isDigital) {
    this.currency = currency;
    this.balance = balance;
    this.availableBalance = availableBalance;
    this.pendingIncoming = pendingIncoming;
    this.pendingOutgoing = pendingOutgoing;
    this.openOrder = openOrder;
    this.isDigital = isDigital;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public BigDecimal getAvailableBalance() {
    return availableBalance;
  }

  public BigDecimal getPendingIncoming() {
    return pendingIncoming;
  }

  public BigDecimal getPendingOutgoing() {
    return pendingOutgoing;
  }

  public BigDecimal getOpenOrder() {
    return openOrder;
  }

  public Boolean getIsDigital() {
    return isDigital;
  }

  @Override
  public String toString() {
    return "Balance:currency = "
        + currency
        + " balance="
        + balance
        + " availableBalance="
        + availableBalance
        + " frozen= "
        + openOrder;
  }
}
