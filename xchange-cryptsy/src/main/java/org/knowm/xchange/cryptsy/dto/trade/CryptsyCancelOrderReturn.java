package org.knowm.xchange.cryptsy.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptsy.dto.CryptsyGenericReturn;

/**
 * @author ObsessiveOrange
 */
public class CryptsyCancelOrderReturn extends CryptsyGenericReturn<String> {

  /**
   * Constructor
   * 
   * @param success
   * @param value
   * @param error
   */
  public CryptsyCancelOrderReturn(@JsonProperty("success") int success, @JsonProperty("return") String value, @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
