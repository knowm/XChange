package org.knowm.xchange.hitbtc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InternalTransferResponse {
  public final String transactionId;
  public final String message;
  public final String statusCode;
  public final String body;

  public InternalTransferResponse(@JsonProperty("transaction") String transactionId, @JsonProperty("message") String message, @JsonProperty("statusCode") String statusCode, @JsonProperty("body") String body) {
    this.transactionId = transactionId;
    this.message = message;
    this.statusCode = statusCode;
    this.body = body;
  }

  @Override
  public String toString() {
    return "InternalTransferResponse{" +
        "transactionId='" + transactionId + '\'' +
        ", message='" + message + '\'' +
        ", statusCode='" + statusCode + '\'' +
        ", body='" + body + '\'' +
        '}';
  }
}
