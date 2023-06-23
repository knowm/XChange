package info.bitrich.xchangestream.kucoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KucoinWebSocketEvent {
    @JsonProperty("type")
    public String type;

    @JsonProperty("topic")
    public String topic;

    @JsonProperty("subject")
    public String subject;
}
