package org.knowm.xchange.wex.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Map;

/** @author Matija Mazi */
public class WexPlaceOrderResult {

  private final long orderId; // 0 if order completely filled, initial order_id otherwise
  private final BigDecimal received;
  private final BigDecimal remains;
  private final Map<String, BigDecimal> funds;

  /**
   * Constructor
   *
   * @param orderId
   * @param received
   * @param remains
   * @param funds
   */
  public WexPlaceOrderResult(
      @JsonProperty("order_id") long orderId,
      @JsonProperty("received") BigDecimal received,
      @JsonProperty("remains") BigDecimal remains,
      @JsonProperty("funds") Map<String, BigDecimal> funds) {

    this.orderId = orderId;
    this.received = received;
    this.remains = remains;
    this.funds = funds;
  }

  public long getOrderId() {

    return orderId;
  }

  public Map<String, BigDecimal> getFunds() {

    return funds;
  }

  public BigDecimal getReceived() {

    return received;
  }

  public BigDecimal getRemains() {

    return remains;
  }

  @Override
  public String toString() {

    return MessageFormat.format(
        "WexPlaceOrderResult[orderId={0}, received={1}, remains={2}, funds={3}]",
        orderId, received, remains, funds);
  }
}
