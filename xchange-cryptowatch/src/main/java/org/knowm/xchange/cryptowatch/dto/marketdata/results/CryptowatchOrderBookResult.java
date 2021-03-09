package org.knowm.xchange.cryptowatch.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchOrderBook;

public class CryptowatchOrderBookResult extends CryptowatchResult<CryptowatchOrderBook> {

  public CryptowatchOrderBookResult(
      @JsonProperty("error") String error,
      @JsonProperty("result") CryptowatchOrderBook result,
      @JsonProperty("allowance") Allowance allowance) {
    super(result, error, allowance);
  }
}
