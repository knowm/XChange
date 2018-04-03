package org.knowm.xchange.huobi.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.huobi.dto.HuobiResult;
import org.knowm.xchange.huobi.dto.marketdata.HuobiAssetPair;

public class HuobiAssetPairsResult extends HuobiResult<HuobiAssetPair[]> {

  public HuobiAssetPairsResult(
      @JsonProperty("status") String status,
      @JsonProperty("data") HuobiAssetPair[] result,
      @JsonProperty("err-code") String errCode,
      @JsonProperty("err-msg") String errMsg) {
    super(status, errCode, errMsg, result);
  }
}
