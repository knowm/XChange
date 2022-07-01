package org.knowm.xchange.kraken.dto.account.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.account.KrakenWebsocketToken;

public class KrakenWebsocketTokenResult extends KrakenResult<KrakenWebsocketToken> {

  /**
   * Constructor
   *
   * @param result
   * @param error
   */
  public KrakenWebsocketTokenResult(
      @JsonProperty("result") KrakenWebsocketToken result, @JsonProperty("error") String[] error) {

    super(result, error);
  }
}
