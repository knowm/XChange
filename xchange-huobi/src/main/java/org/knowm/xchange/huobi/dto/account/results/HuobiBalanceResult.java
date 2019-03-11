package org.knowm.xchange.huobi.dto.account.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.huobi.dto.HuobiResult;
import org.knowm.xchange.huobi.dto.account.HuobiBalance;

public class HuobiBalanceResult extends HuobiResult<HuobiBalance> {

  public HuobiBalanceResult(
      @JsonProperty("status") String status,
      @JsonProperty("data") HuobiBalance result,
      @JsonProperty("err-code") String errCode,
      @JsonProperty("err-msg") String errMsg) {
    super(status, errCode, errMsg, result);
  }
}
