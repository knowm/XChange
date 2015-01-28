package com.xeiam.xchange.btcchina.dto.trade.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.btcchina.dto.BTCChinaError;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaStopOrderObject;

/**
 * Response of {@code getStopOrder}.
 * 
 * @see <a href="http://btcchina.org/api-trade-documentation-en#getstoporder">Trade API(English)</a>
 * @see <a href="http://btcchina.org/api-trade-documentation-zh#getstoporder">Trade API(Chinese)</a>
 */
public class BTCChinaGetStopOrderResponse extends BTCChinaResponse<BTCChinaStopOrderObject> {

  public BTCChinaGetStopOrderResponse(@JsonProperty("id") String id, @JsonProperty("result") BTCChinaStopOrderObject result,
      @JsonProperty("error") BTCChinaError error) {

    super(id, result, error);
  }

}
