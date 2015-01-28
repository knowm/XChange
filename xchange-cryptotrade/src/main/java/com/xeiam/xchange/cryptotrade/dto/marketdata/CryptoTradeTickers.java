package com.xeiam.xchange.cryptotrade.dto.marketdata;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeBaseResponse;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeTickersDeserializer;
import com.xeiam.xchange.currency.CurrencyPair;

public class CryptoTradeTickers extends CryptoTradeBaseResponse {

  private final Map<CurrencyPair, CryptoTradeTicker> tickers;

  public CryptoTradeTickers(@JsonProperty("status") String status, @JsonProperty("error") String error, @JsonProperty("data") @JsonDeserialize(
      using = CryptoTradeTickersDeserializer.class) Map<CurrencyPair, CryptoTradeTicker> tickers) {

    super(status, error);
    this.tickers = tickers;
  }

  public Map<CurrencyPair, CryptoTradeTicker> getTickers() {

    return tickers;
  }

  @Override
  public String toString() {

    return "CryptoTradeTickers [tickers=" + getTickers() + "]";
  }

}