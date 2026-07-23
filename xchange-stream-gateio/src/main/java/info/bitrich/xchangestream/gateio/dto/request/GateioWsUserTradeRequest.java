package info.bitrich.xchangestream.gateio.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder
@Jacksonized
public class GateioWsUserTradeRequest {

  @JsonProperty("time")
  private long time;

  @JsonProperty("id")
  private String id;

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