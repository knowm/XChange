package org.knowm.xchange.coinmate.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinmate.dto.CoinmateBaseResponse;

public class CoinmateTransferDetail extends CoinmateBaseResponse<CoinmateTransferHistoryEntry> {

  public CoinmateTransferDetail(
      @JsonProperty("error") boolean error,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("data") CoinmateTransferHistoryEntry data) {

    super(error, errorMessage, data);
  }
}
