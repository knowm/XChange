package org.knowm.xchange.coindirect.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.coindirect.dto.meta.CoindirectCurrency;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoindirectWallet {
  public final long id;
  public final String description;
  public final CoindirectCurrency currency;
  public final boolean supportsWithdrawals;
  public final boolean supportsDeposits;
  public final String address;
  public final String lookup;
  public final BigDecimal balance;

  public CoindirectWallet(
      @JsonProperty("id") long id,
      @JsonProperty("description") String description,
      @JsonProperty("currency") CoindirectCurrency currency,
      @JsonProperty("supportsWithdrawals") boolean supportsWithdrawals,
      @JsonProperty("supportsDeposits") boolean supportsDeposits,
      @JsonProperty("address") String address,
      @JsonProperty("lookup") String lookup,
      @JsonProperty("balance") BigDecimal balance) {
    this.id = id;
    this.description = description;
    this.currency = currency;
    this.supportsWithdrawals = supportsWithdrawals;
    this.supportsDeposits = supportsDeposits;
    this.address = address;
    this.lookup = lookup;
    this.balance = balance;
  }

  @Override
  public String toString() {
    return "CoindirectWallet{"
        + "id="
        + id
        + ", description='"
        + description
        + '\''
        + ", currency="
        + currency
        + ", supportsWithdrawals="
        + supportsWithdrawals
        + ", supportsDeposits="
        + supportsDeposits
        + ", address='"
        + address
        + '\''
        + ", lookup='"
        + lookup
        + '\''
        + ", balance="
        + balance
        + '}';
  }
}
