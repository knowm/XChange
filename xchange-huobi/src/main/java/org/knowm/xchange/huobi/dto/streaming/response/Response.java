package org.knowm.xchange.huobi.dto.streaming.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.knowm.xchange.huobi.dto.streaming.response.payload.Payload;

/**
 * Response of Huobi WebSocket API.
 */
public class Response<T extends Payload> {

  private final int version;
  private final String msgType;
  private T payload;

  public Response(int version, String msgType) {
    this.version = version;
    this.msgType = msgType;
  }

  public int getVersion() {
    return version;
  }

  public String getMsgType() {
    return msgType;
  }

  public T getPayload() {
    return payload;
  }

  public void setPayload(T payload) {
    this.payload = payload;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
