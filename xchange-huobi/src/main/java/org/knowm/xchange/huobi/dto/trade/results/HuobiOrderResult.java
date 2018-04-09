package org.knowm.xchange.huobi.dto.trade.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.huobi.dto.HuobiResult;

public class HuobiOrderResult extends HuobiResult<String> {

  public HuobiOrderResult(
      @JsonProperty("status") String status,
      @JsonProperty("data") String result,
      @JsonProperty("err-code") String errCode,
      @JsonProperty("err-msg") String errMsg) {
    super(status, errCode, errMsg, result);
  }
}
