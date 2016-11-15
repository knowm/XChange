package org.knowm.xchange.btce.v3.dto.trade;

import java.util.Map;

import org.knowm.xchange.btce.v3.dto.BTCEReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Return value from TradeHistory, including overal status and map of transaction ids to TransHistoryResult.
 *
 * @author Peter N. Steinmetz Date: 3/30/15 Time: 3:19 PM
 */
public class BTCETransHistoryReturn extends BTCEReturn<Map<Long, BTCETransHistoryResult>> {

  /**
   * Constructor
   *
   * @param success
   * @param value
   * @param error
   */
  public BTCETransHistoryReturn(@JsonProperty("success") boolean success, @JsonProperty("return") Map<Long, BTCETransHistoryResult> value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }

}
