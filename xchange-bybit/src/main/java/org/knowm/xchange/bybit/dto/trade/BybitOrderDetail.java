package org.knowm.xchange.bybit.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class BybitOrderDetail {

  @JsonProperty("orderId")
  String orderId;

  @JsonProperty("orderLinkId")
  String orderLinkId;

  @JsonProperty("blockTradeId")
  String blockTradeId;

  @JsonProperty("symbol")
  String symbol;

  @JsonProperty("price")
  BigDecimal price;

  @JsonProperty("qty")
  BigDecimal qty;

  @JsonProperty("side")
  BybitSide side;

  @JsonProperty("isLeverage")
  boolean isLeverage;

  @JsonProperty("positionIdx")
  int positionIdx;

  @JsonProperty("orderStatus")
  BybitOrderStatus orderStatus;

  @JsonProperty("cancelType")
  String cancelType;

  @JsonProperty("rejectReason")
  String rejectReason;

  @JsonProperty("avgPrice")
  BigDecimal avgPrice;

  @JsonProperty("leavesQty")
  BigDecimal leavesQty;

  @JsonProperty("leavesValue")
  BigDecimal leavesValue;

  @JsonProperty("cumExecQty")
  BigDecimal cumExecQty;

  @JsonProperty("cumExecValue")
  BigDecimal cumExecValue;

  @JsonProperty("cumExecFee")
  BigDecimal cumExecFee;

  @JsonProperty("timeInForce")
  String timeInForce;

  @JsonProperty("orderType")
  BybitOrderType orderType;

  @JsonProperty("stopOrderType")
  String stopOrderType;

  @JsonProperty("orderIv")
  String orderIv;

  @JsonProperty("triggerPrice")
  BigDecimal triggerPrice;

  @JsonProperty("takeProfit")
  BigDecimal takeProfit;

  @JsonProperty("stopLoss")
  BigDecimal stopLoss;

  @JsonProperty("tpTriggerBy")
  String tpTriggerBy;

  @JsonProperty("slTriggerBy")
  String slTriggerBy;

  @JsonProperty("triggerDirection")
  int triggerDirection;

  @JsonProperty("triggerBy")
  String triggerBy;

  @JsonProperty("lastPriceOnCreated")
  String lastPriceOnCreated;

  @JsonProperty("reduceOnly")
  boolean reduceOnly;

  @JsonProperty("closeOnTrigger")
  boolean closeOnTrigger;

  @JsonProperty("smpType")
  String smpType;

  @JsonProperty("smpGroup")
  int smpGroup;

  @JsonProperty("smpOrderId")
  String smpOrderId;

  @JsonProperty("tpslMode")
  String tpslMode;

  @JsonProperty("tpLimitPrice")
  String tpLimitPrice;

  @JsonProperty("slLimitPrice")
  String slLimitPrice;

  @JsonProperty("placeType")
  String placeType;

  @JsonProperty("createdTime")
  Date createdTime;

  @JsonProperty("updatedTime")
  Date updatedTime;
}
