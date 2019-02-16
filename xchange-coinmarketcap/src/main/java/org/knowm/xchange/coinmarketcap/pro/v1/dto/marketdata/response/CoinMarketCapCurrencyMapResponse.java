package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.CoinMarketCapResult;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapCurrency;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapStatus;

import java.util.List;

public final class CoinMarketCapCurrencyMapResponse
        extends CoinMarketCapResult <List<CoinMarketCapCurrency>> {

    public CoinMarketCapCurrencyMapResponse(
              @JsonProperty("status") CoinMarketCapStatus status,
              @JsonProperty("result") List<CoinMarketCapCurrency> data){
        super(data, status);
  }
}
