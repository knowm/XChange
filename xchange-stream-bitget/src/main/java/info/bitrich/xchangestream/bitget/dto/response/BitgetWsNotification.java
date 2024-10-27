package info.bitrich.xchangestream.bitget.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import info.bitrich.xchangestream.bitget.dto.common.Action;
import info.bitrich.xchangestream.bitget.dto.common.BitgetChannel;
import info.bitrich.xchangestream.bitget.dto.common.Operation;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@JsonTypeInfo(
    use = Id.NAME,
    include = As.EXISTING_PROPERTY,
    property = "messageType",
    visible = true
)
@JsonSubTypes({
    @Type(value = BitgetTickerNotification.class, name = "ticker"),
})
@Data
@SuperBuilder
@Jacksonized
public class BitgetWsNotification {

  @JsonProperty("action")
  private Action action;

  @JsonProperty("op")
  private Operation operation;

  @JsonProperty("event")
  private Event event;

  @JsonProperty("arg")
  private BitgetChannel channel;


  public static enum Event {
    @JsonProperty("subscribe")
    SUBSCRIBE,

    @JsonProperty("login")
    LOGIN,

    @JsonProperty("error")
    ERROR,
  }


}
