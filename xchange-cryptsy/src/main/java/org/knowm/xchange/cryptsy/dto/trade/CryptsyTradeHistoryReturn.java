package org.knowm.xchange.cryptsy.dto.trade;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptsy.dto.CryptsyGenericReturn;

/**
 * @author ObsessiveOrange
 */
public class CryptsyTradeHistoryReturn extends CryptsyGenericReturn<List<CryptsyTradeHistory>> {

  /**
   * Constructor
   * 
   * @param success
   * @param value
   * @param error
   */
  public CryptsyTradeHistoryReturn(@JsonProperty("success") int success, @JsonProperty("return") List<CryptsyTradeHistory> value,
      @JsonProperty("error") String error) {

    super(success, (value == null ? new ArrayList<CryptsyTradeHistory>() : value), error);
  }
}
