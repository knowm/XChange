package com.xeiam.xchange.huobi.dto.streaming.response.historydata;

import com.xeiam.xchange.huobi.dto.streaming.response.ReqResponse;
import com.xeiam.xchange.huobi.dto.streaming.response.payload.ReqTradeDetailTopPayload;

/**
 * Response of top trade details.
 */
public class ReqTradeDetailTopResponse extends ReqResponse<ReqTradeDetailTopPayload> {

  public ReqTradeDetailTopResponse(int version, String msgType, int retCode, String retMsg) {
    super(version, msgType, retCode, retMsg);
  }

}
