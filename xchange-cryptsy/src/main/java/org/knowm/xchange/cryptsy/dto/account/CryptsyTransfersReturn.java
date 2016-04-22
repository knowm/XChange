package org.knowm.xchange.cryptsy.dto.account;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptsy.dto.CryptsyGenericReturn;

/**
 * @author ObsessiveOrange
 */
public class CryptsyTransfersReturn extends CryptsyGenericReturn<List<CryptsyTransfers>> {

  /**
   * Constructor
   * 
   * @param success True if successful
   * @param value The BTC-e account info
   * @param error Any error
   */
  public CryptsyTransfersReturn(@JsonProperty("success") int success, @JsonProperty("return") List<CryptsyTransfers> value,
      @JsonProperty("error") String error) {

    super(success, (value == null ? new ArrayList<CryptsyTransfers>() : value), error);
  }
}
