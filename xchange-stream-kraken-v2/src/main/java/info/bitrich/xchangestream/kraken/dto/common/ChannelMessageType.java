package info.bitrich.xchangestream.kraken.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ChannelMessageType {
  @JsonProperty("snapshot")
  SNAPSHOT,

  @JsonProperty("update")
  UPDATE,
}
