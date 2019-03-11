package org.knowm.xchange.kraken.dto.account.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.account.KrakenLedger;

public class KrakenQueryLedgerResult extends KrakenResult<Map<String, KrakenLedger>> {

  /**
   * Constructor
   *
   * @param result
   * @param error
   */
  public KrakenQueryLedgerResult(
      @JsonProperty("result") Map<String, KrakenLedger> result,
      @JsonProperty("error") String[] error) {

    super(result, error);
  }
}
