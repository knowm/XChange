package org.knowm.xchange.liqui.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.currency.Currency;

public class LiquiAccountFunds {

  private final Map<Currency, BigDecimal> funds = new HashMap<>();

  @JsonCreator
  public LiquiAccountFunds(final Map<String, String> funds) {
    funds
        .entrySet()
        .forEach(
            entry ->
                this.funds.put(
                    Currency.getInstance(entry.getKey()), new BigDecimal(entry.getValue())));
  }

  public Map<Currency, BigDecimal> getFunds() {
    return funds;
  }

  @Override
  public String toString() {
    return "LiquiAccountFunds{" + "funds=" + funds + '}';
  }
}
