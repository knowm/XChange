package org.knowm.xchange.coinbene.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class CoinbeneRequest {
  @JsonProperty private final String apiId;

  @JsonProperty private final long timestamp;

  @JsonProperty private final String sign;

  protected CoinbeneRequest(String apiId, long timestamp, String sign) {
    this.apiId = apiId;
    this.timestamp = timestamp;
    this.sign = sign;
  }

  public String getApiId() {
    return apiId;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public String getSign() {
    return sign;
  }
}
