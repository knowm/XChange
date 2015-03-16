package com.xeiam.xchange.btcchina.dto.account.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.btcchina.dto.BTCChinaError;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.dto.account.BTCChinaAccountInfo;

/**
 * @author David Yam
 */
public class BTCChinaGetAccountInfoResponse extends BTCChinaResponse<BTCChinaAccountInfo> {

  /**
   * Constructor
   * 
   * @param id
   * @param result
   */
  public BTCChinaGetAccountInfoResponse(@JsonProperty("id") String id, @JsonProperty("result") BTCChinaAccountInfo result,
      @JsonProperty("error") BTCChinaError error) {

    super(id, result, error);
  }

}
