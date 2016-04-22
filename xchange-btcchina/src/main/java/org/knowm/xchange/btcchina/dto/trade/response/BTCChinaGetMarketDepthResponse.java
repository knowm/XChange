package org.knowm.xchange.btcchina.dto.trade.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.btcchina.dto.BTCChinaError;
import org.knowm.xchange.btcchina.dto.BTCChinaResponse;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaMarketDepthObject;

public class BTCChinaGetMarketDepthResponse extends BTCChinaResponse<BTCChinaMarketDepthObject> {

  public BTCChinaGetMarketDepthResponse(@JsonProperty("id") String id, @JsonProperty("result") BTCChinaMarketDepthObject result,
      @JsonProperty("error") BTCChinaError error) {

    super(id, result, error);
  }

}
