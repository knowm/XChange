package info.bitrich.xchangestream.kucoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@ToString
public class KucoinOrderBookEvent extends KucoinWebSocketEvent {
    @JsonProperty("data")
    public KucoinOrderBookEventData data;
}
