package com.xeiam.xchange.huobi.dto.streaming.response.historydata;

import com.xeiam.xchange.huobi.dto.streaming.response.ReqResponse;
import com.xeiam.xchange.huobi.dto.streaming.response.payload.ReqMarketDepthPayload;

/**
 * Response of market-depth.
 */
public class ReqMarketDepthResponse extends ReqResponse<ReqMarketDepthPayload> {

  public ReqMarketDepthResponse(int version, String msgType, int retCode, String retMsg) {
    super(version, msgType, retCode, retMsg);
  }

}
