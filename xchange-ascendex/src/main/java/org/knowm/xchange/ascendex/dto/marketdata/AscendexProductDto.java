package org.knowm.xchange.ascendex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class AscendexProductDto extends AscendexProductBaseDto {

  private final String baseAsset;

  private final String quoteAsset;

  private final AscendexAssetDto.AscendexAssetStatus status;

  private final boolean marginTradable;

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
      @JsonProperty("lotSize") BigDecimal lotSize) {
    super(symbol,minNotional,maxNotional,commissionType,commissionReserveRate,tickSize,lotSize);
    this.baseAsset = baseAsset;
    this.quoteAsset = quoteAsset;
    this.status = status;
    this.marginTradable = marginTradable;
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


  public boolean isMarginTradeable() {
    return marginTradable;
  }






  @Override
  public String toString() {
    return "AscendexProductDto{"
        + "symbol='"
        + super.getSymbol()
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
        + super.getMinNotional()
        + ", maxNotional="
        + super.getMaxNotional()
        + ", marginTradeable="
        + marginTradable
        + ", commissionType="
        + super.getCommissionType()
        + ", commissionReserveRate="
        + super.getCommissionReserveRate()
        + ", tickSize="
        + super.getTickSize()
        + ", lotSize="
        + super.getLotSize()
        + '}';
  }

  public enum AscendexProductCommissionType {
    Base,
    Quote,
    Received
  }
}
