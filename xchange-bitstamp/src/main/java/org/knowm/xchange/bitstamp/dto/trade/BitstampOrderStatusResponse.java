package org.knowm.xchange.bitstamp.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class BitstampOrderStatusResponse {
  private final BitstampOrderStatus status;
  private final BitstampOrderTransaction[] transactions;
  private final String error;

  /**
   * @param status In Queue, Open or Finished.
   * @param transactions Each transaction in dictionary is represented as a list of tid, usd, price,
   *     fee, btc, datetime and type (0 - deposit; 1 - withdrawal; 2 - market trade).
   */
  public BitstampOrderStatusResponse(
      @JsonProperty("status")
          @JsonDeserialize(using = BitstampOrderStatus.BitstampOrderStatusDeserializer.class)
          BitstampOrderStatus status,
      @JsonProperty("transactions") BitstampOrderTransaction[] transactions,
      @JsonProperty("error") String error) {

    this.status = status;
    this.transactions = transactions;
    this.error = error;
  }

  public BitstampOrderStatus getStatus() {
    return status;
  }

  public BitstampOrderTransaction[] getTransactions() {
    return transactions;
  }

  public String getError() {
    return error;
  }
}
