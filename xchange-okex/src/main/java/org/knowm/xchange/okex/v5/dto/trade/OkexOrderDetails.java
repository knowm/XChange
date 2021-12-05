package org.knowm.xchange.okex.v5.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

/** Author: Max Gao (gaamox@tutanota.com) Created: 10-06-2021 */
@Getter
@ToString
public class OkexOrderDetails {
  @JsonProperty("instType")
  private String instrumentType;

  @JsonProperty("instId")
  private String instrumentId;

  @JsonProperty("tdMode")
  private String tradeMode;

  @JsonProperty("ccy")
  private String marginCurrency;

  @JsonProperty("ordId")
  private String orderId;

  @JsonProperty("clOrdId")
  private String clientOrderId;

  @JsonProperty("tag")
  private String tag;

  @JsonProperty("side")
  private String side;

  @JsonProperty("pnl")
  private String pnl;

  @JsonProperty("posSide")
  private String posSide;

  @JsonProperty("ordType")
  private String orderType;

  @JsonProperty("sz")
  private String amount;

  @JsonProperty("px")
  private String price;

  @JsonProperty("accFillSz")
  private String accumulatedFill;

  @JsonProperty("fillPx")
  private String lastFilledPrice;

  @JsonProperty("tradeId")
  private String lastTradeId;

  @JsonProperty("fillSz")
  private String lastFilledQuantity;

  @JsonProperty("fillTime")
  private String lastFilledTime;

  @JsonProperty("avgPx")
  private String averageFilledPrice;

  @JsonProperty("state")
  private String state;

  @JsonProperty("lever")
  private String leverage;

  @JsonProperty("tpTriggerPx")
  private String takeProfitTriggerPrice;

  @JsonProperty("tpOrdPx")
  private String takeProfitOrderPrice;

  @JsonProperty("slTriggerPx")
  private String stopLossTriggerPrice;

  @JsonProperty("slOrdPx")
  private String stopLossOrderPrice;

  @JsonProperty("feeCcy")
  private String feeCurrency;

  @JsonProperty("fee")
  private String fee;

  @JsonProperty("rebaseCcy")
  private String rebateCcy;

  @JsonProperty("rebase")
  private String rebateAmount;

  @JsonProperty("category")
  private String category;

  @JsonProperty("uTime")
  private String updateTime;

  @JsonProperty("cTime")
  private String creationTime;
}
