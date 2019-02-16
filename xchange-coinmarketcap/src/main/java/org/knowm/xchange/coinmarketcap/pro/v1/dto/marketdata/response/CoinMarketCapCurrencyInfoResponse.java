package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.CoinMarketCapResult;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapCurrencyInfo;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapStatus;

import java.util.Map;

public final class CoinMarketCapCurrencyInfoResponse
        extends CoinMarketCapResult<Map<String, CoinMarketCapCurrencyInfo>> {

  public CoinMarketCapCurrencyInfoResponse(
          @JsonProperty("status") CoinMarketCapStatus status,
          @JsonProperty("result") Map<String, CoinMarketCapCurrencyInfo> data) {
    super(data, status);
  }

}
