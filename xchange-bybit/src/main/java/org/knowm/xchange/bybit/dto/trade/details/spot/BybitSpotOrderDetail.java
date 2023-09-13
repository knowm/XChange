package org.knowm.xchange.bybit.dto.trade.details.spot;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bybit.dto.trade.BybitOrderType;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetail;

@SuperBuilder
@Jacksonized
@Value
public class BybitSpotOrderDetail extends BybitOrderDetail {

  @JsonProperty("orderType")
  BybitOrderType orderType;

  @JsonProperty("orderLinkId")
  String orderLinkId;

  @JsonProperty("cancelType")
  String cancelType;

  @JsonProperty("stopOrderType")
  String stopOrderType;

  @JsonProperty("lastPriceOnCreated")
  BigDecimal lastPriceOnCreated;

  @JsonProperty("takeProfit")
  String takeProfit;

  @JsonProperty("cumExecValue")
  BigDecimal cumExecValue;

  @JsonProperty("smpType")
  String smpType;

  @JsonProperty("triggerDirection")
  int triggerDirection;

  @JsonProperty("blockTradeId")
  String blockTradeId;

  @JsonProperty("isLeverage")
  String isLeverage;

  @JsonProperty("rejectReason")
  String rejectReason;

  @JsonProperty("orderIv")
  String orderIv;

  @JsonProperty("tpTriggerBy")
  String tpTriggerBy;

  @JsonProperty("positionIdx")
  int positionIdx;

  @JsonProperty("timeInForce")
  String timeInForce;

  @JsonProperty("leavesValue")
  BigDecimal leavesValue;

  @JsonProperty("updatedTime")
  Date updatedTime;

  @JsonProperty("smpGroup")
  int smpGroup;

  @JsonProperty("triggerPrice")
  BigDecimal triggerPrice;

  @JsonProperty("cumExecFee")
  BigDecimal cumExecFee;

  @JsonProperty("leavesQty")
  BigDecimal leavesQty;

  @JsonProperty("slTriggerBy")
  String slTriggerBy;

  @JsonProperty("closeOnTrigger")
  boolean closeOnTrigger;

  @JsonProperty("placeType")
  String placeType;

  @JsonProperty("reduceOnly")
  boolean reduceOnly;

  @JsonProperty("stopLoss")
  String stopLoss;

  @JsonProperty("smpOrderId")
  String smpOrderId;

  @JsonProperty("triggerBy")
  String triggerBy;
}
