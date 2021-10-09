package org.knowm.xchange.binance.futures.dto.meta;

import org.knowm.xchange.binance.dto.meta.exchangeinfo.Filter;

import java.util.Arrays;

public class FuturesSymbol {

  private String baseAsset;
  private String baseAssetPrecision;
  private String contractType;
  private String deliveryDate;
  private Filter[] filters;
  private String liquidationFee;
  private String maintMarginPercent;
  private String marginAsset;
  private String marketTakeBound;
  private String onboardDate;
  private String[] orderTypes;
  private String pair;
  private String pricePrecision;
  private String quantityPrecision;
  private String quoteAsset;
  private String quotePrecision;
  private String requiredMarginPercent;
  private String settlePlan;
  private String status;
  private String symbol;
  private String[] timeInForce;
  private String triggerProtect;
  private String[] underlyingSubType;
  private String underlyingType;

  public String getBaseAsset() {
    return baseAsset;
  }

  public void setBaseAsset(String baseAsset) {
    this.baseAsset = baseAsset;
  }

  public String getBaseAssetPrecision() {
    return baseAssetPrecision;
  }

  public void setBaseAssetPrecision(String baseAssetPrecision) {
    this.baseAssetPrecision = baseAssetPrecision;
  }

  public String getContractType() {
    return contractType;
  }

  public void setContractType(String contractType) {
    this.contractType = contractType;
  }

  public String getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(String deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  public Filter[] getFilters() {
    return filters;
  }

  public void setFilters(Filter[] filters) {
    this.filters = filters;
  }

  public String getLiquidationFee() {
    return liquidationFee;
  }

  public void setLiquidationFee(String liquidationFee) {
    this.liquidationFee = liquidationFee;
  }

  public String getMaintMarginPercent() {
    return maintMarginPercent;
  }

  public void setMaintMarginPercent(String maintMarginPercent) {
    this.maintMarginPercent = maintMarginPercent;
  }

  public String getMarginAsset() {
    return marginAsset;
  }

  public void setMarginAsset(String marginAsset) {
    this.marginAsset = marginAsset;
  }

  public String getMarketTakeBound() {
    return marketTakeBound;
  }

  public void setMarketTakeBound(String marketTakeBound) {
    this.marketTakeBound = marketTakeBound;
  }

  public String getOnboardDate() {
    return onboardDate;
  }

  public void setOnboardDate(String onboardDate) {
    this.onboardDate = onboardDate;
  }

  public String[] getOrderTypes() {
    return orderTypes;
  }

  public void setOrderTypes(String[] orderTypes) {
    this.orderTypes = orderTypes;
  }

  public String getPair() {
    return pair;
  }

  public void setPair(String pair) {
    this.pair = pair;
  }

  public String getPricePrecision() {
    return pricePrecision;
  }

  public void setPricePrecision(String pricePrecision) {
    this.pricePrecision = pricePrecision;
  }

  public String getQuantityPrecision() {
    return quantityPrecision;
  }

  public void setQuantityPrecision(String quantityPrecision) {
    this.quantityPrecision = quantityPrecision;
  }

  public String getQuoteAsset() {
    return quoteAsset;
  }

  public void setQuoteAsset(String quoteAsset) {
    this.quoteAsset = quoteAsset;
  }

  public String getQuotePrecision() {
    return quotePrecision;
  }

  public void setQuotePrecision(String quotePrecision) {
    this.quotePrecision = quotePrecision;
  }

  public String getRequiredMarginPercent() {
    return requiredMarginPercent;
  }

  public void setRequiredMarginPercent(String requiredMarginPercent) {
    this.requiredMarginPercent = requiredMarginPercent;
  }

  public String getSettlePlan() {
    return settlePlan;
  }

  public void setSettlePlan(String settlePlan) {
    this.settlePlan = settlePlan;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String[] getTimeInForce() {
    return timeInForce;
  }

  public void setTimeInForce(String[] timeInForce) {
    this.timeInForce = timeInForce;
  }

  public String getTriggerProtect() {
    return triggerProtect;
  }

  public void setTriggerProtect(String triggerProtect) {
    this.triggerProtect = triggerProtect;
  }

  public String[] getUnderlyingSubType() {
    return underlyingSubType;
  }

  public void setUnderlyingSubType(String[] underlyingSubType) {
    this.underlyingSubType = underlyingSubType;
  }

  public String getUnderlyingType() {
    return underlyingType;
  }

  public void setUnderlyingType(String underlyingType) {
    this.underlyingType = underlyingType;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("FuturesSymbol{");
    sb.append("baseAsset='").append(baseAsset).append('\'');
    sb.append(", baseAssetPrecision='").append(baseAssetPrecision).append('\'');
    sb.append(", contractType='").append(contractType).append('\'');
    sb.append(", deliveryDate='").append(deliveryDate).append('\'');
    sb.append(", filters=").append(Arrays.toString(filters));
    sb.append(", liquidationFee='").append(liquidationFee).append('\'');
    sb.append(", maintMarginPercent='").append(maintMarginPercent).append('\'');
    sb.append(", marginAsset='").append(marginAsset).append('\'');
    sb.append(", marketTakeBound='").append(marketTakeBound).append('\'');
    sb.append(", onboardDate='").append(onboardDate).append('\'');
    sb.append(", orderTypes=").append(Arrays.toString(orderTypes));
    sb.append(", pair='").append(pair).append('\'');
    sb.append(", pricePrecision='").append(pricePrecision).append('\'');
    sb.append(", quantityPrecision='").append(quantityPrecision).append('\'');
    sb.append(", quoteAsset='").append(quoteAsset).append('\'');
    sb.append(", quotePrecision='").append(quotePrecision).append('\'');
    sb.append(", requiredMarginPercent='").append(requiredMarginPercent).append('\'');
    sb.append(", settlePlan='").append(settlePlan).append('\'');
    sb.append(", status='").append(status).append('\'');
    sb.append(", symbol='").append(symbol).append('\'');
    sb.append(", timeInForce=").append(Arrays.toString(timeInForce));
    sb.append(", triggerProtect='").append(triggerProtect).append('\'');
    sb.append(", underlyingSubType=").append(Arrays.toString(underlyingSubType));
    sb.append(", underlyingType='").append(underlyingType).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
