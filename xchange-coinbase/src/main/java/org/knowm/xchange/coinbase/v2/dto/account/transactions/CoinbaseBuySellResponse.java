package org.knowm.xchange.coinbase.v2.dto.account.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class CoinbaseBuySellResponse {
  private final List<CoinbaseBuySell> data;

  public CoinbaseBuySellResponse(@JsonProperty("data") List<CoinbaseBuySell> data) {
    this.data = data;
  }
}
