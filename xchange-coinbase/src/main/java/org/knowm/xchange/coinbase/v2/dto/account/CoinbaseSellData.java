package org.knowm.xchange.coinbase.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinbaseSellData extends CoinbaseWalletResponseData<CoinbaseSellData.CoinbaseSell> {

  private CoinbaseSellData(@JsonProperty("data") CoinbaseSell data) {
    super(data);
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class CoinbaseSell extends CoinbaseWalletResponseData.CoinbaseWalletResponse {

    @JsonCreator
    CoinbaseSell(
        @JsonProperty("id") String id,
        @JsonProperty("status") String status,
        @JsonProperty("transaction") String transaction,
        @JsonProperty("commited") boolean committed) {
      super(id, status, transaction, committed);
    }
  }
}
