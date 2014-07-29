package com.xeiam.xchange.kraken.dto.account.results;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.kraken.dto.KrakenResult;
import com.xeiam.xchange.kraken.dto.account.KrakenLedger;

public class KrakenQueryLedgerResult extends KrakenResult<Map<String, KrakenLedger>> {

  /**
   * Constructor
   * 
   * @param result
   * @param error
   */
  public KrakenQueryLedgerResult(@JsonProperty("result") Map<String, KrakenLedger> result, @JsonProperty("error") String[] error) {

    super(result, error);
  }
}
