package info.bitrich.xchangestream.gateio.dto.response.wsPlaceOrder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class GateioWsResponseHeader {

  @JsonProperty("response_time")
  private String responseTime;

  @JsonProperty("status")
  private String status;

  @JsonProperty("channel")
  private String channel;

  @JsonProperty("event")
  private String event;

  @JsonProperty("client_id")
  private String clientId;

  @JsonProperty("x_in_time")
  private Long xInTime;

  @JsonProperty("x_out_time")
  private Long xOutTime;

  @JsonProperty("conn_trace_id")
  private String connTraceId;

  @JsonProperty("trace_id")
  private String traceId;

  @JsonProperty("x_gate_ratelimit_requests_remain")
  private Integer xGateRatelimitRequestsRemain;

  @JsonProperty("x_gate_ratelimit_limit")
  private Integer xGateRatelimitLimit;

  @JsonProperty("x_gat_ratelimit_reset_timestamp")
  private Integer xGatRatelimitResetTimestamp;

  @JsonProperty("conn_id")
  private String connId;
}


