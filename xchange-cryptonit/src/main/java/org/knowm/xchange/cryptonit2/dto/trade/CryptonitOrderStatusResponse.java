package org.knowm.xchange.cryptonit2.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class CryptonitOrderStatusResponse {
  private final CryptonitOrderStatus status;
  private final CryptonitOrderTransaction[] transactions;
  private final String error;

  /**
   * @param status In Queue, Open or Finished.
   * @param transactions Each transaction in dictionary is represented as a list of tid, usd, price,
   *     fee, btc, datetime and type (0 - deposit; 1 - withdrawal; 2 - market trade).
   */
  public CryptonitOrderStatusResponse(
      @JsonProperty("status")
          @JsonDeserialize(using = CryptonitOrderStatus.CryptonitOrderStatusDeserializer.class)
          CryptonitOrderStatus status,
      @JsonProperty("transactions") CryptonitOrderTransaction[] transactions,
      @JsonProperty("error") String error) {

    this.status = status;
    this.transactions = transactions;
    this.error = error;
  }

  public CryptonitOrderStatus getStatus() {
    return status;
  }

  public CryptonitOrderTransaction[] getTransactions() {
    return transactions;
  }

  public String getError() {
    return error;
  }
}
