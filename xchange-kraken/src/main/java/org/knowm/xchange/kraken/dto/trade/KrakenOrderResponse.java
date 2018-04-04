package org.knowm.xchange.kraken.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class KrakenOrderResponse {

  private final KrakenOrderResponseDescription description;
  private final List<String> transactionIds;

  /**
   * Constructor
   *
   * @param description
   * @param transactionId
   */
  public KrakenOrderResponse(
      @JsonProperty("descr") KrakenOrderResponseDescription description,
      @JsonProperty("txid") List<String> transactionId) {

    this.description = description;
    this.transactionIds = transactionId;
  }

  public KrakenOrderResponseDescription getDescription() {

    return description;
  }

  public List<String> getTransactionIds() {

    return transactionIds;
  }

  @Override
  public String toString() {

    return "KrakenOrderResponse [description="
        + description
        + ", transactionId="
        + transactionIds
        + "]";
  }

  public static class KrakenOrderResponseDescription {

    private final String orderDescription;
    private final String closeDescription;

    /**
     * Constructor
     *
     * @param orderDescription
     * @param closeDescription
     */
    public KrakenOrderResponseDescription(
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

      return "KrakenOrderResponseDescription [orderDescription="
          + orderDescription
          + ", closeDescription="
          + closeDescription
          + "]";
    }
  }
}
