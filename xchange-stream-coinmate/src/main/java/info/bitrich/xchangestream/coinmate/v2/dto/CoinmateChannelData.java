package info.bitrich.xchangestream.coinmate.v2.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinmateChannelData {
  private String channel;

  @JsonCreator
  public CoinmateChannelData(@JsonProperty("channel") String channel) {
    this.channel = channel;
  }

  public String getChannel() {
    return channel;
  }
}
