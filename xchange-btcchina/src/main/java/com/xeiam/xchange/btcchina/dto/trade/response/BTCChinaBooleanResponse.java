package com.xeiam.xchange.btcchina.dto.trade.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.btcchina.dto.BTCChinaError;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;

/**
 * @author David Yam
 */
public class BTCChinaBooleanResponse extends BTCChinaResponse<Boolean> {

  /**
   * Constructor
   * 
   * @param id
   * @param result
   */
  public BTCChinaBooleanResponse(@JsonProperty("id") String id, @JsonProperty("result") Boolean result, @JsonProperty("error") BTCChinaError error) {

    super(id, result, error);
  }

}
