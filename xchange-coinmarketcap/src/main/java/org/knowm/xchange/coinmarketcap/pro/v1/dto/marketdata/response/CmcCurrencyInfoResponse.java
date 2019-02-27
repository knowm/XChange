package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.CmcResult;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CmcCurrencyInfo;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CmcStatus;

public final class CmcCurrencyInfoResponse extends CmcResult<Map<String, CmcCurrencyInfo>> {

  public CmcCurrencyInfoResponse(
      @JsonProperty("status") CmcStatus status,
      @JsonProperty("result") Map<String, CmcCurrencyInfo> data) {
    super(data, status);
  }
}
