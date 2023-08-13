package info.bitrich.xchangestream.gateio.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import info.bitrich.xchangestream.gateio.config.converter.InstantToTimestampSecondsConverter;
import info.bitrich.xchangestream.gateio.dto.Event;
import java.time.Instant;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.gateio.config.converter.TimestampSecondsToInstantConverter;

@Data
@SuperBuilder
@Jacksonized
public class GateioWsRequest {

  @JsonProperty("time")
  @JsonDeserialize(converter = TimestampSecondsToInstantConverter.class)
  @JsonSerialize(converter = InstantToTimestampSecondsConverter.class)
  private Instant time;

  @JsonProperty("id")
  private Long id;

  @JsonProperty("channel")
  private String channel;

  @JsonProperty("event")
  private Event event;

  @JsonProperty("auth")
  private AuthInfo authInfo;

  @JsonProperty("payload")
  @JsonFormat(shape = JsonFormat.Shape.ARRAY)
  private Object payload;


  @Data
  @SuperBuilder
  @Jacksonized
  public static class AuthInfo {

    @JsonProperty("method")
    private String method;

    @JsonProperty("key")
    private String key;

    @JsonProperty("sign")
    private String sign;

  }

}
