package info.bitrich.xchangestream.coinmate.v2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinmatePingMessage {
  @JsonProperty("event")
  final String event = "ping";

  public CoinmatePingMessage() {

  }
}
