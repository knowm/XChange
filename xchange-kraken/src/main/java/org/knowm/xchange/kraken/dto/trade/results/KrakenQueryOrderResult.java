package org.knowm.xchange.kraken.dto.trade.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.trade.KrakenOrder;

import java.util.Map;

public class KrakenQueryOrderResult extends KrakenResult<Map<String, KrakenOrder>> {

  /**
   * Constructor
   *
   * @param result
   * @param error
   */
  public KrakenQueryOrderResult(@JsonProperty("result") Map<String, KrakenOrder> result, @JsonProperty("error") String[] error) {

    super(result, error);
  }

}
