package info.bitrich.xchangestream.gateio.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import info.bitrich.xchangestream.gateio.config.InstantToTimestampSecondsConverter;
import info.bitrich.xchangestream.gateio.config.TimestampSecondsToInstantConverter;
import java.time.Instant;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder
@Jacksonized
public class GateioWebSocketRequest {

  @JsonProperty("time")
  @JsonDeserialize(converter = TimestampSecondsToInstantConverter.class)
  @JsonSerialize(converter = InstantToTimestampSecondsConverter.class)
  private Instant time;

  @JsonProperty("id")
  private Long id;

  @JsonProperty("channel")
  private String channel;

  @JsonProperty("event")
  private String event;

  @JsonProperty("payload")
  @JsonFormat(shape = JsonFormat.Shape.ARRAY)
  private Object payload;

}
