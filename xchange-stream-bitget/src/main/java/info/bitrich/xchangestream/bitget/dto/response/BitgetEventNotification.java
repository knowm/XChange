package info.bitrich.xchangestream.bitget.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder(toBuilder = true)
@Jacksonized
public class BitgetEventNotification extends BitgetWsNotification<Object> {

  @JsonProperty("event")
  private Event event;

  @JsonProperty("code")
  private Long code;

  @JsonProperty("msg")
  private String message;

  public static enum Event {
    @JsonProperty("subscribe")
    SUBSCRIBE,

    @JsonProperty("unsubscribe")
    UNSUBSCRIBE,

    @JsonProperty("login")
    LOGIN,

    @JsonProperty("error")
    ERROR,
  }
}
