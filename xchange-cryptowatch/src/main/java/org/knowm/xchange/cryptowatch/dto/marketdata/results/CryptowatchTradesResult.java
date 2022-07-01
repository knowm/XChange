package org.knowm.xchange.cryptowatch.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchTrade;

public class CryptowatchTradesResult extends CryptowatchResult<List<CryptowatchTrade>> {

  public CryptowatchTradesResult(
      @JsonProperty("error") String error,
      @JsonProperty("result") List<CryptowatchTrade> result,
      @JsonProperty("allowance") Allowance allowance) {
    super(result, error, allowance);
  }
}
