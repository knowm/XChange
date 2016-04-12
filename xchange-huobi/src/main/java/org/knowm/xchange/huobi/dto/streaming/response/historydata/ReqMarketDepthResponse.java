package org.knowm.xchange.huobi.dto.streaming.response.historydata;

import org.knowm.xchange.huobi.dto.streaming.response.ReqResponse;
import org.knowm.xchange.huobi.dto.streaming.response.payload.ReqMarketDepthPayload;

/**
 * Response of market-depth.
 */
public class ReqMarketDepthResponse extends ReqResponse<ReqMarketDepthPayload> {

  public ReqMarketDepthResponse(int version, String msgType, int retCode, String retMsg) {
    super(version, msgType, retCode, retMsg);
  }

}
