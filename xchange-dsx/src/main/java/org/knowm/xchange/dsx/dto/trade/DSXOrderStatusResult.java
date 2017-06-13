package org.knowm.xchange.dsx.dto.trade;

import java.math.BigDecimal;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXOrderStatusResult {

  private final String pair;
  private final BigDecimal amount;
  private final BigDecimal start_amount;
  private final BigDecimal rate;
  private final Long timestamp;
  private final Integer status;
  private final String orderType;
  private final ClientDeal[] clientDeals;

  public DSXOrderStatusResult(@JsonProperty("pair") String pair, @JsonProperty("amount") BigDecimal amount, @JsonProperty("start_amount") BigDecimal
      start_amount, @JsonProperty("rate") BigDecimal rate, @JsonProperty("timestamp") Long timestamp, @JsonProperty("status") Integer status,
      @JsonProperty("orderType") String orderType, @JsonProperty("clientDeals") ClientDeal[] clientDeals) {
    this.pair = pair;
    this.amount = amount;
    this.start_amount = start_amount;
    this.rate = rate;
    this.timestamp = timestamp;
    this.status = status;
    this.orderType = orderType;
    this.clientDeals = clientDeals;
  }

  @Override
  public String toString() {
    return "DSXOrderStatusResult{" +
        "pair='" + pair + '\'' +
        ", amount=" + amount +
        ", start_amount=" + start_amount +
        ", rate=" + rate +
        ", timestamp=" + timestamp +
        ", status=" + status +
        ", orderType='" + orderType + '\'' +
        ", clientDeals=" + Arrays.toString(clientDeals) +
        '}';
  }
}
