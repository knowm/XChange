package org.knowm.xchange.btcchina.dto.account.response;

import org.knowm.xchange.btcchina.dto.BTCChinaError;
import org.knowm.xchange.btcchina.dto.BTCChinaResponse;
import org.knowm.xchange.btcchina.dto.account.BTCChinaWithdrawalsObject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaGetWithdrawalsResponse extends BTCChinaResponse<BTCChinaWithdrawalsObject> {

  public BTCChinaGetWithdrawalsResponse(@JsonProperty("id") String id, @JsonProperty("result") BTCChinaWithdrawalsObject result,
      @JsonProperty("error") BTCChinaError error) {

    super(id, result, error);
  }

}
