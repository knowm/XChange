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
import java.util.List;
import lombok.Data;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@JsonTypeInfo(
    use = Id.NAME,
    include = As.EXISTING_PROPERTY,
    property = "messageType",
    visible = true,
    defaultImpl = BitgetWsNotification.class)
@JsonSubTypes({
  @Type(value = BitgetEventNotification.class, name = "event"),
  @Type(value = BitgetTickerNotification.class, name = "ticker"),
  @Type(value = BitgetWsOrderBookSnapshotNotification.class, name = "books1"),
  @Type(value = BitgetWsOrderBookSnapshotNotification.class, name = "books5"),
  @Type(value = BitgetWsOrderBookSnapshotNotification.class, name = "books15"),
  @Type(value = BitgetWsUserTradeNotification.class, name = "fill"),
})
@Data
@SuperBuilder(toBuilder = true)
@Jacksonized
public class BitgetWsNotification<T> {

  @JsonProperty("action")
  private Action action;

  @JsonProperty("op")
  private Operation operation;

  @JsonProperty("arg")
  private BitgetChannel channel;

  @Singular
  @JsonProperty("data")
  private List<T> payloadItems;
}
