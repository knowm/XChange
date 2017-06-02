package org.knowm.xchange.dsx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXFiatWithdraw {

  private final long transactionId;

  public DSXFiatWithdraw(@JsonProperty("transactionId") long transactionId) {

    this.transactionId = transactionId;
  }

  public long getTransactionId() {

    return transactionId;
  }

  @Override
  public String toString() {
    return "DSXFiatWithdraw{" +
        "transactionId=" + transactionId +
        '}';
  }
}
