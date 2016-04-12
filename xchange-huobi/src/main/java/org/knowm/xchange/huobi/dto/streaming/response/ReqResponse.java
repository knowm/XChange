package org.knowm.xchange.huobi.dto.streaming.response;

import org.knowm.xchange.huobi.dto.streaming.response.payload.Payload;
import org.knowm.xchange.huobi.service.streaming.HuobiSocket;

/**
 * Response of {@link HuobiSocket}.
 */
public class ReqResponse<T extends Payload> extends Response<T> {

  private long requestIndex;
  private final int retCode;
  private final String retMsg;

  /**
   * Constructs the response.
   *
   * @param version For client version.
   * @param msgType Message type.
   * @param retCode Echo back the error data. 200 for Success, for other results please check HTTP return code for reference.
   * @param retMsg Return tips.
   */
  public ReqResponse(int version, String msgType, int retCode, String retMsg) {
    super(version, msgType);
    this.retCode = retCode;
    this.retMsg = retMsg;
  }

  public long getRequestIndex() {
    return requestIndex;
  }

  public void setRequestIndex(long requestIndex) {
    this.requestIndex = requestIndex;
  }

  public int getRetCode() {
    return retCode;
  }

  public String getRetMsg() {
    return retMsg;
  }

}
