package org.knowm.xchange.bybit.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class BybitOrderResponse {
  @JsonProperty("list")
  List<BybitOrderResponse> list;

  @JsonProperty("orderId")
  String orderId;

  @JsonProperty("orderLinkId")
  String orderLinkId;
}
