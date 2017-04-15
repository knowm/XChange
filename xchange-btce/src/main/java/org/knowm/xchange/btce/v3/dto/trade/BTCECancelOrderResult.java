package org.knowm.xchange.btce.v3.dto.trade;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Matija Mazi
 */
public class BTCECancelOrderResult {

  private final long orderId;
  private final Map<String, BigDecimal> funds;

  /**
   * Constructor
   *
   * @param orderId
   * @param funds
   */
  public BTCECancelOrderResult(@JsonProperty("order_id") long orderId, @JsonProperty("funds") Map<String, BigDecimal> funds) {

    this.orderId = orderId;
    this.funds = funds;
  }

  public long getOrderId() {

    return orderId;
  }

  public Map<String, BigDecimal> getFunds() {

    return funds;
  }

  @Override
  public String toString() {

    return MessageFormat.format("BTCECancelOrderResult[orderId={0}, funds={1}]", orderId, funds);
  }

}
