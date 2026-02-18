package info.bitrich.xchangestream.kraken.dto.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChannelType {
  TICKER("ticker"),
  TRADE("trade"),

  BALANCES("balances"),
  USER_TRADES("executions");

  @JsonValue private final String value;

  public String toString() {
    return value;
  }
}
