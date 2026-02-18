package info.bitrich.xchangestream.kraken.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Method {
  @JsonProperty("subscribe")
  SUBSCRIBE,

  @JsonProperty("unsubscribe")
  UNSUBSCRIBE
}
