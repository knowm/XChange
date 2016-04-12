package org.knowm.xchange.cryptsy.dto.marketdata;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptsy.dto.CryptsyGenericReturn;

/**
 * @author ObsessiveOrange
 */
public class CryptsyGetMarketsReturn extends CryptsyGenericReturn<List<CryptsyMarketData>> {

  /**
   * Constructor
   * 
   * @param success
   * @param value
   * @param error
   */
  public CryptsyGetMarketsReturn(@JsonProperty("success") int success, @JsonProperty("return") List<CryptsyMarketData> value,
      @JsonProperty("error") String error) {

    super(success, (value == null ? new ArrayList<CryptsyMarketData>() : value), error);
  }
}
