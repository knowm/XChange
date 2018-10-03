package org.knowm.xchange.dsx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author Mikhail Wall */
public class DSXCryptoWithdraw {

  private final long transactionId;

  public DSXCryptoWithdraw(@JsonProperty("transactionId") long transactionId) {

    this.transactionId = transactionId;
  }

  public long getTransactionId() {

    return transactionId;
  }

  @Override
  public String toString() {
    return "DSXCryptoWithdraw{" + "transactionId=" + transactionId + '}';
  }
}
