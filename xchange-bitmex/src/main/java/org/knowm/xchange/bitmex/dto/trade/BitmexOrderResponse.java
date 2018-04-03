package org.knowm.xchange.bitmex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BitmexOrderResponse {

  private final BitmexOrderResponseDescription description;
  private final List<String> transactionIds;

  /**
   * Constructor
   *
   * @param description
   * @param transactionId
   */
  public BitmexOrderResponse(
      @JsonProperty("descr") BitmexOrderResponseDescription description,
      @JsonProperty("txid") List<String> transactionId) {

    this.description = description;
    this.transactionIds = transactionId;
  }

  public BitmexOrderResponseDescription getDescription() {

    return description;
  }

  public List<String> getTransactionIds() {

    return transactionIds;
  }

  @Override
  public String toString() {

    return "BitmexOrderResponse [description="
        + description
        + ", transactionId="
        + transactionIds
        + "]";
  }

  public static class BitmexOrderResponseDescription {

    private final String orderDescription;
    private final String closeDescription;

    /**
     * Constructor
     *
     * @param orderDescription
     * @param closeDescription
     */
    public BitmexOrderResponseDescription(
        @JsonProperty("order") String orderDescription,
        @JsonProperty("close") String closeDescription) {

      this.orderDescription = orderDescription;
      this.closeDescription = closeDescription;
    }

    public String getOrderDescription() {

      return orderDescription;
    }

    public String getCloseDescription() {

      return closeDescription;
    }

    @Override
    public String toString() {

      return "BitmexOrderResponseDescription [orderDescription="
          + orderDescription
          + ", closeDescription="
          + closeDescription
          + "]";
    }
  }
}
