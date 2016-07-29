package org.knowm.xchange.cryptsy.dto.marketdata;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptsy.dto.CryptsyGenericReturn;

public class CryptsyCurrencyPairsReturn extends CryptsyGenericReturn<HashMap<String, CryptsyMarketId>> {

  /**
   * Constructor
   * 
   * @param success
   * @param value
   * @param error
   */
  public CryptsyCurrencyPairsReturn(@JsonProperty("success") int success, @JsonProperty("return") HashMap<String, CryptsyMarketId> value,
      @JsonProperty("error") String error) {

    super(success, (value == null ? new HashMap<String, CryptsyMarketId>() : value), error);
  }
}
