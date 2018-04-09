package org.knowm.xchange.therock.dto.account;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class TheRockBalance {

  private String currency;

  private BigDecimal balance;

  private BigDecimal tradingBalance;

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public BigDecimal getTradingBalance() {
    return tradingBalance;
  }

  @Override
  public String toString() {
    return String.format(
        "TheRockBalance{currency='%s', balance=%s, trandingBalance=%s}",
        currency, balance, tradingBalance);
  }
}
