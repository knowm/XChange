package org.knowm.xchange.bitmex.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "symbol",
  "rootSymbol",
  "state",
  "typ",
  "listing",
  "front",
  "expiry",
  "settle",
  "relistInterval",
  "inverseLeg",
  "sellLeg",
  "buyLeg",
  "positionCurrency",
  "underlying",
  "quoteCurrency",
  "underlyingSymbol",
  "reference",
  "referenceSymbol",
  "calcInterval",
  "publishInterval",
  "publishTime",
  "maxOrderQty",
  "maxPrice",
  "lotSize",
  "tickSize",
  "multiplier",
  "settlCurrency",
  "underlyingToPositionMultiplier",
  "underlyingToSettleMultiplier",
  "quoteToSettleMultiplier",
  "isQuanto",
  "isInverse",
  "initMargin",
  "maintMargin",
  "riskLimit",
  "riskStep",
  "limit",
  "capped",
  "taxed",
  "deleverage",
  "makerFee",
  "takerFee",
  "settlementFee",
  "insuranceFee",
  "fundingBaseSymbol",
  "fundingQuoteSymbol",
  "fundingPremiumSymbol",
  "fundingTimestamp",
  "fundingInterval",
  "fundingRate",
  "indicativeFundingRate",
  "rebalanceTimestamp",
  "rebalanceInterval",
  "openingTimestamp",
  "closingTimestamp",
  "sessionInterval",
  "prevClosePrice",
  "limitDownPrice",
  "limitUpPrice",
  "bankruptLimitDownPrice",
  "bankruptLimitUpPrice",
  "prevTotalVolume",
  "totalVolume",
  "volume",
  "volume24h",
  "prevTotalTurnover",
  "totalTurnover",
  "turnover",
  "turnover24h",
  "prevPrice24h",
  "vwap",
  "highPrice",
  "lowPrice",
  "lastPrice",
  "lastPriceProtected",
  "lastTickDirection",
  "lastChangePcnt",
  "bidPrice",
  "midPrice",
  "askPrice",
  "impactBidPrice",
  "impactMidPrice",
  "impactAskPrice",
  "hasLiquidity",
  "openInterest",
  "openValue",
  "fairMethod",
  "fairBasisRate",
  "fairBasis",
  "fairPrice",
  "markMethod",
  "markPrice",
  "indicativeTaxRate",
  "indicativeSettlePrice",
  "settledPrice",
  "timestamp"
})
public class BitmexTicker {

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("rootSymbol")
  private String rootSymbol;

  @JsonProperty("state")
  private String state;

  @JsonProperty("typ")
  private String typ;

  @JsonProperty("listing")
  private String listing;

  @JsonProperty("front")
  private String front;

  @JsonProperty("expiry")
  private String expiry;

  @JsonProperty("settle")
  private String settle;

  @JsonProperty("relistInterval")
  private String relistInterval;

  @JsonProperty("inverseLeg")
  private String inverseLeg;

  @JsonProperty("sellLeg")
  private String sellLeg;

  @JsonProperty("buyLeg")
  private String buyLeg;

  @JsonProperty("positionCurrency")
  private String positionCurrency;

  @JsonProperty("underlying")
  private String underlying;

  @JsonProperty("quoteCurrency")
  private String quoteCurrency;

  @JsonProperty("underlyingSymbol")
  private String underlyingSymbol;

  @JsonProperty("reference")
  private String reference;

  @JsonProperty("referenceSymbol")
  private String referenceSymbol;

  @JsonProperty("calcInterval")
  private String calcInterval;

  @JsonProperty("publishInterval")
  private String publishInterval;

  @JsonProperty("publishTime")
  private String publishTime;

  @JsonProperty("maxOrderQty")
  private BigDecimal maxOrderQty;

  @JsonProperty("maxPrice")
  private BigDecimal maxPrice;

  @JsonProperty("lotSize")
  private BigDecimal lotSize;

  @JsonProperty("tickSize")
  private BigDecimal tickSize;

  @JsonProperty("multiplier")
  private BigDecimal multiplier;

  @JsonProperty("settlCurrency")
  private String settlCurrency;

  @JsonProperty("underlyingToPositionMultiplier")
  private BigDecimal underlyingToPositionMultiplier;

  @JsonProperty("underlyingToSettleMultiplier")
  private BigDecimal underlyingToSettleMultiplier;

