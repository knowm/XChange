package info.bitrich.xchangestream.poloniex2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Created by Lukas Zaoralek on 10.11.17. */
public class PoloniexWebSocketSubscriptionMessage {

  private static final String COMMAND = "command";
  private static final String CHANNEL = "channel";

  @JsonProperty(COMMAND)
  private String command;

  @JsonProperty(CHANNEL)
  private String channel;

  public PoloniexWebSocketSubscriptionMessage(String command, String channel) {
    this.command = command;
    this.channel = channel;
  }
}
