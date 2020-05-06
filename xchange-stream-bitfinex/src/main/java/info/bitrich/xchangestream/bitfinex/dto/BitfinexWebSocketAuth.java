package info.bitrich.xchangestream.bitfinex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexWebSocketAuth {

  private final String apiKey;
  private final String authPayload;
  private final String authNonce;
  private final String authSig;
  private final String event;

  public BitfinexWebSocketAuth(
      @JsonProperty("apiKey") String apiKey,
      @JsonProperty("authPayload") String authPayload,
      @JsonProperty("authNonce") String authNonce,
      @JsonProperty("authSig") String authSig) {
    this.apiKey = apiKey;
    this.event = "auth";
    this.authPayload = authPayload;
    this.authNonce = authNonce;
    this.authSig = authSig;
  }

  @Override
  public String toString() {
    return "BitfinexWebSocketAuth{"
        + "apiKey='"
        + apiKey
        + '\''
        + ", authPayload='"
        + authPayload
        + '\''
        + ", authNonce='"
        + authNonce
        + '\''
        + ", authSig='"
        + authSig
        + '\''
        + ", event='"
        + event
        + '\''
        + '}';
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getAuthPayload() {
    return authPayload;
  }

  public String getAuthNonce() {
    return authNonce;
  }

  public String getAuthSig() {
    return authSig;
  }

  public String getEvent() {
    return event;
  }
}
