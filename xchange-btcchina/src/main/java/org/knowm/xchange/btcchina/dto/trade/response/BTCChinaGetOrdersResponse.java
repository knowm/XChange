package org.knowm.xchange.btcchina.dto.trade.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.btcchina.dto.BTCChinaError;
import org.knowm.xchange.btcchina.dto.BTCChinaResponse;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaOrders;

/**
 * @author David Yam
 */
public class BTCChinaGetOrdersResponse extends BTCChinaResponse<BTCChinaOrders> {

  /**
   * Constructor
   * 
   * @param id
   * @param result
   */
  public BTCChinaGetOrdersResponse(@JsonProperty("id") String id, @JsonProperty("result") BTCChinaOrders result,
      @JsonProperty("error") BTCChinaError error) {

    super(id, result, error);
  }

}
