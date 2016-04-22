package org.knowm.xchange.kraken.dto.marketdata.results;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.marketdata.KrakenDepth;

/**
 * @author Raphael Voellmy
 */
public class KrakenDepthResult extends KrakenResult<Map<String, KrakenDepth>> {

  /**
   * Constructor
   * 
   * @param error array of string error messages
   * @param result the returned depths
   */
  public KrakenDepthResult(@JsonProperty("error") String[] error, @JsonProperty("result") Map<String, KrakenDepth> result) {

    super(result, error);
  }
}
