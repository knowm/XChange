package org.knowm.xchange.btcchina.dto.trade.response;

import org.knowm.xchange.btcchina.dto.BTCChinaError;
import org.knowm.xchange.btcchina.dto.BTCChinaResponse;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaIcebergOrdersObject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaGetIcebergOrdersResponse extends BTCChinaResponse<BTCChinaIcebergOrdersObject> {

  public BTCChinaGetIcebergOrdersResponse(@JsonProperty("id") String id, @JsonProperty("result") BTCChinaIcebergOrdersObject result,
      @JsonProperty("error") BTCChinaError error) {

    super(id, result, error);
  }

}
