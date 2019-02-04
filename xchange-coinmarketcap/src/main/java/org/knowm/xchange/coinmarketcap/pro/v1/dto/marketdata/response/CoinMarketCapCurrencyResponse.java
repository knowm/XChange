package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapStatus;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.wrapper.CoinMarketCapCurrencyData;

public final class CoinMarketCapCurrencyResponse {

  private final CoinMarketCapCurrencyData currencyData;
  private final CoinMarketCapStatus status;

  public CoinMarketCapCurrencyResponse(
      @JsonProperty("data")
          @JsonDeserialize(
              using = CoinMarketCapCurrencyData.CoinMarketCapCurrencyDataDeserializer.class)
          CoinMarketCapCurrencyData currencyData,
      @JsonProperty("status") CoinMarketCapStatus status) {
    this.currencyData = currencyData;
    this.status = status;
  }

  public CoinMarketCapCurrencyData getCurrencyData() {
    return currencyData;
  }

  public CoinMarketCapStatus getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "CoinMarketCapCurrencyResponse{"
        + "currencyData="
        + currencyData
        + ", status="
        + status
        + '}';
  }
}
