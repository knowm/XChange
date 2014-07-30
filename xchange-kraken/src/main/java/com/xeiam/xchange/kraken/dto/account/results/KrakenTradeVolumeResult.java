package com.xeiam.xchange.kraken.dto.account.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.kraken.dto.KrakenResult;
import com.xeiam.xchange.kraken.dto.account.KrakenTradeVolume;

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