package org.knowm.xchange.coinmate.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import org.knowm.xchange.coinmate.dto.CoinmateBaseResponse;

public class UnconfirmedDepositsResponse
    extends CoinmateBaseResponse<ArrayList<UnconfirmedDeposits>> {
  public UnconfirmedDepositsResponse(
      @JsonProperty("error") boolean error,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("data") ArrayList<UnconfirmedDeposits> data) {

    super(error, errorMessage, data);
  }
}
