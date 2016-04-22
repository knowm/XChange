package org.knowm.xchange.huobi.dto.streaming.response;

import org.knowm.xchange.huobi.dto.streaming.response.payload.VoidPayload;

/**
 * Error response.
 */
public class ErrorResponse extends ReqResponse<VoidPayload> {

  public static final int CODE_OK = 200;
  public static final int CODE_PARAM_ERROR = 601;
  public static final int CODE_SERVER_ERROR = 701;

  public ErrorResponse(int version, String msgType, int retCode, String retMsg) {
    super(version, msgType, retCode, retMsg);
  }

}
