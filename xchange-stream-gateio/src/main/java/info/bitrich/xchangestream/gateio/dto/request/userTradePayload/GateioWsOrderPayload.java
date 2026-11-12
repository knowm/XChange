package info.bitrich.xchangestream.gateio.dto.request.userTradePayload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class GateioWsOrderPayload<T> {

  @JsonProperty("req_id")
  private String reqId;

  @JsonProperty("req_param")
  private T reqParam;

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
