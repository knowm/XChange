package org.knowm.xchange.bybit.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class BybitOrderRequest {

  @JsonProperty("accountId")
  String accountId;

  @JsonProperty("symbol")
  String symbol;

  @JsonProperty("symbolName")
  String symbolName;

  @JsonProperty("orderLinkId")
  String orderLinkId;

  @JsonProperty("orderId")
  String orderId;

  @JsonProperty("transactTime")
  String transactTime;

  @JsonProperty("price")
  String price;

  @JsonProperty("origQty")
  String origQty;

  @JsonProperty("executedQty")
  String executedQty;

  @JsonProperty("status")
  String status;

  @JsonProperty("timeInForce")
  String timeInForce;

  @JsonProperty("type")
  String type;

  @JsonProperty("side")
  String side;
}
