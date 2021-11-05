package org.knowm.xchange.huobi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;

public class HuobiCurrencyWrapper {

  private HuobiCurrency[] huobiCurrencies;
  private String currency;
  private String instStatus;

  public HuobiCurrencyWrapper(
      @JsonProperty("chains") HuobiCurrency[] huobiCurrencies,
      @JsonProperty("currency") String currency,
      @JsonProperty("instStatus") String instStatus) {
    this.huobiCurrencies = huobiCurrencies;
    this.currency = currency;
    this.instStatus = instStatus;
  }

  public HuobiCurrency[] getHuobiCurrencies() {
    return huobiCurrencies;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getInstStatus() {
    return instStatus;
  }

  @Override
  public String toString() {
    return "HuobiCurrencyWrapper{"
        + "huobiCurrencies="
        + Arrays.toString(huobiCurrencies)
        + ", currency='"
        + currency
        + '\''
        + ", instStatus='"
        + instStatus
        + '\''
        + '}';
  }
}
