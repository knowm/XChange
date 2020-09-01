package org.knowm.xchange.huobi.dto.trade.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.huobi.dto.HuobiResult;
import org.knowm.xchange.huobi.dto.trade.HuobiMatchResult;

public class HuobiMatchesResult extends HuobiResult<HuobiMatchResult[]> {

  public HuobiMatchesResult(
      @JsonProperty("status") String status,
      @JsonProperty("data") HuobiMatchResult[] result,
      @JsonProperty("err-code") String errCode,
      @JsonProperty("err-msg") String errMsg) {
    super(status, errCode, errMsg, result);
  }
}
