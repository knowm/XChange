package info.bitrich.xchangestream.kucoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KucoinRawMatchEvent {

  @JsonProperty("data")
  private KucoinRawMatchEventData data;

}