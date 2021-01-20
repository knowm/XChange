package info.bitrich.xchangestream.coinmate.v2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinmateUnsubscribeMessage {
  @JsonProperty("event")
  private String event;

  @JsonProperty("data")
  private CoinmateChannelData data;

  public CoinmateUnsubscribeMessage() {
  }

  public CoinmateUnsubscribeMessage(String channel) {
    this.event = "unsubscribe";
    this.data = new CoinmateChannelData(channel);
  }
}
