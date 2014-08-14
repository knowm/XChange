package com.xeiam.xchange.kraken.dto.trade.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.kraken.dto.KrakenResult;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderResponse;

public class KrakenOrderResult extends KrakenResult<KrakenOrderResponse> {

  /**
   * Constructor
   * 
   * @param result
   * @param error
   */
  public KrakenOrderResult(@JsonProperty("result") KrakenOrderResponse result, @JsonProperty("error") String[] error) {

    super(result, error);
  }

}
