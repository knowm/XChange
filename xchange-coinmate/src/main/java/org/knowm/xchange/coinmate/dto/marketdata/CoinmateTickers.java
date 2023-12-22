package org.knowm.xchange.coinmate.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.coinmate.dto.CoinmateBaseResponse;

public class CoinmateTickers extends CoinmateBaseResponse<Map<String, CoinmateTickerData>> {

  public CoinmateTickers(
      @JsonProperty("error") boolean error,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("data") Map<String, CoinmateTickerData> data) {

    super(error, errorMessage, data);
  }
}
