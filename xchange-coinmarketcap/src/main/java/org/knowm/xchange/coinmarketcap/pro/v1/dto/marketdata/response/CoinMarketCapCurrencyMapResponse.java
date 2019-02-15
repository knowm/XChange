package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapCurrency;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapStatus;

import java.util.List;

public final class CoinMarketCapCurrencyMapResponse {

  private final List<CoinMarketCapCurrency> currencyData;
  private final CoinMarketCapStatus status;

    public CoinMarketCapCurrencyMapResponse(
            @JsonProperty("data") List<CoinMarketCapCurrency> currencyData,
            @JsonProperty("status") CoinMarketCapStatus status) {
        this.currencyData = currencyData;
        this.status = status;
  }

  public List<CoinMarketCapCurrency> getCurrencyData() {
    return currencyData;
  }

  public CoinMarketCapStatus getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "CoinMarketCapCurrencyInfoResponse{"
        + "currencyData="
        + currencyData
        + ", status="
        + status
        + '}';
  }
}
