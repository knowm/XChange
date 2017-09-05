package org.knowm.xchange.kraken.dto.account.results;

import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.account.KrakenTradeVolume;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenTradeVolumeResult extends KrakenResult<KrakenTradeVolume> {

  /**
   * Constructor
   *
   * @param result
   * @param error
   */
  public KrakenTradeVolumeResult(@JsonProperty("result") KrakenTradeVolume result, @JsonProperty("error") String[] error) {

    super(result, error);
  }
}