package com.xeiam.xchange.huobi.dto.streaming.response.service;

import com.xeiam.xchange.huobi.dto.streaming.response.ReqResponse;
import com.xeiam.xchange.huobi.dto.streaming.response.payload.VoidPayload;

/**
 * Response of canceling push message subscription.
 */
public class ReqMsgUnsubscribeResponse extends ReqResponse<VoidPayload> {

  public ReqMsgUnsubscribeResponse(int version, String msgType, int retCode, String retMsg) {
    super(version, msgType, retCode, retMsg);
  }

}
