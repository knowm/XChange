package org.knowm.xchange.coinsuper.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinsuperBalance {

  private final String asset;

  private final BigDecimal balance;

  private final BigDecimal frozen;

  private final int state;

  public CoinsuperBalance(
      @JsonProperty("asset") String asset,
      @JsonProperty("balance") BigDecimal balance,
      @JsonProperty("frozen") BigDecimal frozen,
      @JsonProperty("state") int state) {
    super();
    this.asset = asset;
    this.balance = balance;
    this.frozen = frozen;
    this.state = state;
  }

  public String getAsset() {
    return asset;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public BigDecimal getFrozen() {
    return frozen;
  }

  public int getState() {
    return state;
  }
}
