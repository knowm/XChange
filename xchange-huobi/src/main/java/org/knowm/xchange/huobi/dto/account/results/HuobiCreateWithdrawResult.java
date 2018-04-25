package org.knowm.xchange.huobi.dto.account.results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.huobi.dto.HuobiResult;

public class HuobiCreateWithdrawResult extends HuobiResult<Long> {
  @JsonCreator
  public HuobiCreateWithdrawResult(
      @JsonProperty("status") String status,
      @JsonProperty("data") long data,
      @JsonProperty("err-code") String errCode,
      @JsonProperty("err-msg") String errMsg) {
    super(status, errCode, errMsg, data);
  }
}
