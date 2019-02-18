package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.CmcResult;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CmcCurrency;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CmcStatus;

import java.util.List;

public final class CmcCurrencyMapResponse
        extends CmcResult<List<CmcCurrency>> {

    public CmcCurrencyMapResponse(
              @JsonProperty("status") CmcStatus status,
              @JsonProperty("result") List<CmcCurrency> data){
        super(data, status);
  }
}
