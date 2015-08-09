package com.xeiam.xchange.huobi.dto.streaming.request.service;

import com.xeiam.xchange.huobi.dto.streaming.request.AbstractSymbolIdListRequest;

/**
 * Request of symbol list.
 */
public class ReqSymbolListRequest extends AbstractSymbolIdListRequest {

  public ReqSymbolListRequest(int version) {
    super(version, "reqSymbolList");
  }

}