  @JsonProperty("quoteToSettleMultiplier")
  private BigDecimal quoteToSettleMultiplier;

  @JsonProperty("isQuanto")
  private Boolean isQuanto;

  @JsonProperty("isInverse")
  private Boolean isInverse;

  @JsonProperty("initMargin")
  private BigDecimal initMargin;

  @JsonProperty("maintMargin")
  private BigDecimal maintMargin;

  @JsonProperty("riskLimit")
  private BigInteger riskLimit;

  @JsonProperty("riskStep")
  private BigInteger riskStep;

  @JsonProperty("limit")
  private BigDecimal limit;

  @JsonProperty("capped")
  private Boolean capped;

  @JsonProperty("taxed")
  private Boolean taxed;

  @JsonProperty("deleverage")
  private Boolean deleverage;

  @JsonProperty("makerFee")
  private BigDecimal makerFee;

  @JsonProperty("takerFee")
  private BigDecimal takerFee;

  @JsonProperty("settlementFee")
  private BigDecimal settlementFee;

  @JsonProperty("insuranceFee")
  private BigDecimal insuranceFee;

  @JsonProperty("fundingBaseSymbol")
  private String fundingBaseSymbol;

  @JsonProperty("fundingQuoteSymbol")
  private String fundingQuoteSymbol;

  @JsonProperty("fundingPremiumSymbol")
  private String fundingPremiumSymbol;

  @JsonProperty("fundingTimestamp")
  private String fundingTimestamp;

  @JsonProperty("fundingInterval")
  private String fundingInterval;

  @JsonProperty("fundingRate")
  private BigDecimal fundingRate;

  @JsonProperty("indicativeFundingRate")
  private BigDecimal indicativeFundingRate;

  @JsonProperty("rebalanceTimestamp")
  private String rebalanceTimestamp;

  @JsonProperty("rebalanceInterval")
  private String rebalanceInterval;

  @JsonProperty("openingTimestamp")
  private String openingTimestamp;

  @JsonProperty("closingTimestamp")
  private String closingTimestamp;

  @JsonProperty("sessionInterval")
  private String sessionInterval;

  @JsonProperty("prevClosePrice")
  private BigDecimal prevClosePrice;

  @JsonProperty("limitDownPrice")
  private BigDecimal limitDownPrice;

  @JsonProperty("limitUpPrice")
  private BigDecimal limitUpPrice;

  @JsonProperty("bankruptLimitDownPrice")
  private BigDecimal bankruptLimitDownPrice;

  @JsonProperty("bankruptLimitUpPrice")
  private BigDecimal bankruptLimitUpPrice;

  @JsonProperty("prevTotalVolume")
  private BigDecimal prevTotalVolume;

  @JsonProperty("totalVolume")
  private BigDecimal totalVolume;

  @JsonProperty("volume")
  private BigDecimal volume;

  @JsonProperty("volume24h")
  private BigDecimal volume24h;

  @JsonProperty("prevTotalTurnover")
  private BigInteger prevTotalTurnover;

  @JsonProperty("totalTurnover")
  private BigInteger totalTurnover;

  @JsonProperty("turnover")
  private BigInteger turnover;

  @JsonProperty("turnover24h")
  private BigInteger turnover24h;

  @JsonProperty("prevPrice24h")
  private BigInteger prevPrice24h;

  @JsonProperty("vwap")
  private BigInteger vwap;

  @JsonProperty("highPrice")
  private BigDecimal highPrice;

  @JsonProperty("lowPrice")
  private BigDecimal lowPrice;

  @JsonProperty("lastPrice")
  private BigDecimal lastPrice;

  @JsonProperty("lastPriceProtected")
  private BigDecimal lastPriceProtected;

  @JsonProperty("lastTickDirection")
  private String lastTickDirection;

  @JsonProperty("lastChangePcnt")
  private BigDecimal lastChangePcnt;

  @JsonProperty("bidPrice")
  private BigDecimal bidPrice;

  @JsonProperty("midPrice")
  private BigDecimal midPrice;

  @JsonProperty("askPrice")
  private BigDecimal askPrice;

  @JsonProperty("impactBidPrice")
  private BigDecimal impactBidPrice;

  @JsonProperty("impactMidPrice")
  private BigDecimal impactMidPrice;

  @JsonProperty("impactAskPrice")
  private BigDecimal impactAskPrice;

  @JsonProperty("hasLiquidity")
  private Boolean hasLiquidity;

