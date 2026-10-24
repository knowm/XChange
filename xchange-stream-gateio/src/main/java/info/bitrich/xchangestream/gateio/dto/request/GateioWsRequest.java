package info.bitrich.xchangestream.gateio.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import info.bitrich.xchangestream.gateio.config.converter.InstantToTimestampMilliSecondsConverter;
import info.bitrich.xchangestream.gateio.config.converter.InstantToTimestampSecondsConverter;
import info.bitrich.xchangestream.gateio.dto.Event;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.gateio.config.converter.DoubleMilliSecondsToInstantConverter;
import org.knowm.xchange.gateio.config.converter.DoubleSecondsToInstantConverter;

import java.time.Instant;

@Data
@SuperBuilder
@Jacksonized
public class GateioWsRequest {

  @JsonProperty("time")
  @JsonDeserialize(converter = DoubleSecondsToInstantConverter.class)
  @JsonSerialize(converter = InstantToTimestampSecondsConverter.class)
  private Instant time;

  @JsonProperty("time_ms")
  @JsonDeserialize(converter = DoubleMilliSecondsToInstantConverter.class)
  @JsonSerialize(converter = InstantToTimestampMilliSecondsConverter.class)
  private Instant time_ms;

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
