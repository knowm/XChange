package org.knowm.xchange.bitz.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;

public class BitZCurrencyRateList {
  private final Map<String, BitZCurrencyRate> currencyRateMap;

  @JsonCreator
  public BitZCurrencyRateList(Map<String, BitZCurrencyRate> currencyRateMap) {
    this.currencyRateMap = currencyRateMap;
  }

  public Map<String, BitZCurrencyRate> getCurrencyRateMap() {
    return currencyRateMap;
  }
}
