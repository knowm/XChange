package org.knowm.xchange.quadrigacx.dto.account;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.currency.Currency;

public final class QuadrigaCxBalance {

  private final BigDecimal fee;
  private final String error;
  private Map<Currency, BigDecimal> currencyReserved = new HashMap<>();
  private Map<Currency, BigDecimal> currencyAvailable = new HashMap<>();
  private Map<Currency, BigDecimal> currencyBalance = new HashMap<>();
  private List<Currency> currencies = new ArrayList<>();

  public QuadrigaCxBalance(
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("error") String error,
      @JsonProperty("fees") Object fees) {

    this.fee = fee;
    this.error = error;
  }

  @JsonAnySetter
  public void setCurrencyAmount(String currencyBalance, BigDecimal amount) {

    String[] parts = currencyBalance.split("_");
    Currency currency = Currency.getInstance(parts[0].toUpperCase());

    if (parts.length > 1) {
      switch (parts[1]) {
        case "reserved":
          this.currencyReserved.put(currency, amount);
          break;
        case "available":
          this.currencyAvailable.put(currency, amount);
          break;
        case "balance":
          this.currencyBalance.put(currency, amount);
          break;
      }

      if (!currencies.contains(currency)) currencies.add(currency);
    }
  }

  public BigDecimal getCurrencyBalance(Currency currency) {
    return this.currencyBalance.get(currency);
  }

  public BigDecimal getCurrencyReserved(Currency currency) {
    return this.currencyReserved.get(currency);
  }

  public BigDecimal getCurrencyAvailable(Currency currency) {
    return this.currencyAvailable.get(currency);
  }

  public List<Currency> getCurrencyList() {
    return this.currencies;
  }

  public BigDecimal getFee() {

    return fee;
  }

  public String getError() {

    return error;
  }

  @Override
  public String toString() {
    return "QuadrigaCxBalance{"
        + "currencyReserved="
        + currencyReserved
        + ", currencyAvailable="
        + currencyAvailable
        + ", currencyBalance="
        + currencyBalance
        + ", fee="
        + fee
        + ", error='"
        + error
        + '\''
        + '}';
  }
}
