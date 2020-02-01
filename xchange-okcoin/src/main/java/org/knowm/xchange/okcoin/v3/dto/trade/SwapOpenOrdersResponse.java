package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SwapOpenOrdersResponse extends OkexResponse {

  @JsonProperty("order_info")
  private List<OkexSwapOpenOrder> orders;
}
