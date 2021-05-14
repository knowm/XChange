package org.knowm.xchange.bx.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Map;
import org.knowm.xchange.bx.dto.BxResult;
import org.knowm.xchange.bx.dto.marketdata.BxTicker;

public class BxTickerResult extends BxResult<Map<String, BxTicker>> {

  @JsonCreator
  public BxTickerResult(Map<String, BxTicker> result) {
    super(result, true, "");
  }
}
