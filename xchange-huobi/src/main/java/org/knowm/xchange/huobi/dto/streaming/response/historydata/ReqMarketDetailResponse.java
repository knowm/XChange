package org.knowm.xchange.huobi.dto.streaming.response.historydata;

import org.knowm.xchange.huobi.dto.streaming.response.ReqResponse;
import org.knowm.xchange.huobi.dto.streaming.response.payload.ReqMarketDetailPayload;

/**
 * Response of market details.
 */
public class ReqMarketDetailResponse extends ReqResponse<ReqMarketDetailPayload> {

  public ReqMarketDetailResponse(int version, String msgType, int retCode, String retMsg) {
    super(version, msgType, retCode, retMsg);
  }

}
