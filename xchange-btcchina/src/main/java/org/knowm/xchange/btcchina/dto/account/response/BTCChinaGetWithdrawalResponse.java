package org.knowm.xchange.btcchina.dto.account.response;

import org.knowm.xchange.btcchina.dto.BTCChinaError;
import org.knowm.xchange.btcchina.dto.BTCChinaResponse;
import org.knowm.xchange.btcchina.dto.account.BTCChinaWithdrawalObject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaGetWithdrawalResponse extends BTCChinaResponse<BTCChinaWithdrawalObject> {

  public BTCChinaGetWithdrawalResponse(@JsonProperty("id") String id, @JsonProperty("result") BTCChinaWithdrawalObject result,
      @JsonProperty("error") BTCChinaError error) {

    super(id, result, error);
  }

}
