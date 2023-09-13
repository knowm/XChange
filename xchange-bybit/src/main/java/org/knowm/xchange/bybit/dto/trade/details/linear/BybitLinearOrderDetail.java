package org.knowm.xchange.bybit.dto.trade.details.linear;

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
public class BybitLinearOrderDetail extends BybitOrderDetail {

  @JsonProperty("orderLinkId")
  String orderLinkId;

  @JsonProperty("blockTradeId")
  String blockTradeId;

  @JsonProperty("isLeverage")
  String isLeverage;

  @JsonProperty("positionIdx")
  int positionIdx;

  @JsonProperty("cancelType")
  String cancelType;

  @JsonProperty("rejectReason")
  String rejectReason;

  @JsonProperty("leavesQty")
  BigDecimal leavesQty;

  @JsonProperty("leavesValue")
  BigDecimal leavesValue;

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

  @JsonProperty("updatedTime")
  Date updatedTime;
}
