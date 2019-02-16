package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.CoinMarketCapResult;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapStatus;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapTicker;

import java.util.List;

public final class CoinMarketCapTickerListResponse
        extends CoinMarketCapResult<List<CoinMarketCapTicker>> {

  public CoinMarketCapTickerListResponse(
          @JsonProperty("status") CoinMarketCapStatus status,
          @JsonProperty("result") List<CoinMarketCapTicker> data) {

    super(data, status);
  }
}
