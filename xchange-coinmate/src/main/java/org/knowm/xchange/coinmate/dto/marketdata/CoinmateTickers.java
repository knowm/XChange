package org.knowm.xchange.coinmate.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinmate.dto.CoinmateBaseResponse;

import java.util.List;
import java.util.Map;

public class CoinmateTickers extends CoinmateBaseResponse<Map<String, CoinmateTickerData>> {

  public CoinmateTickers(
      @JsonProperty("error") boolean error,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("data") Map<String, CoinmateTickerData> data) {

    super(error, errorMessage, data);
  }
}
