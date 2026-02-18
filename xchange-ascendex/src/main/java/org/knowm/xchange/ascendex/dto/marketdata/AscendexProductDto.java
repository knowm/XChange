package org.knowm.xchange.ascendex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class AscendexProductDto {

  private final String symbol;

  private final String baseAsset;

  private final String quoteAsset;

  private final AscendexAssetDto.AscendexAssetStatus status;

  private final BigDecimal minNotional;

  private final BigDecimal maxNotional;

  private final boolean marginTradable;

  private final AscendexProductCommissionType commissionType;

  private final BigDecimal commissionReserveRate;

  private final BigDecimal tickSize;

  private final BigDecimal lotSize;

  private final BigDecimal minQty;

  private final BigDecimal maxQty;

  public AscendexProductDto(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("baseAsset") String baseAsset,
      @JsonProperty("quoteAsset") String quoteAsset,
      @JsonProperty("status") AscendexAssetDto.AscendexAssetStatus status,
      @JsonProperty("minNotional") BigDecimal minNotional,
      @JsonProperty("maxNotional") BigDecimal maxNotional,
      @JsonProperty("marginTradable") boolean marginTradable,
      @JsonProperty("commissionType") AscendexProductCommissionType commissionType,
      @JsonProperty("commissionReserveRate") BigDecimal commissionReserveRate,
      @JsonProperty("tickSize") BigDecimal tickSize,
      @JsonProperty("lotSize") BigDecimal lotSize,
      @JsonProperty("minQty") BigDecimal minQty,
      @JsonProperty("maxQty") BigDecimal maxQty) {
    this.symbol = symbol;
    this.baseAsset = baseAsset;
    this.quoteAsset = quoteAsset;
    this.status = status;
    this.minNotional = minNotional;
    this.maxNotional = maxNotional;
    this.marginTradable = marginTradable;
    this.commissionType = commissionType;
    this.commissionReserveRate = commissionReserveRate;
    this.tickSize = tickSize;
    this.lotSize = lotSize;
    this.minQty = minQty;
    this.maxQty = maxQty;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getBaseAsset() {
    return baseAsset;
  }

  public String getQuoteAsset() {
    return quoteAsset;
  }

  public AscendexAssetDto.AscendexAssetStatus getStatus() {
    return status;
  }

  public BigDecimal getMinNotional() {
    return minNotional;
  }

  public BigDecimal getMaxNotional() {
    return maxNotional;
  }

  public boolean isMarginTradeable() {
    return marginTradable;
  }

  public AscendexProductCommissionType getCommissionType() {
    return commissionType;
  }

  public BigDecimal getCommissionReserveRate() {
    return commissionReserveRate;
  }

  public BigDecimal getTickSize() {
    return tickSize;
  }

  public BigDecimal getLotSize() {
    return lotSize;
  }

  public BigDecimal getMinQty() {
    return minQty;
  }

  public BigDecimal getMaxQty() {
    return maxQty;
  }

  @Override
  public String toString() {
    return "AscendexProductDto{"
        + "symbol='"
        + symbol
        + '\''
        + ", baseAsset='"
        + baseAsset
        + '\''
        + ", quoteAsset='"
        + quoteAsset
        + '\''
        + ", status="
        + status
        + ", minNotional="
        + minNotional
        + ", maxNotional="
        + maxNotional
        + ", marginTradeable="
        + marginTradable
        + ", commissionType="
        + commissionType
        + ", commissionReserveRate="
        + commissionReserveRate
        + ", tickSize="
        + tickSize
        + ", lotSize="
        + lotSize
        + ", minQty="
        + minQty
        + ", maxQty="
        + maxQty
        + '}';
  }

  public enum AscendexProductCommissionType {
    Base,
    Quote,
    Received
  }
}
