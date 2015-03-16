package com.xeiam.xchange.cryptotrade.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.currency.CurrencyPair;

public class CryptoTradePairs extends CryptoTradeBaseResponse {

  private Map<CurrencyPair, CryptoTradePair> pairs;

  public CryptoTradePairs(@JsonProperty("status") String status, @JsonProperty("error") String error, @JsonProperty("data") @JsonDeserialize(
      using = CryptoTradePairsDeserializer.class) Map<CurrencyPair, CryptoTradePair> pairs) {

    super(status, error);
    this.pairs = pairs;
  }

  public Map<CurrencyPair, CryptoTradePair> getPairs() {

    return pairs;
  }

  @Override
  public String toString() {

    return "CryptoTradePairs [pairs=" + getPairs() + "]";
  }

}
