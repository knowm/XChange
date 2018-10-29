package org.knowm.xchange.bitz.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;

public class BitZCurrencyCoinRateList {
  private final Map<String, BitZCurrencyCoinRate> currencyCoinRateMap;

  @JsonCreator
  public BitZCurrencyCoinRateList(Map<String, BitZCurrencyCoinRate> currencyCoinRateMap) {
    this.currencyCoinRateMap = currencyCoinRateMap;
  }

  public Map<String, BitZCurrencyCoinRate> getCurrencyCoinRateMap() {
    return currencyCoinRateMap;
  }
}
