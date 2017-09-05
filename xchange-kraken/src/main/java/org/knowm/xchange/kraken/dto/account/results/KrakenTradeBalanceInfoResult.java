package org.knowm.xchange.kraken.dto.account.results;

import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.account.KrakenTradeBalanceInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenTradeBalanceInfoResult extends KrakenResult<KrakenTradeBalanceInfo> {

  /**
   * Constructor
   *
   * @param result
   * @param error
   */
  public KrakenTradeBalanceInfoResult(@JsonProperty("result") KrakenTradeBalanceInfo result, @JsonProperty("error") String[] error) {

    super(result, error);
  }

}
