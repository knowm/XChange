package org.knowm.xchange.binance.dto.meta.exchangeinfo;

public class Symbol {

  private String quoteAsset;

  private String icebergAllowed;

  private String baseAsset;

  private String symbol;

  private String status;

  private String quotePrecision;

  private String baseAssetPrecision;

  private String[] orderTypes;

  private Filter[] filters;

  public String getQuoteAsset() {
    return quoteAsset;
  }

  public void setQuoteAsset(String quoteAsset) {
    this.quoteAsset = quoteAsset;
  }

  public String getIcebergAllowed() {
    return icebergAllowed;
  }

  public void setIcebergAllowed(String icebergAllowed) {
    this.icebergAllowed = icebergAllowed;
  }

  public String getBaseAsset() {
    return baseAsset;
  }

  public void setBaseAsset(String baseAsset) {
    this.baseAsset = baseAsset;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getQuotePrecision() {
    return quotePrecision;
  }

  public void setQuotePrecision(String quotePrecision) {
    this.quotePrecision = quotePrecision;
  }

  public String getBaseAssetPrecision() {
    return baseAssetPrecision;
  }

  public void setBaseAssetPrecision(String baseAssetPrecision) {
    this.baseAssetPrecision = baseAssetPrecision;
  }

  public String[] getOrderTypes() {
    return orderTypes;
  }

  public void setOrderTypes(String[] orderTypes) {
    this.orderTypes = orderTypes;
  }

  public Filter[] getFilters() {
    return filters;
  }

  public void setFilters(Filter[] filters) {
    this.filters = filters;
  }

  @Override
  public String toString() {
    return "ClassPojo [quoteAsset = "
        + quoteAsset
        + ", icebergAllowed = "
        + icebergAllowed
        + ", baseAsset = "
        + baseAsset
        + ", symbol = "
        + symbol
        + ", status = "
        + status
        + ", quotePrecision = "
        + quotePrecision
        + ", baseAssetPrecision = "
        + baseAssetPrecision
        + ", orderTypes = "
        + orderTypes
        + ", filters = "
        + filters
        + "]";
  }
}
