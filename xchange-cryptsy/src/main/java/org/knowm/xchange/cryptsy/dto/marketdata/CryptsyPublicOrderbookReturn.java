package org.knowm.xchange.cryptsy.dto.marketdata;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptsy.dto.CryptsyGenericReturn;

public class CryptsyPublicOrderbookReturn extends CryptsyGenericReturn<Map<String, CryptsyPublicOrderbook>> {

  /**
   * Constructor
   * 
   * @param success
   * @param value
   * @param error
   */
  public CryptsyPublicOrderbookReturn(@JsonProperty("success") int success, @JsonProperty("return") Map<String, CryptsyPublicOrderbook> value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
