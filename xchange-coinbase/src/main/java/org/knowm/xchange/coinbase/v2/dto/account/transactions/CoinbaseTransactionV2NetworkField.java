package org.knowm.xchange.coinbase.v2.dto.account.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CoinbaseTransactionV2NetworkField {

  private final String status;
  private final String networkName;
  private final String transactionFee;
  private final String hash;

  public CoinbaseTransactionV2NetworkField(
          @JsonProperty("status") String status,
          @JsonProperty("network_name") String networkName,
          @JsonProperty("transaction_fee") String transactionFee,
            @JsonProperty("hash") String hash
  ) {
    this.status = status;
    this.networkName = networkName;
    this.transactionFee = transactionFee;
    this.hash = hash;
  }

  @Override
  public String toString() {
    return "{" + "\"status\":" + '\"' + status + '\"' + '}';
  }
}
