package org.known.xchange.acx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class AcxAccount {
  public final String currency;
  /** Account balance, exclude locked funds */
  public final BigDecimal balance;
  /** locked funds */
  public final BigDecimal locked;

  public AcxAccount(
      @JsonProperty("currency") String currency,
      @JsonProperty("balance") BigDecimal balance,
      @JsonProperty("locked") BigDecimal locked) {
    this.currency = currency;
    this.balance = balance;
    this.locked = locked;
  }
}
