package org.knowm.xchange.cryptowatch.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchAsset;

import java.util.List;

public class CryptowatchAssetsResult extends CryptowatchResult<List<CryptowatchAsset>> {

  public CryptowatchAssetsResult(
      @JsonProperty("error") String error,
      @JsonProperty("result") List<CryptowatchAsset> result,
      @JsonProperty("allowance") Allowance allowance) {
    super(result, error, allowance);
  }
}
