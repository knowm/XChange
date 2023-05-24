package org.knowm.xchange.bybit.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class BybitOrderDetails {

  @JsonProperty("accountId")
  String accountId;

  @JsonProperty("exchangeId")
  String exchangeId;

  @JsonProperty("symbol")
  String symbol;

  @JsonProperty("symbolName")
  String symbolName;

  @JsonProperty("orderLinkId")
  String orderLinkId;

  @JsonProperty("orderId")
  String orderId;

  @JsonProperty("price")
  String price;

  @JsonProperty("origQty")
  String origQty;

  @JsonProperty("executedQty")
  String executedQty;

  @JsonProperty("cummulativeQuoteQty")
  String cummulativeQuoteQty;

  @JsonProperty("avgPrice")
  String avgPrice;

  @JsonProperty("status")
  String status;

  @JsonProperty("timeInForce")
  String timeInForce;

  @JsonProperty("type")
  String type;

  @JsonProperty("side")
  String side;

  @JsonProperty("stopPrice")
  String stopPrice;

  @JsonProperty("icebergQty")
  String icebergQty;

  @JsonProperty("time")
  String time;

  @JsonProperty("updateTime")
  String updateTime;

  @JsonProperty("isWorking")
  boolean isWorking;

  @JsonProperty("locked")
  String locked;

}
