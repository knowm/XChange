package org.knowm.xchange.cryptowatch.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchSummary;

public class CryptowatchSummaryResult extends CryptowatchResult<CryptowatchSummary> {

  public CryptowatchSummaryResult(
      @JsonProperty("error") String error,
      @JsonProperty("result") CryptowatchSummary result,
      @JsonProperty("allowance") Allowance allowance) {
    super(result, error, allowance);
  }
}
