package com.xeiam.xchange.btcchina.dto.trade.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.btcchina.dto.BTCChinaError;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;

public class BTCChinaIntegerResponse extends BTCChinaResponse<Integer> {

  /**
   * Constructor
   * 
   * @param id
   * @param result
   */
  public BTCChinaIntegerResponse(@JsonProperty("id") String id, @JsonProperty("result") Integer result, @JsonProperty("error") BTCChinaError error) {

    super(id, result, error);
  }

}
