package org.knowm.xchange.bx.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Map;
import org.knowm.xchange.bx.dto.BxResult;
import org.knowm.xchange.bx.dto.marketdata.BxAssetPair;

public class BxAssetPairsResult extends BxResult<Map<String, BxAssetPair>> {

  @JsonCreator
  public BxAssetPairsResult(Map<String, BxAssetPair> result) {
    super(result, true, "");
  }
}
