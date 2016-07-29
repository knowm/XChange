package org.knowm.xchange.cryptsy.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptsy.dto.CryptsyGenericReturn;

/**
 * @author ObsessiveOrange
 */
public class CryptsyCalculatedFeesReturn extends CryptsyGenericReturn<CryptsyCalculatedFees> {

  /**
   * Constructor
   * 
   * @param success
   * @param value
   * @param error
   */
  public CryptsyCalculatedFeesReturn(@JsonProperty("success") int success, @JsonProperty("return") CryptsyCalculatedFees value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
