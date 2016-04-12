package org.knowm.xchange.cryptsy.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptsy.dto.CryptsyGenericReturn;

/**
 * @author ObsessiveOrange
 */
public class CryptsyAccountInfoReturn extends CryptsyGenericReturn<CryptsyAccountInfo> {

  /**
   * Constructor
   * 
   * @param success True if successful
   * @param value The BTC-e account info
   * @param error Any error
   */
  @JsonCreator
  public CryptsyAccountInfoReturn(@JsonProperty("success") int success, @JsonProperty("return") CryptsyAccountInfo value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
