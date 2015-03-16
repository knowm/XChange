package com.xeiam.xchange.btcchina.dto.account.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.btcchina.dto.BTCChinaError;
import com.xeiam.xchange.btcchina.dto.BTCChinaID;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;

/**
 * @author David Yam
 */
public class BTCChinaRequestWithdrawalResponse extends BTCChinaResponse<BTCChinaID> {

  /**
   * Constructor
   * 
   * @param id
   * @param result
   */
  public BTCChinaRequestWithdrawalResponse(@JsonProperty("id") String id, @JsonProperty("result") BTCChinaID result,
      @JsonProperty("error") BTCChinaError error) {

    super(id, result, error);
  }

}
