package com.xeiam.xchange.huobi.dto.streaming.response.marketdata;

import com.xeiam.xchange.huobi.dto.streaming.response.Response;
import com.xeiam.xchange.huobi.dto.streaming.response.payload.Payload;

/**
 * Push message.
 */
public abstract class Message<T extends Payload> extends Response<T> {

  private final String symbolId;

  public Message(int version, String msgType, String symbolId, T payload) {
    super(version, msgType);
    this.symbolId = symbolId;
    setPayload(payload);
  }

  public String getSymbolId() {
    return symbolId;
  }

}
