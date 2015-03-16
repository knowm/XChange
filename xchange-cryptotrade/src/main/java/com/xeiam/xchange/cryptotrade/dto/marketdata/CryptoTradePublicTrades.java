package com.xeiam.xchange.cryptotrade.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeBaseResponse;

public class CryptoTradePublicTrades extends CryptoTradeBaseResponse {

  private final List<CryptoTradePublicTrade> publicTrades;

  public CryptoTradePublicTrades(@JsonProperty("status") String status, @JsonProperty("error") String error,
      @JsonProperty("data") List<CryptoTradePublicTrade> publicTrades) {

    super(status, error);
    this.publicTrades = publicTrades;
  }

  public List<CryptoTradePublicTrade> getPublicTrades() {

    return publicTrades;
  }

  @Override
  public String toString() {

    return "CryptoTradePublicTrades [publicTrades=" + publicTrades + "]";
  }
}
