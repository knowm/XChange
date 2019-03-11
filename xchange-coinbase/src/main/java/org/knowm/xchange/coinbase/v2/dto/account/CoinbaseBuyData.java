package org.knowm.xchange.coinbase.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinbaseBuyData extends CoinbaseWalletResponseData<CoinbaseBuyData.CoinbaseBuy> {

  private CoinbaseBuyData(@JsonProperty("data") CoinbaseBuy data) {
    super(data);
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class CoinbaseBuy extends CoinbaseWalletResponseData.CoinbaseWalletResponse {

    @JsonCreator
    CoinbaseBuy(
        @JsonProperty("id") String id,
        @JsonProperty("status") String status,
        @JsonProperty("transaction") String transaction,
        @JsonProperty("commited") boolean committed) {
      super(id, status, transaction, committed);
    }
  }
}
