package info.bitrich.xchangestream.gateio.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Event {
  SUBSCRIBE("subscribe"),
  UNSUBSCRIBE("unsubscribe"),
  UPDATE("update");

  @JsonValue
  private final String value;


}
