package com.xeiam.xchange.btcchina.dto.trade.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.btcchina.dto.BTCChinaError;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaIcebergOrdersObject;

public class BTCChinaGetIcebergOrdersResponse extends BTCChinaResponse<BTCChinaIcebergOrdersObject> {

  public BTCChinaGetIcebergOrdersResponse(@JsonProperty("id") String id, @JsonProperty("result") BTCChinaIcebergOrdersObject result,
      @JsonProperty("error") BTCChinaError error) {

    super(id, result, error);
  }

}
