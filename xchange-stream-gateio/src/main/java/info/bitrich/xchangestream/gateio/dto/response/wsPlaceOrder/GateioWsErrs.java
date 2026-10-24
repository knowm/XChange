package info.bitrich.xchangestream.gateio.dto.response.wsPlaceOrder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@lombok.Data
@Builder
@Jacksonized
public class GateioWsErrs {
  @JsonProperty("label")
  private String label;

  @JsonProperty("message")
  private String message;
}
