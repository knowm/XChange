package org.knowm.xchange.hitbtc.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcCancelReject {

  private final String cancelRequestClientOrderId;
  private final String clientOrderId;
  private final String rejectReasonCode;

  public HitbtcCancelReject(@JsonProperty("cancelRequestClientOrderId") String cancelRequestClientOrderId,
      @JsonProperty("clientOrderId") String clientOrderId, @JsonProperty("rejectReasonCode") String rejectReasonCode) {

    super();
    this.cancelRequestClientOrderId = cancelRequestClientOrderId;
    this.clientOrderId = clientOrderId;
    this.rejectReasonCode = rejectReasonCode;
  }

  public String getCancelRequestClientOrderId() {

    return cancelRequestClientOrderId;
  }

  public String getClientOrderId() {

    return clientOrderId;
  }

  public String getRejectReasonCode() {

    return rejectReasonCode;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("HitbtcCancelReject [cancelRequestClientOrderId=");
    builder.append(cancelRequestClientOrderId);
    builder.append(", clientOrderId=");
    builder.append(clientOrderId);
    builder.append(", rejectReasonCode=");
    builder.append(rejectReasonCode);
    builder.append("]");
    return builder.toString();
  }
}