  @JsonProperty("openInterest")
  private BigDecimal openInterest;

  @JsonProperty("openValue")
  private BigDecimal openValue;

  @JsonProperty("fairMethod")
  private String fairMethod;

  @JsonProperty("fairBasisRate")
  private BigDecimal fairBasisRate;

  @JsonProperty("fairBasis")
  private BigDecimal fairBasis;

  @JsonProperty("fairPrice")
  private BigDecimal fairPrice;

  @JsonProperty("markMethod")
  private String markMethod;

  @JsonProperty("markPrice")
  private BigDecimal markPrice;

  @JsonProperty("indicativeTaxRate")
  private BigDecimal indicativeTaxRate;

  @JsonProperty("indicativeSettlePrice")
  private BigDecimal indicativeSettlePrice;

  @JsonProperty("settledPrice")
  private BigDecimal settledPrice;

  @JsonProperty("timestamp")
  private String timestamp;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<>();

  public String getSymbol() {
    return symbol;
  }

  public String getRootSymbol() {
    return rootSymbol;
  }

  public String getState() {
    return state;
  }

  public String getTyp() {
    return typ;
  }

  public String getListing() {
    return listing;
  }

  public String getFront() {
    return front;
  }

  public String getExpiry() {
    return expiry;
  }

  public String getSettle() {
    return settle;
  }

  public String getRelistInterval() {
    return relistInterval;
  }

  public String getInverseLeg() {
    return inverseLeg;
  }

  public String getSellLeg() {
    return sellLeg;
  }

  public String getBuyLeg() {
    return buyLeg;
  }

  public String getPositionCurrency() {
    return positionCurrency;
  }

  public String getUnderlying() {
    return underlying;
  }

  public String getQuoteCurrency() {
    return quoteCurrency;
  }

  public String getUnderlyingSymbol() {
    return underlyingSymbol;
  }

  public String getReference() {
    return reference;
  }

  public String getReferenceSymbol() {
    return referenceSymbol;
  }

  public String getCalcInterval() {
    return calcInterval;
  }

  public String getPublishInterval() {
    return publishInterval;
  }

  public String getPublishTime() {
    return publishTime;
  }

  public BigDecimal getMaxOrderQty() {
    return maxOrderQty;
  }

  public BigDecimal getMaxPrice() {
    return maxPrice;
  }

  public BigDecimal getLotSize() {
    return lotSize;
  }

  public BigDecimal getTickSize() {
    return tickSize;
  }

  public BigDecimal getMultiplier() {
    return multiplier;
  }

  public String getSettlCurrency() {
    return settlCurrency;
  }

  public BigDecimal getUnderlyingToPositionMultiplier() {
    return underlyingToPositionMultiplier;
  }

  public BigDecimal getUnderlyingToSettleMultiplier() {
    return underlyingToSettleMultiplier;
  }

  public BigDecimal getQuoteToSettleMultiplier() {
    return quoteToSettleMultiplier;
  }

  public Boolean getQuanto() {
    return isQuanto;
  }

  public Boolean getInverse() {
    return isInverse;
  }

  public BigDecimal getInitMargin() {
    return initMargin;
  }

  public BigDecimal getMaintMargin() {
    return maintMargin;
  }

  public BigInteger getRiskLimit() {
    return riskLimit;
  }

  public BigInteger getRiskStep() {
    return riskStep;
  }

  public BigDecimal getLimit() {
    return limit;
  }

  public Boolean getCapped() {
    return capped;
  }

  public Boolean getTaxed() {
    return taxed;
  }

  public Boolean getDeleverage() {
    return deleverage;
  }

  public BigDecimal getMakerFee() {
    return makerFee;
  }

  public BigDecimal getTakerFee() {
    return takerFee;
  }

  public BigDecimal getSettlementFee() {
    return settlementFee;
  }

  public BigDecimal getInsuranceFee() {
    return insuranceFee;
  }

  public String getFundingBaseSymbol() {
    return fundingBaseSymbol;
  }

  public String getFundingQuoteSymbol() {
    return fundingQuoteSymbol;
  }

  public String getFundingPremiumSymbol() {
    return fundingPremiumSymbol;
  }

  public String getFundingTimestamp() {
    return fundingTimestamp;
  }

  public String getFundingInterval() {
    return fundingInterval;
  }

  public BigDecimal getFundingRate() {
    return fundingRate;
  }

