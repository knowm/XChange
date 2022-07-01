package org.knowm.xchange.coinmate.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinmateReplaceResponseData {
  private final boolean replacedOrderCancellationFinished;
  private final String replacedOrderCancellationResult;
  private final BigDecimal replacedOrderRemainingAmount;
  private final long createdOrderId;

  public CoinmateReplaceResponseData(
      @JsonProperty("replacedOrderCancellationFinished") boolean replacedOrderCancellationFinished,
      @JsonProperty("replacedOrderCancellationResult") String replacedOrderCancellationResult,
      @JsonProperty("replacedOrderRemainingAmount") BigDecimal replacedOrderRemainingAmount,
      @JsonProperty("createdOrderId") long createdOrderId) {
    this.replacedOrderCancellationFinished = replacedOrderCancellationFinished;
    this.replacedOrderCancellationResult = replacedOrderCancellationResult;
    this.replacedOrderRemainingAmount = replacedOrderRemainingAmount;
    this.createdOrderId = createdOrderId;
  }

  public boolean isReplacedOrderCancellationFinished() {
    return replacedOrderCancellationFinished;
  }

  public String getReplacedOrderCancellationResult() {
    return replacedOrderCancellationResult;
  }

  public BigDecimal getReplacedOrderRemainingAmount() {
    return replacedOrderRemainingAmount;
  }

  public long getCreatedOrderId() {
    return createdOrderId;
  }
}
