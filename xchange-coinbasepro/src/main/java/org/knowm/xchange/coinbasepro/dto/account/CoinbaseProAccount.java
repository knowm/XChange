package org.knowm.xchange.coinbasepro.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CoinbaseProAccount {
  private final String id;
  private final String currency;
  private final String profileId;
  private final BigDecimal balance;
  private final BigDecimal hold;
  private final BigDecimal available;
  private final boolean tradingEnabled;

  public CoinbaseProAccount(
      @JsonProperty("id") String id,
      @JsonProperty("currency") String currency,
      @JsonProperty("profile_id") String profileId,
      @JsonProperty("balance") BigDecimal balance,
      @JsonProperty("hold") BigDecimal hold,
      @JsonProperty("available") BigDecimal available,
      @JsonProperty("trading_enabled") boolean tradingEnabled) {
    this.id = id;
    this.currency = currency;
    this.profileId = profileId;
    this.balance = balance;
    this.hold = hold;
    this.available = available;
    this.tradingEnabled = tradingEnabled;
  }
}
