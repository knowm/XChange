package info.bitrich.xchangestream.cexio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CexioWebSocketSubscriptionRequest {
  @JsonProperty("e")
  private final String event;

  @JsonProperty private final Object data;

  @JsonProperty private final String oid;

  public CexioWebSocketSubscriptionRequest(String event, Object data, String oid) {
    this.event = event;
    this.data = data;
    this.oid = oid;
  }
}
