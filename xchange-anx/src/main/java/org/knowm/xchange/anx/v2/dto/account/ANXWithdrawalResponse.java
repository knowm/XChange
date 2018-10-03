package org.knowm.xchange.anx.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing the content of a response message from ANX after requesting a bitcoin
 * withdrawal
 */
public final class ANXWithdrawalResponse {

  /** Bitcoin transaction id (in the block chain) */
  private final String transactionId;

  /** error message */
  private final String message;

  /**
   * Constructor
   *
   * @param transactionId
   */
  public ANXWithdrawalResponse(
      @JsonProperty("trx") String transactionId, @JsonProperty("message") String message) {
    this.message = message;
    this.transactionId = transactionId;
  }

  public String getTransactionId() {

    return transactionId;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {

    return "ANXWithdrawalResponse [transactionId=" + transactionId + "]";
  }
}
