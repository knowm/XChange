package org.knowm.xchange.cryptowatch.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchPrice;

public class CryptowatchPriceResult extends CryptowatchResult<CryptowatchPrice> {

  public CryptowatchPriceResult(
      @JsonProperty("error") String error,
      @JsonProperty("result") CryptowatchPrice result,
      @JsonProperty("allowance") Allowance allowance) {
    super(result, error, allowance);
  }
}
