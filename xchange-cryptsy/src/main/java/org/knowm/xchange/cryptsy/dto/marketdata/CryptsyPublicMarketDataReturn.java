package org.knowm.xchange.cryptsy.dto.marketdata;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptsy.dto.CryptsyGenericReturn;

public class CryptsyPublicMarketDataReturn extends CryptsyGenericReturn<Map<String, CryptsyPublicMarketData>> {

  public CryptsyPublicMarketDataReturn(@JsonProperty("success") int success,
      @JsonProperty("return") Map<String, Map<String, CryptsyPublicMarketData>> value, @JsonProperty("error") String error) {

    super(success, value.get("markets"), error);
  }
}
