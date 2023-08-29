package info.bitrich.xchangestream.kucoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KucoinWebSocketOrderEventV2 extends KucoinWebSocketEvent {
    @JsonProperty("data")
    private KucoinOrderEventDataV2 data;
}
