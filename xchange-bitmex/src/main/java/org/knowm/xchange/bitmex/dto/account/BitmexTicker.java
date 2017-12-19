package org.knowm.xchange.bitmex.dto.account;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<>();
}