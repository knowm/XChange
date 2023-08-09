package info.bitrich.xchangestream.gateio.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import info.bitrich.xchangestream.gateio.config.TimestampSecondsToInstantConverter;
import java.time.Instant;
import lombok.Data;

@Data
public class GateioWebSocketNotification<T> {

  @JsonProperty("time")
  @JsonDeserialize(converter = TimestampSecondsToInstantConverter.class)
  private Instant time;

  @JsonProperty("time_ms")
  private Instant timeMs;

  @JsonProperty("channel")
  private String channel;

  @JsonProperty("event")
  private String event;

  @JsonProperty("error")
  private String error;

  @JsonProperty("result")
  private T result;
}
