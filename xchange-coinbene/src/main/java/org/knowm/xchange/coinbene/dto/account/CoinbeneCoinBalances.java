package org.knowm.xchange.coinbene.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.coinbene.dto.CoinbeneResponse;

public class CoinbeneCoinBalances extends CoinbeneResponse {
  private final List<CoinbeneCoinBalance> balances;
  private final String account;

  public CoinbeneCoinBalances(
      @JsonProperty("balance") List<CoinbeneCoinBalance> balances,
      @JsonProperty("account") String account) {
    this.balances = balances;
    this.account = account;
  }

  public List<CoinbeneCoinBalance> getBalances() {
    return balances;
  }

  public String getAccount() {
    return account;
  }
}
