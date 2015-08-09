package com.xeiam.xchange.huobi.dto.streaming.request.service;

import com.xeiam.xchange.huobi.dto.streaming.request.AbstractSymbolIdListRequest;
import com.xeiam.xchange.huobi.dto.streaming.request.marketdata.Message;

/**
 * Request of canceling push message subscription.
 */
public class ReqMsgUnsubscribeRequest extends AbstractSymbolIdListRequest {

  private final Message symbolList;

  public ReqMsgUnsubscribeRequest(int version, Message symbolList) {
    super(version, "reqMsgUnsubscribe");
    this.symbolList = symbolList;
  }

  public Message getSymbolList() {
    return symbolList;
  }

}
