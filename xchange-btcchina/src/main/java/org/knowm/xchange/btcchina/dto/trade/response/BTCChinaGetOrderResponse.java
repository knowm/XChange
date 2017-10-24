package org.knowm.xchange.btcchina.dto.trade.response;

import org.knowm.xchange.btcchina.dto.BTCChinaError;
import org.knowm.xchange.btcchina.dto.BTCChinaResponse;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaOrderObject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaGetOrderResponse extends BTCChinaResponse<BTCChinaOrderObject> {

  /**
   * Constructor
   *
   * @param id
   * @param result
   * @param error
   */
  public BTCChinaGetOrderResponse(@JsonProperty("id") String id, @JsonProperty("result") BTCChinaOrderObject result,
      @JsonProperty("error") BTCChinaError error) {

    super(id, result, error);
  }

}
