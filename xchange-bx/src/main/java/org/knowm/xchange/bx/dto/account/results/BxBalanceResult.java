package org.knowm.xchange.bx.dto.account.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.bx.dto.BxResult;
import org.knowm.xchange.bx.dto.account.BxBalance;

public class BxBalanceResult extends BxResult<Map<String, BxBalance>> {

  public BxBalanceResult(
      @JsonProperty("balance") Map<String, BxBalance> result,
      @JsonProperty("success") boolean success,
      @JsonProperty("error") String error) {
    super(result, success, error);
  }
}
