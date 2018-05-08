package org.knowm.xchange.coinbase.v2.dto.marketdata;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

public class CoinbaseExchangeRateData {

  private CoinbaseExchangeRates data;

  public CoinbaseExchangeRates getData() {
    return data;
  }

  public void setData(CoinbaseExchangeRates data) {
    this.data = data;
  }

  public static class CoinbaseExchangeRates {

    private String currency;
    private Map<String, BigDecimal> rates;

    public String getCurrency() {
      return currency;
    }

    public Map<String, BigDecimal> getRates() {
      return Collections.unmodifiableMap(rates);
    }
  }
}
