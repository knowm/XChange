package com.xeiam.xchange.huobi.dto.streaming.response.service;

import com.xeiam.xchange.huobi.dto.streaming.response.ReqResponse;
import com.xeiam.xchange.huobi.dto.streaming.response.payload.ReqSymbolListPayload;

/**
 * Response of symbol list.
 */
public class ReqSymbolListResponse extends ReqResponse<ReqSymbolListPayload> {

  public ReqSymbolListResponse(int version, String msgType, int retCode, String retMsg) {
    super(version, msgType, retCode, retMsg);
  }

}
