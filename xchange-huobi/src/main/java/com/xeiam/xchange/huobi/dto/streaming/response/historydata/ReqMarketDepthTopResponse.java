package com.xeiam.xchange.huobi.dto.streaming.response.historydata;

import com.xeiam.xchange.huobi.dto.streaming.response.ReqResponse;
import com.xeiam.xchange.huobi.dto.streaming.response.payload.ReqMarketDepthTopPayload;

/**
 * Response of top market-depth.
 */
public class ReqMarketDepthTopResponse extends ReqResponse<ReqMarketDepthTopPayload> {

  public ReqMarketDepthTopResponse(int version, String msgType, int retCode, String retMsg) {
    super(version, msgType, retCode, retMsg);
  }

}
