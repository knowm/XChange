package info.bitrich.xchangestream.gateio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@lombok.Data
@SuperBuilder
@Jacksonized
public class GateioUserTradeWsResponse {

  @JsonProperty("request_id")
  private String requestId;

  @JsonProperty("header")
  private Header header;

  @JsonProperty("data")
  private ResponseData data;

  @lombok.Data
  @SuperBuilder
  @Jacksonized
  public static class Header {

    @JsonProperty("response_time")
    private String responseTime;

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

    @JsonProperty("x_gate_ratelimit_requests_remain")
    private Long xGateRatelimitRequestsRemain;

    @JsonProperty("x_gate_ratelimit_limit")
    private Long xGateRatelimitLimit;

    @JsonProperty("x_gat_ratelimit_reset_timestamp")
    private Long xGatRatelimitResetTimestamp;

    @JsonProperty("conn_id")
    private String connId;

    @JsonProperty("conn_trace_id")
    private String connTraceId;

    @JsonProperty("trace_id")
    private String traceId;
  }

  @lombok.Data
  @SuperBuilder
  @Jacksonized
  public static class ResponseData {

    @JsonProperty("result")
    private Object result;

    @JsonProperty("errs")
    private Errors errs;
  }

  @lombok.Data
  @SuperBuilder
  @Jacksonized
  public static class Errors {

    @JsonProperty("label")
    private String label;

    @JsonProperty("message")
    private String message;
  }
}