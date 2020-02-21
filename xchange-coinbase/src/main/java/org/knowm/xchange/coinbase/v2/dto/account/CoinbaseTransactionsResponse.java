package org.knowm.xchange.coinbase.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.knowm.xchange.coinbase.v2.dto.account.transactions.CoinbaseTransactionV2;

import java.util.List;

@Getter
public class CoinbaseTransactionsResponse {
  private final List<CoinbaseTransactionV2> data;

  public CoinbaseTransactionsResponse(@JsonProperty("data") List<CoinbaseTransactionV2> data) {
    this.data = data;
  }
}
