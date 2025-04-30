package info.bitrich.xchangestream.bitget.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Operation {
  @JsonProperty("subscribe")
  SUBSCRIBE,

  @JsonProperty("unsubscribe")
  UNSUBSCRIBE,

  @JsonProperty("login")
  LOGIN
}
