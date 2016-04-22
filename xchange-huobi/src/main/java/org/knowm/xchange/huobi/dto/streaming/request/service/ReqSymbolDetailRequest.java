package org.knowm.xchange.huobi.dto.streaming.request.service;

import org.knowm.xchange.huobi.dto.streaming.request.AbstractSymbolIdListRequest;

/**
 * Request of symbol details.
 */
public class ReqSymbolDetailRequest extends AbstractSymbolIdListRequest {

  public ReqSymbolDetailRequest(int version, String... symbolIdList) {
    super(version, "reqSymbolDetail");
    setSymbolIdList(symbolIdList);
  }

}
