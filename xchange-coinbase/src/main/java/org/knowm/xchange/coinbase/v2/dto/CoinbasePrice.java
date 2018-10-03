package org.knowm.xchange.coinbase.v2.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.utils.Assert;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinbasePrice {

  private final Currency currency;
  private final BigDecimal amount;
  private final String toString;

  @JsonCreator
  public CoinbasePrice(
      @JsonProperty("amount") BigDecimal amount, @JsonProperty("currency") String currency) {
    this(amount, Currency.getInstance(currency));
  }

  public CoinbasePrice(BigDecimal amount, Currency currency) {
    Assert.notNull(currency, "Null currency");
    Assert.notNull(amount, "Null amount");
    this.currency = currency;
    this.amount = amount;

    toString = String.format("%.2f %s", amount, currency);
  }

  public Currency getCurrency() {
    return currency;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    CoinbasePrice other = (CoinbasePrice) obj;
    return amount.compareTo(other.amount) == 0 && currency.equals(other.currency);
  }

  @Override
  public String toString() {
    return toString;
  }
}
