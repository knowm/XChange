package org.knowm.xchange.loyalbit.dto.account;

import java.math.BigDecimal;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import si.mazi.rescu.ExceptionalReturnContentException;

/**
 * @author Matija Mazi
 */
public class LoyalbitBalance {

  @JsonProperty("available_usd")
  BigDecimal availableUsd;
  @JsonProperty("available_btc")
  BigDecimal availableBtc;
  @JsonProperty("reserved_order_usd")
  BigDecimal reservedOrderUsd;
  @JsonProperty("reserved_order_btc")
  BigDecimal reservedOrderBtc;
  @JsonProperty("reserved_withdraw_usd")
  BigDecimal reservedWithdrawUsd;
  @JsonProperty("reserved_withdraw_btc")
  BigDecimal reservedWithdrawBtc;
  @JsonProperty("fee")
  BigDecimal fee;

  public LoyalbitBalance(@JsonProperty("available_usd") BigDecimal availableUsd, @JsonProperty("available_btc") BigDecimal availableBtc,
      @JsonProperty("reserved_order_usd") BigDecimal reservedOrderUsd, @JsonProperty("reserved_order_btc") BigDecimal reservedOrderBtc,
      @JsonProperty("reserved_withdraw_usd") BigDecimal reservedWithdrawUsd, @JsonProperty("reserved_withdraw_btc") BigDecimal reservedWithdrawBtc,
      @JsonProperty("fee") BigDecimal fee, @JsonProperty("status") Integer status) throws ExceptionalReturnContentException {
    if (Objects.equals(status, 0)) {
      throw new ExceptionalReturnContentException("Status indicates failure: " + status);
    }
    this.availableUsd = availableUsd;
    this.availableBtc = availableBtc;
    this.reservedOrderUsd = reservedOrderUsd;
    this.reservedOrderBtc = reservedOrderBtc;
    this.reservedWithdrawUsd = reservedWithdrawUsd;
    this.reservedWithdrawBtc = reservedWithdrawBtc;
    this.fee = fee;
  }

  public BigDecimal getAvailableUsd() {
    return availableUsd;
  }

  public BigDecimal getAvailableBtc() {
    return availableBtc;
  }

  public BigDecimal getReservedOrderUsd() {
    return reservedOrderUsd;
  }

  public BigDecimal getReservedOrderBtc() {
    return reservedOrderBtc;
  }

  public BigDecimal getReservedWithdrawUsd() {
    return reservedWithdrawUsd;
  }

  public BigDecimal getReservedWithdrawBtc() {
    return reservedWithdrawBtc;
  }

  public BigDecimal getFee() {
    return fee;
  }

  @Override
  public String toString() {
    return String.format(
        "LoyalbitBalance{availableUsd=%s, availableBtc=%s, reservedOrderUsd=%s, reservedOrderBtc=%s, reservedWithdrawUsd=%s, reservedWithdrawBtc=%s, fee=%s}",
        availableUsd, availableBtc, reservedOrderUsd, reservedOrderBtc, reservedWithdrawUsd, reservedWithdrawBtc, fee);
  }
}
