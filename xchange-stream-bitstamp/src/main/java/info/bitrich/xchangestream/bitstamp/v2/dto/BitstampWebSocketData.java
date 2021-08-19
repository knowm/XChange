package info.bitrich.xchangestream.bitstamp.v2.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BitstampWebSocketData {

  private static final String CHANNEL = "channel";

  private String channel;

  @JsonCreator
  public BitstampWebSocketData(@JsonProperty(CHANNEL) String channel) {
    this.channel = channel;
  }

  public String getChannel() {
    return channel;
  }
}
