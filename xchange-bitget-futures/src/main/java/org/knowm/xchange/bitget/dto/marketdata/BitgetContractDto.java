package org.knowm.xchange.bitget.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitget.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;

@Data
@Builder
@Jacksonized
public class BitgetContractDto {

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("baseCoin")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency base;

  @JsonProperty("quoteCoin")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency quote;

  @JsonProperty("buyLimitPriceRatio")
  private BigDecimal buyLimitPriceRatio;

  @JsonProperty("sellLimitPriceRatio")
  private BigDecimal sellLimitPriceRatio;

  @JsonProperty("feeRateUpRatio")
  private BigDecimal feeRateUpRatio;

  @JsonProperty("makerFeeRate")
  private BigDecimal makerFeeRate;

  @JsonProperty("takerFeeRate")
  private BigDecimal takerFeeRate;

  @JsonProperty("openCostUpRatio")
  private BigDecimal openCostUpRatio;

  @JsonProperty("supportMarginCoins")
  @JsonDeserialize(contentConverter = StringToCurrencyConverter.class)
  private List<Currency> supportMarginCoins;

  @JsonProperty("minTradeNum")
  private BigDecimal minTradeAssetAmount;

  @JsonProperty("priceEndStep")
  private Integer priceEndStep;

  @JsonProperty("volumePlace")
  private Integer assetAmountPrecision;

  @JsonProperty("pricePlace")
  private Integer pricePrecision;

  @JsonProperty("sizeMultiplier")
  private BigDecimal assetAmountStepSize;

  @JsonProperty("symbolType")
  private SymbolType symbolType;

  @JsonProperty("minTradeUSDT")
  private BigDecimal minTradeUSDT;

  @JsonProperty("maxSymbolOrderNum")
  private Integer maxSymbolOrderNum;

  @JsonProperty("maxProductOrderNum")
  private Integer maxProductOrderNum;

  @JsonProperty("maxPositionNum")
  private Integer maxPositionNum;

  @JsonProperty("symbolStatus")
  private SymbolStatus symbolStatus;

  @JsonProperty("offTime")
  private String removalTime;

  @JsonProperty("limitOpenTime")
  private String limitOpenTime;

  @JsonProperty("deliveryTime")
  private String deliveryTime;

  @JsonProperty("deliveryStartTime")
  private String deliveryStartTime;

  @JsonProperty("deliveryPeriod")
  private String deliveryPeriod;

  @JsonProperty("launchTime")
  private String launchTime;

  @JsonProperty("fundInterval")
  private Integer fundIntervalHrs;

  @JsonProperty("minLever")
  private Integer minLeverage;

  @JsonProperty("maxLever")
  private Integer maxLeverage;

  @JsonProperty("posLimit")
  private BigDecimal posLimit;

  @JsonProperty("maintainTime")
  private Instant maintainTime;

  @JsonProperty("openTime")
  private Instant openTime;

  public FuturesContract getFuturesContract() {
    return new FuturesContract(new CurrencyPair(base, quote), "PERP");
  }

  public enum SymbolType {
    @JsonProperty("perpetual")
    PERPETUAL,
    @JsonProperty("delivery")
    DELIVERY
  }

  public enum SymbolStatus {
    @JsonProperty("listed")
    LISTED,
    @JsonProperty("normal")
    NORMAL,
    @JsonProperty("maintain")
    MAINTAIN,
    @JsonProperty("limit_open")
    LIMIT_OPEN,
    @JsonProperty("restrictedAPI")
    RESTRICTEDAPI,
    @JsonProperty("off")
    OFFLINE
  }
}
