package info.bitrich.xchangestream.coinjar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinjarWebSocketSubscribeMessage {

  @JsonProperty("event")
  public final String event = "phx_join";

  @JsonProperty("topic")
  public final String topic;

  @JsonProperty("payload")
  public final Payload payload;

  @JsonProperty("ref")
  public final Integer ref;

  public CoinjarWebSocketSubscribeMessage(String topic, String token, Integer ref) {
    this.topic = topic;
    this.payload = new Payload(token);
    this.ref = ref;
  }

  public class Payload {
    @JsonProperty("token")
    public final String token;

    Payload(String token) {
      this.token = token;
    }
  }
}
