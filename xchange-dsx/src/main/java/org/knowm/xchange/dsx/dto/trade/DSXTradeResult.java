package org.knowm.xchange.dsx.dto.trade;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXTradeResult {

  private final long orderId;
  private final BigDecimal received;
  private final BigDecimal remains;
  private final Map<String, BigDecimal> funds;
  private final Map<String, BigDecimal> total;

  public DSXTradeResult(@JsonProperty("order_id") long orderId, @JsonProperty("received") BigDecimal received,
      @JsonProperty("remains") BigDecimal remains, @JsonProperty("funds") Map<String, BigDecimal> funds,
      @JsonProperty("total") Map<String, BigDecimal> total) {

    this.orderId = orderId;
    this.received = received;
    this.remains = remains;
    this.funds = funds;
    this.total = total;
  }

  public long getOrderId() {

    return orderId;
  }

  public BigDecimal getReceived() {

    return received;
  }

  public BigDecimal getRemains() {

    return remains;
  }

  public Map<String, BigDecimal> getFunds() {

    return funds;
  }

  public Map<String, BigDecimal> getTotal() {

    return total;
  }

  @Override
  public String toString() {

    return MessageFormat.format("DSXTrade[orderId={0}, received={1}, remains={2}, funds={3}, total={4}]", orderId, received, remains, funds, total);
  }
}
