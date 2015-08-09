package com.xeiam.xchange.huobi.dto.streaming.request.historydata;

import com.xeiam.xchange.huobi.dto.streaming.request.AbstractSymbolIdRequest;

/**
 * Request of market details.
 */
public class ReqMarketDetailRequest extends AbstractSymbolIdRequest {

  public ReqMarketDetailRequest(int version, String symbolId) {
    super(version, "reqMarketDetail", symbolId);
  }

}
