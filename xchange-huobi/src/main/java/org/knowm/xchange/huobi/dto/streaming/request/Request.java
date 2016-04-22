package org.knowm.xchange.huobi.dto.streaming.request;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.knowm.xchange.huobi.service.streaming.HuobiSocket;

/**
 * The request for {@link HuobiSocket}.
 */
public class Request {

  private final int version;
  private final String msgType;
  private long requestIndex;

  /**
   * Constructs a request.
   *
   * @param version Client version.
   * @param msgType Message type.
   */
  public Request(int version, String msgType) {
    this.version = version;
    this.msgType = msgType;
  }

  /**
   * Returns the serial number to sort multiple requests, the server side will echo back the consistent data that client posted.
   *
   * @return serial number.
   */
  public long getRequestIndex() {
    return requestIndex;
  }

  public void setRequestIndex(long requestIndex) {
    this.requestIndex = requestIndex;
  }

  public int getVersion() {
    return version;
  }

  public String getMsgType() {
    return msgType;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
