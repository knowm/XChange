package info.bitrich.xchangestream.gateio.dto.response.wsPlaceOrder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class GateioWsAmendOrderFuture {
  @JsonProperty("order_id")
  String order_id;

  @JsonProperty("size")
  String size;

  @JsonProperty("price")
  String price;

}
