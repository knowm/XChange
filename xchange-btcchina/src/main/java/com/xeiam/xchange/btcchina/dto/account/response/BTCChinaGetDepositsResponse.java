package com.xeiam.xchange.btcchina.dto.account.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.btcchina.dto.BTCChinaError;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.dto.account.BTCChinaDepositObject;

public class BTCChinaGetDepositsResponse extends BTCChinaResponse<BTCChinaDepositObject> {

  /**
   * @param id
   * @param result
   * @param error
   */
  public BTCChinaGetDepositsResponse(@JsonProperty("id") String id, @JsonProperty("result") BTCChinaDepositObject result,
      @JsonProperty("error") BTCChinaError error) {

    super(id, result, error);
  }

}
