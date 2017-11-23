package org.knowm.xchange.btcchina.dto.trade.response;

import org.knowm.xchange.btcchina.dto.BTCChinaError;
import org.knowm.xchange.btcchina.dto.BTCChinaResponse;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaStopOrderObject;

import com.fasterxml.jackson.annotation.JsonProperty;

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
