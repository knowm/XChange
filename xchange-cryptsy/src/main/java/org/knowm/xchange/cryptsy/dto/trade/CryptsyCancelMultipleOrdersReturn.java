package org.knowm.xchange.cryptsy.dto.trade;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptsy.dto.CryptsyGenericReturn;

/**
 * @author ObsessiveOrange
 */
public class CryptsyCancelMultipleOrdersReturn extends CryptsyGenericReturn<List<String>> {

  /**
   * Constructor
   * 
   * @param success
   * @param value
   * @param error
   */
  public CryptsyCancelMultipleOrdersReturn(@JsonProperty("success") int success, @JsonProperty("return") List<String> value,
      @JsonProperty("error") String error) {

    super(success, (value == null ? new ArrayList<String>() : value), error);
  }
}
