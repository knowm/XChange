package org.knowm.xchange.cryptsy.dto.account;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptsy.dto.CryptsyGenericReturn;

/**
 * @author ObsessiveOrange
 */
public class CryptsyDepositAddressReturn extends CryptsyGenericReturn<Map<String, String>> {

  /**
   * Constructor
   * 
   * @param success True if successful
   * @param value The BTC-e account info
   * @param error Any error
   */
  public CryptsyDepositAddressReturn(@JsonProperty("success") int success, @JsonProperty("return") Map<String, String> value,
      @JsonProperty("error") String error) {

    super(success, (value == null ? new HashMap<String, String>() : value), error);
  }
}
