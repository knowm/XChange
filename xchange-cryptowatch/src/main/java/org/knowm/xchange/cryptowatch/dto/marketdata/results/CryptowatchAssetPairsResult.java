package org.knowm.xchange.cryptowatch.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchAssetPair;

import java.util.List;

public class CryptowatchAssetPairsResult extends CryptowatchResult<List<CryptowatchAssetPair>> {

  public CryptowatchAssetPairsResult(
      @JsonProperty("error") String error,
      @JsonProperty("result") List<CryptowatchAssetPair> result,
      @JsonProperty("allowance") Allowance allowance) {
    super(result, error, allowance);
  }
}
