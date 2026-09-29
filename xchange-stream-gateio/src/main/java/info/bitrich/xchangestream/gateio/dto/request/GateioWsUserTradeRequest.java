package info.bitrich.xchangestream.gateio.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;

@Data
@SuperBuilder
@Jacksonized
public class GateioWsUserTradeRequest {

  @JsonProperty("time")
  private Instant time;

  @JsonProperty("id")
  private Long id;

  @JsonProperty("channel")
  private String channel;

  @JsonProperty("event")
  private String event;

  @JsonProperty("payload")
  private Object payload;

  @JsonProperty("req_id")
  private String reqId;

  @JsonProperty("req_param")
  private byte[] reqParam;

}