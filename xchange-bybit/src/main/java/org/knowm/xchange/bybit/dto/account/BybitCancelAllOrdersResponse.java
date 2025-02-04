package org.knowm.xchange.bybit.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.bybit.dto.trade.BybitOrderResponse;

@Getter
@Setter
public class BybitCancelAllOrdersResponse {

  @JsonProperty("list")
  List<BybitOrderResponse> list;

  String success;
}
