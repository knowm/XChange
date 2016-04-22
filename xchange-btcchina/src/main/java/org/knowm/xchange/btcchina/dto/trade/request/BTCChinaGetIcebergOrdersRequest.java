package org.knowm.xchange.btcchina.dto.trade.request;

import org.knowm.xchange.btcchina.BTCChinaExchange;
import org.knowm.xchange.btcchina.dto.BTCChinaRequest;

public class BTCChinaGetIcebergOrdersRequest extends BTCChinaRequest {

  private static final String METHOD_NAME = "getIcebergOrders";

  public BTCChinaGetIcebergOrdersRequest(Integer limit, Integer offset, String market) {

    this.method = METHOD_NAME;
    this.params = String.format("[%1$d,%2$d,\"%3$s\"]", limit == null ? 1000 : limit.intValue(), offset == null ? 0 : offset.intValue(),
        market == null ? BTCChinaExchange.DEFAULT_MARKET : market);
  }

}
