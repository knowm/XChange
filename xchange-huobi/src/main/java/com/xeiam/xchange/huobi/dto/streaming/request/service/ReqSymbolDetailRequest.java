package com.xeiam.xchange.huobi.dto.streaming.request.service;

import com.xeiam.xchange.huobi.dto.streaming.request.AbstractSymbolIdListRequest;

/**
 * Request of symbol details.
 */
public class ReqSymbolDetailRequest extends AbstractSymbolIdListRequest {

  public ReqSymbolDetailRequest(int version, String... symbolIdList) {
    super(version, "reqSymbolDetail");
    setSymbolIdList(symbolIdList);
  }

}
