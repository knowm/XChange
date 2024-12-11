package org.knowm.xchange.bybit.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import org.knowm.xchange.bybit.dto.BybitCategory;

@Getter
@JsonInclude(Include.NON_NULL)
public class BybitAmendOrderPayload {

  BybitCategory category;
  String symbol;
  String orderId;
  String orderLinkId;
  String orderIv;
  String triggerPrice;
  String qty;
  String price;
  String tpslMode;
  String takeProfit;
  String stopLoss;
  String tpTriggerBy;
  String slTriggerBy;
  String triggerBy;
  String tpLimitPrice;
  String slLimitPrice;

  public BybitAmendOrderPayload(
      BybitCategory category,
      String symbol,
      String orderId,
      String orderLinkId,
      String triggerPrice,
      String qty,
      String price,
      String tpslMode,
      String takeProfit,
      String stopLoss,
      String tpTriggerBy,
      String slTriggerBy,
      String triggerBy,
      String tpLimitPrice,
      String slLimitPrice) {
    this.category = category;
    this.symbol = symbol;
    this.orderId = orderId;
    this.orderLinkId = orderLinkId;
    this.triggerPrice = triggerPrice;
    this.qty = qty;
    this.price = price;
    this.tpslMode = tpslMode;
    this.takeProfit = takeProfit;
    this.stopLoss = stopLoss;
    this.tpTriggerBy = tpTriggerBy;
    this.slTriggerBy = slTriggerBy;
    this.triggerBy = triggerBy;
    this.tpLimitPrice = tpLimitPrice;
    this.slLimitPrice = slLimitPrice;
  }
}
