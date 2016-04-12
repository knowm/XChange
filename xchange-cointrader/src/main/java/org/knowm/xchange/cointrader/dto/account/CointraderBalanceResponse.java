package org.knowm.xchange.cointrader.dto.account;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cointrader.dto.CointraderBaseResponse;

public class CointraderBalanceResponse extends CointraderBaseResponse<Map<String, CointraderBalance>> {
  protected CointraderBalanceResponse(@JsonProperty("success") Boolean status, @JsonProperty("message") String message,
      @JsonProperty("data") Map<String, CointraderBalance> data) {
    super(status, message, data);
  }
}
