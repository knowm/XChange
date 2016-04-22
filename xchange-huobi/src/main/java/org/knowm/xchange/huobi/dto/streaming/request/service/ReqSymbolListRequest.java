package org.knowm.xchange.huobi.dto.streaming.request.service;

import org.knowm.xchange.huobi.dto.streaming.request.AbstractSymbolIdListRequest;

/**
 * Request of symbol list.
 */
public class ReqSymbolListRequest extends AbstractSymbolIdListRequest {

  public ReqSymbolListRequest(int version) {
    super(version, "reqSymbolList");
  }

}
