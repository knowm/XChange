package org.knowm.xchange.cryptowatch.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchAsset;

public class CryptowatchAssetsResult extends CryptowatchResult<List<CryptowatchAsset>> {

  public CryptowatchAssetsResult(
      @JsonProperty("error") String error,
      @JsonProperty("result") List<CryptowatchAsset> result,
      @JsonProperty("allowance") Allowance allowance) {
    super(result, error, allowance);
  }
}
