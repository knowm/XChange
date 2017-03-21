package org.knowm.xchange.anx.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing the content of a response message from ANX after requesting a bitcoin withdrawal
 */
public final class ANXWithdrawalResponse {

  /**
   * Bitcion transaction id (in the block chain)
   */
  private final String transactionId;

  /**
   * Constructor
   * 
   * @param transactionId
   */
  public ANXWithdrawalResponse(@JsonProperty("trx") String transactionId) {

    this.transactionId = transactionId;
  }

  public String getTransactionId() {

    return transactionId;
  }

  @Override
  public String toString() {

    return "ANXWithdrawalResponse [transactionId=" + transactionId + "]";
  }

}
