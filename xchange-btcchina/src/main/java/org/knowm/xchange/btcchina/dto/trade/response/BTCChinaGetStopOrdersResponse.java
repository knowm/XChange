package org.knowm.xchange.btcchina.dto.trade.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.btcchina.dto.BTCChinaError;
import org.knowm.xchange.btcchina.dto.BTCChinaResponse;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaStopOrdersObject;

/**
 * Response of {@code getStopOrders}.
 * 
 * @see <a href="http://btcchina.org/api-trade-documentation-en#getstoporders">Trade API(English)</a>
 * @see <a href="http://btcchina.org/api-trade-documentation-zh#getstoporders">Trade API(Chinese)</a>
 */
public class BTCChinaGetStopOrdersResponse extends BTCChinaResponse<BTCChinaStopOrdersObject> {

  public BTCChinaGetStopOrdersResponse(@JsonProperty("id") String id, @JsonProperty("result") BTCChinaStopOrdersObject result,
      @JsonProperty("error") BTCChinaError error) {

    super(id, result, error);
  }

}
