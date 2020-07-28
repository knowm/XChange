package info.bitrich.xchangestream.bitmex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Created by Lukas Zaoralek on 13.11.17. */
public class BitmexWebSocketSubscriptionMessage {
  private static final String OP = "op";
  private static final String ARGS = "args";

  @JsonProperty(OP)
  private String op;

  @JsonProperty(ARGS)
  private Object[] args;

  public BitmexWebSocketSubscriptionMessage(String op, Object[] args) {
    this.op = op;
    this.args = args;
  }
}
