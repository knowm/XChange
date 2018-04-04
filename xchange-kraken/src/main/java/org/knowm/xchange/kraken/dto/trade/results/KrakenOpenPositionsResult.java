package org.knowm.xchange.kraken.dto.trade.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.trade.KrakenOpenPosition;

public class KrakenOpenPositionsResult extends KrakenResult<Map<String, KrakenOpenPosition>> {

  /**
   * Constructor
   *
   * @param result
   * @param error
   */
  public KrakenOpenPositionsResult(
      @JsonProperty("result") Map<String, KrakenOpenPosition> result,
      @JsonProperty("error") String[] error) {

    super(result, error);
  }
}
