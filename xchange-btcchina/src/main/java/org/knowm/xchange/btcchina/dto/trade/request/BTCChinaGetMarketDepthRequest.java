package org.knowm.xchange.btcchina.dto.trade.request;

import org.knowm.xchange.btcchina.dto.BTCChinaRequest;

public class BTCChinaGetMarketDepthRequest extends BTCChinaRequest {

  private static final int DEFAULT_LIMIT = 10;

  private static final String METHOD_NAME = "getMarketDepth2";

  /**
   * Constructs request for getting the complete market depth.
   *
   * @param limit Limit number of orders returned. Default is 10 per side.
   * @param market Default to "BTCCNY". [ BTCCNY | LTCCNY | LTCBTC ].
   */
  public BTCChinaGetMarketDepthRequest(Integer limit, String market) {

    this.method = METHOD_NAME;

    if (limit == null && market == null) {
      this.params = "[]";
    } else if (market == null) {
      this.params = String.format("[%d]", limit);
    } else {
      this.params = String.format("[%d,\"%s\"]", limit == null ? DEFAULT_LIMIT : limit, market);
    }
  }

}
