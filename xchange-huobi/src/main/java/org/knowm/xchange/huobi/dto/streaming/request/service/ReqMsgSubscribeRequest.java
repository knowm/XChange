package org.knowm.xchange.huobi.dto.streaming.request.service;

import org.knowm.xchange.huobi.dto.streaming.request.Request;
import org.knowm.xchange.huobi.dto.streaming.request.marketdata.Message;

/**
 * Request of subscribing push message.
 */
public class ReqMsgSubscribeRequest extends Request {

  private final Message symbolList;

  public ReqMsgSubscribeRequest(int version, Message symbolList) {
    super(version, "reqMsgSubscribe");
    this.symbolList = symbolList;
  }

  public Message getSymbolList() {
    return symbolList;
  }

}
