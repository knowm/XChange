package info.bitrich.xchangestream.coinmate.v2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.coinmate.v2.dto.auth.AuthParams;

public class CoinmateAuthenticedSubscribeMessage {

  @JsonProperty("event")
  private String event;

  @JsonProperty("data")
  private CoinmateAuthenticatedChannelData data;

  public CoinmateAuthenticedSubscribeMessage(String channel, AuthParams authParams) {
    this.event = "subscribe";
    this.data = new CoinmateAuthenticatedChannelData(channel, authParams);
  }
}
