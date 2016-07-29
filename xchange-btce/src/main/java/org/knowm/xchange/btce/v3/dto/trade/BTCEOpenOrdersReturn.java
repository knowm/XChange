package org.knowm.xchange.btce.v3.dto.trade;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.btce.v3.dto.BTCEReturn;

/**
 * @author Matija Mazi
 */
public class BTCEOpenOrdersReturn extends BTCEReturn<Map<Long, BTCEOrder>> {

  /**
   * Constructor
   * 
   * @param success
   * @param value
   * @param error
   */
  public BTCEOpenOrdersReturn(@JsonProperty("success") boolean success, @JsonProperty("return") Map<Long, BTCEOrder> value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
