package info.bitrich.xchangestream.kucoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KucoinWebSocketBalanceChangeEvent extends KucoinWebSocketEvent{

    @JsonProperty("data")
    private KucoinBalanceChangeEventData data;

}
