package org.knowm.xchange.bitmex.dto.trade;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BitmexPrivateExecution {

  @JsonProperty("execID")
  public String execID;

  @JsonProperty("orderID")
  public String orderID;

  @JsonProperty("clOrdID")
  public String clOrdID;

  @JsonProperty("clOrdLinkID")
  public String clOrdLinkID;

  @JsonProperty("account")
  public long account;

  @JsonProperty("symbol")
  public String symbol;

  @JsonProperty("side")
  public String side;

  @JsonProperty("lastQty")
  public BigDecimal lastQty;

  @JsonProperty("lastPx")
  public BigDecimal lastPx;

  @JsonProperty("underlyingLastPx")
  public BigDecimal underlyingLastPx;

  @JsonProperty("lastMkt")
  public String lastMkt;

  @JsonProperty("lastLiquidityInd")
  public String lastLiquidityInd;

  @JsonProperty("simpleOrderQty")
  public BigDecimal simpleOrderQty;

  @JsonProperty("orderQty")
  public BigDecimal orderQty;

  @JsonProperty("price")
  public BigDecimal price;

  @JsonProperty("displayQty")
  public BigDecimal displayQty;

  @JsonProperty("stopPx")
  public BigDecimal stopPx;

  @JsonProperty("pegOffsetValue")
  public BigDecimal pegOffsetValue;

  @JsonProperty("pegPriceType")
  public String pegPriceType;

  @JsonProperty("currency")
  public String currency;

  @JsonProperty("settlCurrency")
  public String settlCurrency;

  @JsonProperty("execType")
  public String execType;

  @JsonProperty("ordType")
  public String ordType;

  @JsonProperty("timeInForce")
  public String timeInForce;

  @JsonProperty("execInst")
  public String execInst;

  @JsonProperty("contingencyType")
  public String contingencyType;

  @JsonProperty("exDestination")
  public String exDestination;

  @JsonProperty("ordStatus")
  public String ordStatus;

  @JsonProperty("triggered")
  public String triggered;

  @JsonProperty("workingIndicator")
  public boolean workingIndicator;

  @JsonProperty("ordRejReason")
  public String ordRejReason;

  @JsonProperty("simpleLeavesQty")
  public BigDecimal simpleLeavesQty;

  @JsonProperty("leavesQty")
  public BigDecimal leavesQty;

  @JsonProperty("simpleCumQty")
  public BigDecimal simpleCumQty;

  @JsonProperty("cumQty")
  public BigDecimal cumQty;

  @JsonProperty("avgPx")
  public BigDecimal avgPx;

  @JsonProperty("commission")
  public BigDecimal commission;

  @JsonProperty("tradePublishIndicator")
  public String tradePublishIndicator;

  @JsonProperty("multiLegReportingType")
  public String multiLegReportingType;

  @JsonProperty("text")
  public String text;

  @JsonProperty("trdMatchID")
  public String trdMatchID;

  @JsonProperty("execCost")
  public BigDecimal execCost;

  @JsonProperty("execComm")
  public BigDecimal execComm;

  @JsonProperty("homeNotional")
  public BigDecimal homeNotional;

  @JsonProperty("foreignNotional")
  public BigDecimal foreignNotional;

  @JsonProperty("transactTime")
  public Date transactTime;

  @JsonProperty("timestamp")
  public Date timestamp;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

  @Override
  public String toString() {
    return "BitmexPrivateExecution ["
        + (execID != null ? "execID=" + execID + ", " : "")
        + (orderID != null ? "orderID=" + orderID + ", " : "")
        + (clOrdID != null ? "clOrdID=" + clOrdID + ", " : "")
        + (clOrdLinkID != null ? "clOrdLinkID=" + clOrdLinkID + ", " : "")
        + "account="
        + account
        + ", "
        + (symbol != null ? "symbol=" + symbol + ", " : "")
        + (side != null ? "side=" + side + ", " : "")
        + "lastQty="
        + lastQty
        + ", lastPx="
        + lastPx
        + ", "
        + (underlyingLastPx != null ? "underlyingLastPx=" + underlyingLastPx + ", " : "")
        + (lastMkt != null ? "lastMkt=" + lastMkt + ", " : "")
        + (lastLiquidityInd != null ? "lastLiquidityInd=" + lastLiquidityInd + ", " : "")
        + (simpleOrderQty != null ? "simpleOrderQty=" + simpleOrderQty + ", " : "")
        + "orderQty="
        + orderQty
        + ", price="
        + price
        + ", "
        + (displayQty != null ? "displayQty=" + displayQty + ", " : "")
        + (stopPx != null ? "stopPx=" + stopPx + ", " : "")
        + (pegOffsetValue != null ? "pegOffsetValue=" + pegOffsetValue + ", " : "")
        + (pegPriceType != null ? "pegPriceType=" + pegPriceType + ", " : "")
        + (currency != null ? "currency=" + currency + ", " : "")
        + (settlCurrency != null ? "settlCurrency=" + settlCurrency + ", " : "")
        + (execType != null ? "execType=" + execType + ", " : "")
        + (ordType != null ? "ordType=" + ordType + ", " : "")
        + (timeInForce != null ? "timeInForce=" + timeInForce + ", " : "")
        + (execInst != null ? "execInst=" + execInst + ", " : "")
        + (contingencyType != null ? "contingencyType=" + contingencyType + ", " : "")
        + (exDestination != null ? "exDestination=" + exDestination + ", " : "")
        + (ordStatus != null ? "ordStatus=" + ordStatus + ", " : "")
        + (triggered != null ? "triggered=" + triggered + ", " : "")
        + "workingIndicator="
        + workingIndicator
        + ", "
        + (ordRejReason != null ? "ordRejReason=" + ordRejReason + ", " : "")
        + "simpleLeavesQty="
        + simpleLeavesQty
        + ", leavesQty="
        + leavesQty
        + ", simpleCumQty="
        + simpleCumQty
        + ", cumQty="
        + cumQty
        + ", avgPx="
        + avgPx
        + ", commission="
        + commission
        + ", "
        + (tradePublishIndicator != null
            ? "tradePublishIndicator=" + tradePublishIndicator + ", "
            : "")
        + (multiLegReportingType != null
            ? "multiLegReportingType=" + multiLegReportingType + ", "
            : "")
        + (text != null ? "text=" + text + ", " : "")
        + (trdMatchID != null ? "trdMatchID=" + trdMatchID + ", " : "")
        + "execCost="
        + execCost
        + ", execComm="
        + execComm
        + ", homeNotional="
        + homeNotional
        + ", foreignNotional="
        + foreignNotional
        + ", "
        + (transactTime != null ? "transactTime=" + transactTime + ", " : "")
        + (timestamp != null ? "timestamp=" + timestamp + ", " : "")
        + (additionalProperties != null ? "additionalProperties=" + additionalProperties : "")
        + "]";
  }
}
