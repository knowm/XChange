package info.bitrich.xchangestream.gateio.dto.response.wsPlaceOrder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class GateioWsPlaceOrderSpotResponse {

  @JsonProperty("request_id")
  private String requestId;

  @JsonProperty("ack")
  private Boolean ack;

  @JsonProperty("header")
  private GateioWsResponseHeader header;

  @JsonProperty("data")
  private Data data;

  @lombok.Data
  @Builder
  @Jacksonized
  public static class Data {

    @JsonProperty("result")
    private Result result;

    @JsonProperty("errs")
    private GateioWsErrs errs;
  }

  @lombok.Data
  @Builder
  @Jacksonized
  public static class Result {

    @JsonProperty("req_id")
    private String reqId;

    @JsonProperty("req_param")
    private ReqParam reqParam;
  }

  @lombok.Data
  @Builder
  @Jacksonized
  public static class ReqParam {

    @JsonProperty("text")
    private String text;

    @JsonProperty("currency_pair")
    private String currencyPair;

    @JsonProperty("type")
    private String type;

    @JsonProperty("side")
    private String side;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("time_in_force")
    private String timeInForce;
  }

}
