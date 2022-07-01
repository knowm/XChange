package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.CmcResult;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CmcStatus;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CmcTicker;

public final class CmcTickerResponse extends CmcResult<Map<String, CmcTicker>> {

  public CmcTickerResponse(
      @JsonProperty("status") CmcStatus status,
      @JsonProperty("result") Map<String, CmcTicker> data) {

    super(data, status);
  }
}
