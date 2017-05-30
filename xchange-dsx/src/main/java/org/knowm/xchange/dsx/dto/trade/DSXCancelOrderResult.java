package org.knowm.xchange.dsx.dto.trade;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXCancelOrderResult {

  private final Map<String, BigDecimal> funds;
  private final long orderId;

  public DSXCancelOrderResult(@JsonProperty("funds") Map<String, BigDecimal> funds, @JsonProperty("order_id") long orderId) {

    this.funds = funds;
    this.orderId = orderId;
  }

  public Map<String, BigDecimal> getFunds() {

    return funds;
  }

  public long getOrderId() {

    return orderId;
  }

  @Override
  public String toString() {

    return "DSXCancelOrderResult{" +
        "funds=" + funds +
        ", orderId=" + orderId +
        '}';
  }
}
