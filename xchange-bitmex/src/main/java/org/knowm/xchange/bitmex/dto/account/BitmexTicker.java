package org.knowm.xchange.bitmex.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;

@Data
@Builder
@Jacksonized
public class BitmexTicker {

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("rootSymbol")
  private String rootSymbol;

  @JsonProperty("state")
  private State state;

  @JsonProperty("typ")
  private SymbolType symbolType;

  @JsonProperty("listing")
  private ZonedDateTime listing;

  @JsonProperty("front")
  private ZonedDateTime front;

  @JsonProperty("expiry")
  private ExpirationInfo expirationInfo;

  @JsonProperty("settle")
  private ZonedDateTime settle;

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
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency underlying;

  @JsonProperty("quoteCurrency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency quoteCurrency;

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
  private ZonedDateTime fundingTimestamp;

  @JsonProperty("fundingInterval")
  private ZonedDateTime fundingInterval;

  @JsonProperty("fundingRate")
  private BigDecimal fundingRate;

  @JsonProperty("indicativeFundingRate")
  private BigDecimal indicativeFundingRate;

  @JsonProperty("rebalanceTimestamp")
  private String rebalanceTimestamp;

  @JsonProperty("rebalanceInterval")
  private String rebalanceInterval;

  @JsonProperty("openingTimestamp")
  private ZonedDateTime openingTimestamp;

  @JsonProperty("closingTimestamp")
  private ZonedDateTime closingTimestamp;

  @JsonProperty("sessionInterval")
  private ZonedDateTime sessionInterval;

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

  @JsonProperty("homeNotional24h")
  private BigDecimal assetVolume24h;

  @JsonProperty("foreignNotional24h")
  private BigDecimal quoteVolume24h;

  @JsonProperty("prevPrice24h")
  private BigInteger prevPrice24h;

  @JsonProperty("vwap")
  private BigDecimal vwap;

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
  private ZonedDateTime timestamp;

  public Instrument getInstrument() {
    return new CurrencyPair(underlying, quoteCurrency);
  }

  public static enum SymbolType {
    @JsonProperty("FFCCSX")
    FUTURES,

    @JsonProperty("FFWCSX")
    PERPETUALS,

    @JsonProperty("IFXXXP")
    SPOT,

    @JsonEnumDefaultValue
    UNKNOWN,
  }

  public static enum State {
    @JsonProperty("Open")
    OPEN,

    @JsonProperty("Closed")
    CLOSED,

    @JsonProperty("Unlisted")
    UNLISTED,

    @JsonProperty("Expired")
    EXPIRED,

    @JsonProperty("Settled")
    SETTLED,

    @JsonProperty("Cleared")
    CLEARED,
  }

  @Data
  public static class ExpirationInfo {

    private ZonedDateTime expirationDate;

    private String futuresCode;

    @JsonCreator
    public ExpirationInfo(ZonedDateTime expiry) {
      expirationDate = expiry;
      futuresCode = BitmexAdapters.toFuturesCode(expiry);
    }
  }
}
