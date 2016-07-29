package org.knowm.xchange.cryptsy.dto.account;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptsy.dto.CryptsyGenericReturn;

/**
 * @author ObsessiveOrange
 */
public class CryptsyTxnHistoryReturn extends CryptsyGenericReturn<List<CryptsyTxn>> {

  /**
   * Constructor
   * 
   * @param success True if successful
   * @param value The BTC-e account info
   * @param error Any error
   */
  public CryptsyTxnHistoryReturn(@JsonProperty("success") int success, @JsonProperty("return") List<CryptsyTxn> value,
      @JsonProperty("error") String error) {

    super(success, (value == null ? new ArrayList<CryptsyTxn>() : value), error);
  }
}
