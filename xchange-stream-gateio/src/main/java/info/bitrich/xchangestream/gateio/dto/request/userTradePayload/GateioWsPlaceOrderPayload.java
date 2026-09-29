package info.bitrich.xchangestream.gateio.dto.request.userTradePayload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.gateio.dto.trade.GateioSpotOrderRequest;

@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class GateioWsPlaceOrderPayload {

  @JsonProperty("req_id")
  private String reqId;

  @JsonProperty("req_param")
  private GateioSpotOrderRequest reqParam;

  @JsonProperty("req_header")
  private ReqHeader reqHeader;

  @Data
  @Builder
  @Jacksonized
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ReqHeader {

    @JsonProperty("x-gate-exptime")
    private String xGateExptime;
  }
}
