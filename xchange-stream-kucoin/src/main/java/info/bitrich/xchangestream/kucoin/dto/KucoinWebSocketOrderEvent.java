package info.bitrich.xchangestream.kucoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@ToString
public class KucoinWebSocketOrderEvent extends KucoinWebSocketEvent {
    @JsonProperty("data")
    public KucoinOrderEventData data;
}
