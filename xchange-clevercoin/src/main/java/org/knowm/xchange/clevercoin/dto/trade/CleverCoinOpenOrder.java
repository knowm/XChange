package org.knowm.xchange.clevercoin.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Ronald Bultman
 */
public final class CleverCoinOpenOrder {

  private final String orderId;
  private final String errorMessage;

  /**
   * Constructor
   * 
   * @param result
   */
  public CleverCoinOpenOrder(@JsonProperty("orderID") String orderId,
      @JsonProperty("error") @JsonDeserialize(using = CleverCoinErrorDeserializer.class) String errorMessage) {

    this.orderId = orderId;
    this.errorMessage = errorMessage;
  }

  public String getOrderId() {

    return orderId;
  }

  @JsonIgnore
  public String getErrorMessage() {

    return errorMessage;
  }

  @Override
  public String toString() {

    return errorMessage != null ? errorMessage : String.format("OrderId=%s", orderId);
  }
}
