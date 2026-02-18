package info.bitrich.xchangestream.deribit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Collection;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "messageType",
    visible = true,
    defaultImpl = DeribitWsNotification.class)
@JsonSubTypes({
  @Type(value = DeribitEventNotification.class, name = "event"),
  @Type(value = DeribitTickerNotification.class, name = "ticker"),
  @Type(value = DeribitTradeNotification.class, name = "trades"),
  @Type(value = DeribitUserChangeNotification.class, name = "user.changes"),
  @Type(value = DeribitUserTradeNotification.class, name = "user.trades"),
})
@Data
@SuperBuilder(toBuilder = true)
@Jacksonized
public class DeribitWsNotification<T> {

  @JsonProperty("method")
  String method;

  @JsonProperty("params")
  Params<T> params;

  @Data
  @Builder
  @Jacksonized
  public static class Params<T> {

    @JsonProperty("channel")
    String channel;

    @JsonProperty("data")
    T data;

    public boolean hasSinglePayload() {
      return data == null || !(data instanceof Collection) || ((Collection) data).size() < 2;
    }
  }

  public boolean hasSinglePayload() {
    return params == null || params.hasSinglePayload();
  }
}
