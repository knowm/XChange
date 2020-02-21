package org.knowm.xchange.coinbase.v2.dto.account.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CoinbaseTransactionV2NetworkField {

  private String status;

  public CoinbaseTransactionV2NetworkField(@JsonProperty("status") String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "{" + "\"status\":" + '\"' + status + '\"' + '}';
  }
}
