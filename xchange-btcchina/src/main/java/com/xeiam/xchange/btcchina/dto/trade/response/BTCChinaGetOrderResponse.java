package com.xeiam.xchange.btcchina.dto.trade.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.btcchina.dto.BTCChinaError;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaOrderObject;

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
