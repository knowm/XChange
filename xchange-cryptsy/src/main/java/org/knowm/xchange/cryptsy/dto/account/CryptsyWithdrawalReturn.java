package org.knowm.xchange.cryptsy.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptsy.dto.CryptsyGenericReturn;

/**
 * @author ObsessiveOrange
 */
public class CryptsyWithdrawalReturn extends CryptsyGenericReturn<String> {

  /**
   * Constructor
   * 
   * @param success True if successful
   * @param value The BTC-e account info
   * @param error Any error
   */
  public CryptsyWithdrawalReturn(@JsonProperty("success") int success, @JsonProperty("return") String value, @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
