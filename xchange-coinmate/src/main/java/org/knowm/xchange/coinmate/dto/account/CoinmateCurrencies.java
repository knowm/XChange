package org.knowm.xchange.coinmate.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.coinmate.dto.CoinmateBaseResponse;

public class CoinmateCurrencies extends CoinmateBaseResponse<List<CoinmateCurrencyInfo>> {

  public CoinmateCurrencies(
      @JsonProperty("error") boolean error,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("data") List<CoinmateCurrencyInfo> data) {
    super(error, errorMessage, data);
  }
}
