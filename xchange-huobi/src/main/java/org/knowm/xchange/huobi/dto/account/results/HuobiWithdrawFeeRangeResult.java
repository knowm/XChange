package org.knowm.xchange.huobi.dto.account.results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.huobi.dto.HuobiResult;
import org.knowm.xchange.huobi.dto.account.HuobiWithdrawFeeRange;

public class HuobiWithdrawFeeRangeResult extends HuobiResult<HuobiWithdrawFeeRange> {
  @JsonCreator
  public HuobiWithdrawFeeRangeResult(
      @JsonProperty("status") String status,
      @JsonProperty("data") HuobiWithdrawFeeRange feeRange,
      @JsonProperty("err-code") String errCode,
      @JsonProperty("err-msg") String errMsg) {
    super(status, errCode, errMsg, feeRange);
  }
}
