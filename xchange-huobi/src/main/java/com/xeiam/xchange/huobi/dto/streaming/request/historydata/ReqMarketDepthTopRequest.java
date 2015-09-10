package com.xeiam.xchange.huobi.dto.streaming.request.historydata;

import com.xeiam.xchange.huobi.dto.streaming.request.AbstractSymbolIdRequest;

/**
 * Request of top market-depth.
 */
public class ReqMarketDepthTopRequest extends AbstractSymbolIdRequest {

  public ReqMarketDepthTopRequest(int version, String symbolId) {
    super(version, "reqMarketDepthTop", symbolId);
  }

}
