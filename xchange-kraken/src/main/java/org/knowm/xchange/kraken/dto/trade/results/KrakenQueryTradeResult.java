package org.knowm.xchange.kraken.dto.trade.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.trade.KrakenTrade;

import java.util.Map;

public class KrakenQueryTradeResult extends KrakenResult<Map<String, KrakenTrade>> {

  /**
   * Constructor
   *
   * @param result
   * @param error
   */
  public KrakenQueryTradeResult(@JsonProperty("result") Map<String, KrakenTrade> result, @JsonProperty("error") String[] error) {

    super(result, error);
  }
}