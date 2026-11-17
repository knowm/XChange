package info.bitrich.xchangestream.gateio.dto.response.wsPlaceOrder;

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
public class GateioWsCancelOrder {

  @JsonProperty("order_id")
  private String orderId;

  @JsonProperty("currency_pair")
  private String currencyPair;

  @JsonProperty("account")
  private String account;
}
