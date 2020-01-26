package org.knowm.xchange.cryptowatch.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchOHLCs;

public class CryptowatchOHLCResult extends CryptowatchResult<CryptowatchOHLCs> {

  /** @param result The OHLC data */
  public CryptowatchOHLCResult(
      @JsonProperty("error") String error,
      @JsonProperty("result") CryptowatchOHLCs result,
      @JsonProperty("allowance") Allowance allowance) {

    super(result, error, allowance);
  }
}
