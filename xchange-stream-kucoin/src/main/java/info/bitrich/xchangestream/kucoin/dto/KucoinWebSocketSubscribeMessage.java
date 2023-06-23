package info.bitrich.xchangestream.kucoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KucoinWebSocketSubscribeMessage {

  @JsonProperty("topic")
  public final String topic;

  @JsonProperty("type")
  public final String type = "subscribe";

  @JsonProperty("id")
  public final Long id;

  @JsonProperty("privateChannel")
  public final boolean privateChannel;

  public KucoinWebSocketSubscribeMessage(String topic, Long ref, boolean privateChannel) {
    this.topic = topic;
    this.id = ref;
    this.privateChannel = privateChannel;
  }
}
