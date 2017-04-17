package org.knowm.xchange.btce.v3.dto.trade;

import java.util.Map;

import org.knowm.xchange.btce.v3.dto.BTCEReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Return value from OrderInfo.
 *
 * @author Dyorgio Nascimento Date: 4/17/15 Time: 18:05 PM
 */
public class BTCEOrderInfoReturn extends BTCEReturn<Map<Long, BTCEOrderInfoResult>> {

  /**
   * Constructor
   *
   * @param success
   * @param value
   * @param error
   */
  public BTCEOrderInfoReturn(@JsonProperty("success") boolean success, @JsonProperty("return") Map<Long, BTCEOrderInfoResult> value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }

}
