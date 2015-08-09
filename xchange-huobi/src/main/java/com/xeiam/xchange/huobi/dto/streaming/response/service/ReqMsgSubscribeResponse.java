package com.xeiam.xchange.huobi.dto.streaming.response.service;

import com.xeiam.xchange.huobi.dto.streaming.response.ReqResponse;
import com.xeiam.xchange.huobi.dto.streaming.response.payload.VoidPayload;

/**
 * Response of subscribing push message.
 */
public class ReqMsgSubscribeResponse extends ReqResponse<VoidPayload> {

  public ReqMsgSubscribeResponse(int version, String msgType, int retCode, String retMsg) {
    super(version, msgType, retCode, retMsg);
  }

}
