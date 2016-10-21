package org.knowm.xchange.btcchina.dto.trade.response;

import org.knowm.xchange.btcchina.dto.BTCChinaError;
import org.knowm.xchange.btcchina.dto.BTCChinaResponse;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaIcebergOrderObject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaGetIcebergOrderResponse extends BTCChinaResponse<BTCChinaIcebergOrderObject> {

  public BTCChinaGetIcebergOrderResponse(@JsonProperty("id") String id, @JsonProperty("result") BTCChinaIcebergOrderObject result,
      @JsonProperty("error") BTCChinaError error) {

    super(id, result, error);
  }

}
