package org.knowm.xchange.huobi.dto.streaming.response.service;

import org.knowm.xchange.huobi.dto.streaming.response.ReqResponse;
import org.knowm.xchange.huobi.dto.streaming.response.payload.ReqSymbolDetailPayload;

/**
 * Response of symbol details.
 */
public class ReqSymbolDetailResponse extends ReqResponse<ReqSymbolDetailPayload> {

  public ReqSymbolDetailResponse(int version, String msgType, int retCode, String retMsg, ReqSymbolDetailPayload payload) {
    super(version, msgType, retCode, retMsg);
    setPayload(payload);
  }

}
