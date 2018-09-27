package org.knowm.xchange.cobinhood.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CobinhoodCurrencies {
  private final List<CobinhoodCurrency> currencies;

  public CobinhoodCurrencies(@JsonProperty("currencies") List<CobinhoodCurrency> currencies) {
    this.currencies = currencies;
  }

  public List<CobinhoodCurrency> getCurrencies() {
    return currencies;
  }
}
