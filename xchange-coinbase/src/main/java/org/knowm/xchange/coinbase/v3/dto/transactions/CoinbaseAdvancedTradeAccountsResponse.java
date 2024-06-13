package org.knowm.xchange.coinbase.v3.dto.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class CoinbaseAdvancedTradeAccountsResponse {
  public final List<CoinbaseAdvancedTradesAccounts> accounts;
  private final boolean hasNext;
  private final String cursor;
  private final String size;

  public CoinbaseAdvancedTradeAccountsResponse(@JsonProperty("accounts") List<CoinbaseAdvancedTradesAccounts> accounts,
                                               @JsonProperty(
          "has_next") boolean hasNext, @JsonProperty("cursor") String cursor, @JsonProperty("size") String size
  ) {
    this.accounts = accounts;
    this.hasNext = hasNext;
    this.cursor = cursor;
    this.size = size;
  }

}
