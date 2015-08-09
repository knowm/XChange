package com.xeiam.xchange.ripple.dto.trade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.ripple.dto.RippleAmount;
import com.xeiam.xchange.ripple.dto.RippleCommon;

public class RippleOrderDetails extends RippleCommon {

  @JsonProperty("timestamp")
  public Date timestamp;

  @JsonProperty("fee")
  public BigDecimal fee;

  @JsonProperty("action")
  public String action;

  @JsonProperty("direction")
  public String direction;

  @JsonProperty("order")
  public RippleOrderResponseBody order;

  @JsonProperty("balance_changes")
  public List<RippleAmount> balanceChanges;

  @JsonProperty("order_changes")
  public List<RippleOrderResponseBody> orderChanges;

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(final Date value) {
    this.timestamp = value;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public void setFee(final BigDecimal value) {
    fee = value;
  }

  public String getAction() {
    return action;
  }

  public void setAction(final String value) {
    action = value;
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection(final String value) {
    this.direction = value;
  }

  public void setOrder(final RippleOrderResponseBody value) {
    order = value;
  }

  public List<RippleOrderResponseBody> getOrderChanges() {
    return orderChanges;
  }

  public List<RippleAmount> getBalanceChanges() {
    return balanceChanges;
  }

  public void setBalanceChanges(final List<RippleAmount> value) {
    balanceChanges = value;
  }

  public RippleOrderResponseBody getOrder() {
    return order;
  }

  public void setOrderChanges(final List<RippleOrderResponseBody> value) {
    orderChanges = value;
  }

  @Override
  public String toString() {
    return String.format(
        "%s [hash=%s, ledger=%d, validated=%s, success=%s timestamp=%s, fee=%s, action=%s, direction=%s, order=%s, balance_changes=%s order_changes=%s]",
        getClass().getSimpleName(), hash, ledger, validated, success, timestamp, fee, action, direction, order, balanceChanges, orderChanges);
  }
}
