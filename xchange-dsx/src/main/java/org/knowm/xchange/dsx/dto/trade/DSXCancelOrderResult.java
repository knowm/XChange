package org.knowm.xchange.dsx.dto.trade;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXCancelOrderResult {

  private final Map<String, BigDecimal> funds;
  private final Map<String, BigDecimal> total;
  private final long orderId;

  public DSXCancelOrderResult(@JsonProperty("funds") Map<String, BigDecimal> funds,
      @JsonProperty("total") Map<String, BigDecimal> total, @JsonProperty("order_id") long orderId) {

    this.funds = funds;
    this.total = total;
    this.orderId = orderId;
  }

  public Map<String, BigDecimal> getFunds() {

    return funds;
  }

  public long getOrderId() {

    return orderId;
  }

  public Map<String, BigDecimal> getTotal() {

    return total;
  }

  @Override
  public String toString() {

    return MessageFormat.format("DSXCancelOrderResult[orderId={0}, funds={1}, total={2}]", orderId, funds, total);
  }
}
