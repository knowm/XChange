package org.knowm.xchange.kraken.dto.trade.results;

import java.util.Map;

import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.trade.KrakenTrade;

import com.fasterxml.jackson.annotation.JsonProperty;

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