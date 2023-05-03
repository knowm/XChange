package info.bitrich.xchangestream.kucoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KucoinWebSocketUnsubscribeMessage {

  @JsonProperty("topic")
  public final String topic;

  @JsonProperty("type")
  public final String type = "unsubscribe";

  @JsonProperty("id")
  public final Long id;

  public KucoinWebSocketUnsubscribeMessage(String topic, Long id) {
    this.topic = topic;
    this.id = id;
  }
}
