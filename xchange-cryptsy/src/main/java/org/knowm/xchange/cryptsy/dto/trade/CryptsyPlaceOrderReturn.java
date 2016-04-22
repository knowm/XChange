package org.knowm.xchange.cryptsy.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptsy.dto.CryptsyGenericReturn;

/**
 * @author ObsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptsyPlaceOrderReturn extends CryptsyGenericReturn<Integer> {

  /**
   * Constructor
   * 
   * @param success
   * @param value
   * @param error
   */
  public CryptsyPlaceOrderReturn(@JsonProperty("success") int success, @JsonProperty("orderid") int value, @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
