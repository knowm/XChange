package org.knowm.xchange.cryptsy.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptsy.dto.CryptsyGenericReturn;

/**
 * @author ObsessiveOrange
 */
public class CryptsyOrderBookReturn extends CryptsyGenericReturn<CryptsyOrderBook> {

  /**
   * Constructor
   * 
   * @param success
   * @param value
   * @param error
   */
  public CryptsyOrderBookReturn(@JsonProperty("success") int success, @JsonProperty("return") CryptsyOrderBook value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
