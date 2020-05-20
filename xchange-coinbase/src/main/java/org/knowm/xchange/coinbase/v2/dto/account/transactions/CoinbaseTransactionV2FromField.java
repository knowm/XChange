package org.knowm.xchange.coinbase.v2.dto.account.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CoinbaseTransactionV2FromField extends CoinbaseTransactionV2Field {

  private String currency;

  public CoinbaseTransactionV2FromField(
      @JsonProperty("id") String id,
      @JsonProperty("resource") String resource,
      @JsonProperty("resource_path") String resourcePath,
      @JsonProperty("currency") String currency) {
    super(id, resource, resourcePath);
    this.currency = currency;
  }

  @Override
  public String toString() {
    return "{"
        + "\"id\":"
        + '\"'
        + id
        + '\"'
        + ",\"resource\":"
        + '\"'
        + resource
        + '\"'
        + ",\"resourcePath\":"
        + '\"'
        + resourcePath
        + '\"'
        + ",\"currency\":"
        + '\"'
        + currency
        + '\"'
        + '}';
  }
}
