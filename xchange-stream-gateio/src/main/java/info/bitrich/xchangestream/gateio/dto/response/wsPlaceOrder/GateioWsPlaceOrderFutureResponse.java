package info.bitrich.xchangestream.gateio.dto.response.wsPlaceOrder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class GateioWsPlaceOrderFutureResponse {

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
    private Object result;

    @JsonProperty("errs")
    private GateioWsErrs errs;
  }

}
