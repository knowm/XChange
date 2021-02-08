package info.bitrich.xchangestream.coinmate.v2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinmateSubscribeMessage {

  @JsonProperty("event")
  private String event;

  @JsonProperty("data")
  private CoinmateChannelData data;

  public CoinmateSubscribeMessage(String channel) {
    this.event = "subscribe";
    this.data = new CoinmateChannelData(channel);
  }
}
