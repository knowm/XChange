package org.knowm.xchange.huobi.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.huobi.dto.HuobiResult;
import org.knowm.xchange.huobi.dto.marketdata.HuobiAsset;

public class HuobiAssetsResult extends HuobiResult<HuobiAsset[]> {

  public HuobiAssetsResult(
      @JsonProperty("status") String status,
      @JsonProperty("data") HuobiAsset[] result,
      @JsonProperty("err-code") String errCode,
      @JsonProperty("err-msg") String errMsg) {
    super(status, errCode, errMsg, result);
  }
}
