package org.knowm.xchange.bitz.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.knowm.xchange.currency.Currency;

import java.math.BigDecimal;
import java.util.Map;

public class BitZCurrencyCoinRateList {
  private final Map<Currency, Map<Currency, BigDecimal>> currencyCoinRateMap;

  @JsonCreator
  public BitZCurrencyCoinRateList(Map<Currency, Map<Currency, BigDecimal>> currencyCoinRateMap) {
    this.currencyCoinRateMap = currencyCoinRateMap;
  }

  public Map<Currency, Map<Currency, BigDecimal>> getCurrencyCoinRateMap() {
    return currencyCoinRateMap;
  }
}
