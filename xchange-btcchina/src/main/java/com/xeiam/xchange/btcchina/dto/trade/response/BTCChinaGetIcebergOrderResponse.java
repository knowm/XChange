package com.xeiam.xchange.btcchina.dto.trade.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.btcchina.dto.BTCChinaError;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaIcebergOrderObject;

public class BTCChinaGetIcebergOrderResponse extends BTCChinaResponse<BTCChinaIcebergOrderObject> {

  public BTCChinaGetIcebergOrderResponse(@JsonProperty("id") String id, @JsonProperty("result") BTCChinaIcebergOrderObject result,
      @JsonProperty("error") BTCChinaError error) {

    super(id, result, error);
  }

}