  public BigDecimal getIndicativeFundingRate() {
    return indicativeFundingRate;
  }

  public String getRebalanceTimestamp() {
    return rebalanceTimestamp;
  }

  public String getRebalanceInterval() {
    return rebalanceInterval;
  }

  public String getOpeningTimestamp() {
    return openingTimestamp;
  }

  public String getClosingTimestamp() {
    return closingTimestamp;
  }

  public String getSessionInterval() {
    return sessionInterval;
  }

  public BigDecimal getPrevClosePrice() {
    return prevClosePrice;
  }

  public BigDecimal getLimitDownPrice() {
    return limitDownPrice;
  }

  public BigDecimal getLimitUpPrice() {
    return limitUpPrice;
  }

  public BigDecimal getBankruptLimitDownPrice() {
    return bankruptLimitDownPrice;
  }

  public BigDecimal getBankruptLimitUpPrice() {
    return bankruptLimitUpPrice;
  }

  public BigDecimal getPrevTotalVolume() {
    return prevTotalVolume;
  }

  public BigDecimal getTotalVolume() {
    return totalVolume;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getVolume24h() {
    return volume24h;
  }

  public BigInteger getPrevTotalTurnover() {
    return prevTotalTurnover;
  }

  public BigInteger getTotalTurnover() {
    return totalTurnover;
  }

  public BigInteger getTurnover() {
    return turnover;
  }

  public BigInteger getTurnover24h() {
    return turnover24h;
  }

  public BigInteger getPrevPrice24h() {
    return prevPrice24h;
  }

  public BigInteger getVwap() {
    return vwap;
  }

  public BigDecimal getHighPrice() {
    return highPrice;
  }

  public BigDecimal getLowPrice() {
    return lowPrice;
  }

  public BigDecimal getLastPrice() {
    return lastPrice;
  }

  public BigDecimal getLastPriceProtected() {
    return lastPriceProtected;
  }

  public String getLastTickDirection() {
    return lastTickDirection;
  }

  public BigDecimal getLastChangePcnt() {
    return lastChangePcnt;
  }

  public BigDecimal getBidPrice() {
    return bidPrice;
  }

  public BigDecimal getMidPrice() {
    return midPrice;
  }

  public BigDecimal getAskPrice() {
    return askPrice;
  }

  public BigDecimal getImpactBidPrice() {
    return impactBidPrice;
  }

  public BigDecimal getImpactMidPrice() {
    return impactMidPrice;
  }

  public BigDecimal getImpactAskPrice() {
    return impactAskPrice;
  }

  public Boolean getHasLiquidity() {
    return hasLiquidity;
  }

  public BigDecimal getOpenInterest() {
    return openInterest;
  }

  public BigDecimal getOpenValue() {
    return openValue;
  }

  public String getFairMethod() {
    return fairMethod;
  }

  public BigDecimal getFairBasisRate() {
    return fairBasisRate;
  }

  public BigDecimal getFairBasis() {
    return fairBasis;
  }

  public BigDecimal getFairPrice() {
    return fairPrice;
  }

  public String getMarkMethod() {
    return markMethod;
  }

  public BigDecimal getMarkPrice() {
    return markPrice;
  }

  public BigDecimal getIndicativeTaxRate() {
    return indicativeTaxRate;
  }

  public BigDecimal getIndicativeSettlePrice() {
    return indicativeSettlePrice;
  }

  public BigDecimal getSettledPrice() {
    return settledPrice;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }


  @Override
  public String toString() {
    return "BitmexTicker{" +
            "symbol='" + symbol + '\'' +
            ", rootSymbol='" + rootSymbol + '\'' +
            ", state='" + state + '\'' +
            ", typ='" + typ + '\'' +
            ", listing='" + listing + '\'' +
            ", front='" + front + '\'' +
            ", expiry='" + expiry + '\'' +
            ", settle='" + settle + '\'' +
            ", relistInterval='" + relistInterval + '\'' +
            ", inverseLeg='" + inverseLeg + '\'' +
            ", sellLeg='" + sellLeg + '\'' +
            ", buyLeg='" + buyLeg + '\'' +
            ", positionCurrency='" + positionCurrency + '\'' +
            ", underlying='" + underlying + '\'' +
            ", quoteCurrency='" + quoteCurrency + '\'' +
            ", underlyingSymbol='" + underlyingSymbol + '\'' +
            ", reference='" + reference + '\'' +
            ", referenceSymbol='" + referenceSymbol + '\'' +
            ", calcInterval='" + calcInterval + '\'' +
            ", publishInterval='" + publishInterval + '\'' +
            ", publishTime='" + publishTime + '\'' +
            ", maxOrderQty=" + maxOrderQty +
            ", maxPrice=" + maxPrice +
            ", lotSize=" + lotSize +
            ", tickSize=" + tickSize +
            ", multiplier=" + multiplier +
            ", settlCurrency='" + settlCurrency + '\'' +
            ", underlyingToPositionMultiplier=" + underlyingToPositionMultiplier +
            ", underlyingToSettleMultiplier=" + underlyingToSettleMultiplier +
            ", quoteToSettleMultiplier=" + quoteToSettleMultiplier +
            ", isQuanto=" + isQuanto +
            ", isInverse=" + isInverse +
            ", initMargin=" + initMargin +
            ", maintMargin=" + maintMargin +
            ", riskLimit=" + riskLimit +
            ", riskStep=" + riskStep +
            ", limit=" + limit +
            ", capped=" + capped +
            ", taxed=" + taxed +
            ", deleverage=" + deleverage +
            ", makerFee=" + makerFee +
            ", takerFee=" + takerFee +
            ", settlementFee=" + settlementFee +
            ", insuranceFee=" + insuranceFee +
            ", fundingBaseSymbol='" + fundingBaseSymbol + '\'' +
            ", fundingQuoteSymbol='" + fundingQuoteSymbol + '\'' +
            ", fundingPremiumSymbol='" + fundingPremiumSymbol + '\'' +
            ", fundingTimestamp='" + fundingTimestamp + '\'' +
            ", fundingInterval='" + fundingInterval + '\'' +
            ", fundingRate=" + fundingRate +
            ", indicativeFundingRate=" + indicativeFundingRate +
            ", rebalanceTimestamp='" + rebalanceTimestamp + '\'' +
            ", rebalanceInterval='" + rebalanceInterval + '\'' +
            ", openingTimestamp='" + openingTimestamp + '\'' +
            ", closingTimestamp='" + closingTimestamp + '\'' +
            ", sessionInterval='" + sessionInterval + '\'' +
            ", prevClosePrice=" + prevClosePrice +
            ", limitDownPrice=" + limitDownPrice +
            ", limitUpPrice=" + limitUpPrice +
            ", bankruptLimitDownPrice=" + bankruptLimitDownPrice +
            ", bankruptLimitUpPrice=" + bankruptLimitUpPrice +
            ", prevTotalVolume=" + prevTotalVolume +
            ", totalVolume=" + totalVolume +
            ", volume=" + volume +
            ", volume24h=" + volume24h +
            ", prevTotalTurnover=" + prevTotalTurnover +
            ", totalTurnover=" + totalTurnover +
            ", turnover=" + turnover +
            ", turnover24h=" + turnover24h +
            ", prevPrice24h=" + prevPrice24h +
            ", vwap=" + vwap +
            ", highPrice=" + highPrice +
            ", lowPrice=" + lowPrice +
            ", lastPrice=" + lastPrice +
            ", lastPriceProtected=" + lastPriceProtected +
            ", lastTickDirection='" + lastTickDirection + '\'' +
            ", lastChangePcnt=" + lastChangePcnt +
            ", bidPrice=" + bidPrice +
            ", midPrice=" + midPrice +
            ", askPrice=" + askPrice +
            ", impactBidPrice=" + impactBidPrice +
            ", impactMidPrice=" + impactMidPrice +
            ", impactAskPrice=" + impactAskPrice +
            ", hasLiquidity=" + hasLiquidity +
            ", openInterest=" + openInterest +
            ", openValue=" + openValue +
            ", fairMethod='" + fairMethod + '\'' +
            ", fairBasisRate=" + fairBasisRate +
            ", fairBasis=" + fairBasis +
            ", fairPrice=" + fairPrice +
            ", markMethod='" + markMethod + '\'' +
            ", markPrice=" + markPrice +
            ", indicativeTaxRate=" + indicativeTaxRate +
            ", indicativeSettlePrice=" + indicativeSettlePrice +
            ", settledPrice=" + settledPrice +
            ", timestamp='" + timestamp + '\'' +
            ", additionalProperties=" + additionalProperties +
            '}';
  }
}
