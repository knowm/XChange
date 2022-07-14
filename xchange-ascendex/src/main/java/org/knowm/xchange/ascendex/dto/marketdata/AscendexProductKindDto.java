package org.knowm.xchange.ascendex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class AscendexProductKindDto extends AscendexProductBaseDto {


  private final String	displayName;
  private final String	domain;
  private final Long	tradingStartTime;
  private final String	collapseDecimals;
  private final BigDecimal	minQty;
  private final BigDecimal	maxQty;
  private final AscendexAssetDto.AscendexAssetStatus	statusCode;
  private final String	statusMessage;
  private final boolean	useTick;
  private final boolean	useLot;
  private final BigDecimal	qtyScale;
  private final BigDecimal	priceScale;
  private final BigDecimal	notionalScale;


  public AscendexProductKindDto(@JsonProperty("symbol") String symbol,
                                @JsonProperty("minNotional") BigDecimal minNotional,
                                @JsonProperty("maxNotional") BigDecimal maxNotional,
                                @JsonProperty("commissionType") AscendexProductDto.AscendexProductCommissionType commissionType,
                                @JsonProperty("commissionReserveRate") BigDecimal commissionReserveRate,
                                @JsonProperty("tickSize") BigDecimal tickSize,
                                @JsonProperty("lotSize") BigDecimal lotSize,
                                @JsonProperty("displayName") String displayName,
                                @JsonProperty("domain") String domain,
                                @JsonProperty("tradingStartTime") Long tradingStartTime,
                                @JsonProperty("collapseDecimals") String collapseDecimals,
                                @JsonProperty("minQty") BigDecimal minQty,
                                @JsonProperty("maxQty") BigDecimal maxQty,
                                @JsonProperty("statusCode") AscendexAssetDto.AscendexAssetStatus statusCode,
                                @JsonProperty("statusMessage") String statusMessage,
                                @JsonProperty("useTick") boolean useTick,
                                @JsonProperty("useLot") boolean useLot,
                                @JsonProperty("qtyScale") BigDecimal qtyScale,
                                @JsonProperty("priceScale") BigDecimal priceScale,
                                @JsonProperty("notionalScale") BigDecimal notionalScale) {
    super(symbol, minNotional, maxNotional, commissionType, commissionReserveRate, tickSize, lotSize);
    this.displayName = displayName;
    this.domain = domain;
    this.tradingStartTime = tradingStartTime;
    this.collapseDecimals = collapseDecimals;
    this.minQty = minQty;
    this.maxQty = maxQty;
    this.statusCode = statusCode;
    this.statusMessage = statusMessage;
    this.useTick = useTick;
    this.useLot = useLot;
    this.qtyScale = qtyScale;
    this.priceScale = priceScale;
    this.notionalScale = notionalScale;
  }

  @Override
  public String toString() {
    return "AscendexProductKindDto{" +
             "symbol='"+
             super.getSymbol()+ '\'' +
            "displayName='" + displayName + '\'' +
            ", domain='" + domain + '\'' +
            ", tradingStartTime=" + tradingStartTime +
            ", collapseDecimals='" + collapseDecimals + '\'' +
            ", minQty=" + minQty +
            ", maxQty=" + maxQty +
            ", statusCode=" + statusCode +
            ", statusMessage='" + statusMessage + '\'' +
            ", useTick=" + useTick +
            ", useLot=" + useLot +
            ", qtyScale=" + qtyScale +
            ", priceScale=" + priceScale +
            ", notionalScale=" + notionalScale +
             ", minNotional="
            + super.getMinNotional()
            + ", maxNotional="
            + super.getMaxNotional()
            + ", commissionType="
            + super.getCommissionType()
            + ", commissionReserveRate="
            + super.getCommissionReserveRate()
            + ", tickSize="
            + super.getTickSize()
            + ", lotSize="
            + super.getLotSize()+
            '}';
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getDomain() {
    return domain;
  }

  public Long getTradingStartTime() {
    return tradingStartTime;
  }

  public String getCollapseDecimals() {
    return collapseDecimals;
  }

  public BigDecimal getMinQty() {
    return minQty;
  }

  public BigDecimal getMaxQty() {
    return maxQty;
  }

  public AscendexAssetDto.AscendexAssetStatus getStatusCode() {
    return statusCode;
  }

  public String getStatusMessage() {
    return statusMessage;
  }

  public boolean isUseTick() {
    return useTick;
  }

  public boolean isUseLot() {
    return useLot;
  }

  public BigDecimal getQtyScale() {
    return qtyScale;
  }

  public BigDecimal getPriceScale() {
    return priceScale;
  }

  public BigDecimal getNotionalScale() {
    return notionalScale;
  }
}
