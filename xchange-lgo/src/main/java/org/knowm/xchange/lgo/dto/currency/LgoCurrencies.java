package org.knowm.xchange.lgo.dto.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public final class LgoCurrencies {

  private final List<LgoCurrency> currencies;

  public LgoCurrencies(@JsonProperty("currencies") List<LgoCurrency> currencies) {
    this.currencies = currencies;
  }

  public List<LgoCurrency> getCurrencies() {
    return currencies;
  }
}
