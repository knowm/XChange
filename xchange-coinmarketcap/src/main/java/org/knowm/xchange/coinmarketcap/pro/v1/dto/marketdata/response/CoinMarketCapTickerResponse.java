package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.CoinMarketCapResult;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapStatus;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapTicker;

import java.util.Map;

public final class CoinMarketCapTickerResponse
          extends CoinMarketCapResult<Map<String, CoinMarketCapTicker>> {

    public CoinMarketCapTickerResponse(
            @JsonProperty("status") CoinMarketCapStatus status,
            @JsonProperty("result") Map<String, CoinMarketCapTicker> data) {

      super(data, status);
    }
}

