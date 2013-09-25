package org.xchange.kraken.dto.account;

import java.math.BigDecimal;
import java.util.Map;

import org.xchange.kraken.dto.KrakenResult;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * 
 * @author Benedikt
 *
 */
public class KrakenBalanceResult extends KrakenResult<Map<String,BigDecimal>> {
  /**
   * 
   * @param error List of errors
   * @param result Recent trades
   */
  public KrakenBalanceResult(@JsonProperty("error") String[] error, @JsonProperty("result") Map<String,BigDecimal> result) {

    super(result, error);
  }

}