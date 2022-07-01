package org.knowm.xchange.coinbase.v2.dto.account.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CoinbaseTransactionV2Field {

  protected String id;
  protected String resource;
  protected String resourcePath;

  public CoinbaseTransactionV2Field(
      @JsonProperty("id") String id,
      @JsonProperty("resource") String resource,
      @JsonProperty("resource_path") String resourcePath) {
    this.id = id;
    this.resource = resource;
    this.resourcePath = resourcePath;
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
        + '}';
  }
}
