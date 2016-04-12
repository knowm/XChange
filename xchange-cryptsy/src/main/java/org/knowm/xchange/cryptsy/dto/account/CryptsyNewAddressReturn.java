package org.knowm.xchange.cryptsy.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptsy.dto.CryptsyGenericReturn;

/**
 * @author ObsessiveOrange
 */
public class CryptsyNewAddressReturn extends CryptsyGenericReturn<CryptsyAddress> {

  /**
   * Constructor
   * 
   * @param success True if successful
   * @param value The BTC-e account info
   * @param error Any error
   */
  public CryptsyNewAddressReturn(@JsonProperty("success") int success, @JsonProperty("return") CryptsyAddress value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
