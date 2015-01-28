package com.xeiam.xchange.btcchina.dto.trade.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.btcchina.dto.BTCChinaError;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaMarketDepthObject;

public class BTCChinaGetMarketDepthResponse extends BTCChinaResponse<BTCChinaMarketDepthObject> {

  public BTCChinaGetMarketDepthResponse(@JsonProperty("id") String id, @JsonProperty("result") BTCChinaMarketDepthObject result,
      @JsonProperty("error") BTCChinaError error) {

    super(id, result, error);
  }

}
