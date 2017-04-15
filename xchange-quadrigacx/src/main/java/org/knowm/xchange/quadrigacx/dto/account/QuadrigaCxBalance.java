package org.knowm.xchange.quadrigacx.dto.account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.currency.Currency;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class QuadrigaCxBalance {

  private Map<String, BigDecimal> currencyReserved = new HashMap<>();
  private Map<String, BigDecimal> currencyAvailable = new HashMap<>();
  private Map<String, BigDecimal> currencyBalance = new HashMap<>();
  private List<Currency> currencies = new ArrayList<>();

  private final BigDecimal fee;

  private final String error;

  public QuadrigaCxBalance(@JsonProperty("fee") BigDecimal fee, @JsonProperty("error") String error, @JsonProperty("fees") Object fees) {

    this.fee = fee;
    this.error = error;
  }

  @JsonAnySetter
  public void setCurrencyAmount(String currencyBalance, BigDecimal amount) {

    String[] parts = currencyBalance.split("_");
    if (parts.length > 1) {
      switch (parts[1]) {
        case "reserved":
          this.currencyReserved.put(parts[0], amount);
          break;
        case "available":
          this.currencyAvailable.put(parts[0], amount);
          break;
        case "balance":
          this.currencyBalance.put(parts[0], amount);
          break;
      }
      Currency currency = new Currency(parts[0]);
      if (!currencies.contains(currency))
        currencies.add(currency);
    }
  }

  public BigDecimal getCurrencyBalance(Currency currency) {
    return this.currencyBalance.get(currency.getCurrencyCode().toLowerCase());
  }

  public BigDecimal getCurrencyReserved(Currency currency) {
    return this.currencyReserved.get(currency.getCurrencyCode().toLowerCase());
  }

  public BigDecimal getCurrencyAvailable(Currency currency) {
    return this.currencyAvailable.get(currency.getCurrencyCode().toLowerCase());
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
    return "QuadrigaCxBalance{" + "currencyReserved=" + currencyReserved + ", currencyAvailable=" + currencyAvailable + ", currencyBalance="
        + currencyBalance + ", fee=" + fee + ", error='" + error + '\'' + '}';
  }
}
