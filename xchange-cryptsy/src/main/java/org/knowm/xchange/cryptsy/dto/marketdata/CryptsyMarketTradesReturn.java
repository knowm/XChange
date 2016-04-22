package org.knowm.xchange.cryptsy.dto.marketdata;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptsy.dto.CryptsyGenericReturn;
import org.knowm.xchange.cryptsy.dto.CryptsyOrder;

/**
 * @author ObsessiveOrange
 */
public class CryptsyMarketTradesReturn extends CryptsyGenericReturn<List<CryptsyOrder>> {

  /**
   * Constructor
   * 
   * @param success
   * @param value
   * @param error
   */
  public CryptsyMarketTradesReturn(@JsonProperty("success") int success, @JsonProperty("return") List<CryptsyOrder> value,
      @JsonProperty("error") String error) {

    super(success, (value == null ? new ArrayList<CryptsyOrder>() : value), error);
  }
}
