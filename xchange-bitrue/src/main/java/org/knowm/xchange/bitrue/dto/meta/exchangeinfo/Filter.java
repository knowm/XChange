package org.knowm.xchange.bitrue.dto.meta.exchangeinfo;

public class Filter {
  private String maxPrice;

  private String filterType;

  private String priceScale;

  private String minPrice;

  private String minQty;

  private String maxQty;

  private String volumeScale;

  private String minVal;

  public String getMaxPrice() {
    return maxPrice;
  }

  public void setMaxPrice(String maxPrice) {
    this.maxPrice = maxPrice;
  }

  public String getFilterType() {
    return filterType;
  }

  public void setFilterType(String filterType) {
    this.filterType = filterType;
  }

  public String getPriceScale() {
    return priceScale;
  }

  public void setPriceScale(String priceScale) {
    this.priceScale = priceScale;
  }

  public String getMinPrice() {
    return minPrice;
  }

  public void setMinPrice(String minPrice) {
    this.minPrice = minPrice;
  }

  public String getMinQty() {
    return minQty;
  }

  public void setMinQty(String minQty) {
    this.minQty = minQty;
  }

  public String getMaxQty() {
    return maxQty;
  }

  public void setMaxQty(String maxQty) {
    this.maxQty = maxQty;
  }

  public String getVolumeScale() {
    return volumeScale;
  }

  public void setVolumeScale(String volumeScale) {
    this.volumeScale = volumeScale;
  }

  public String getMinVal() {
    return minVal;
  }

  public void setMinVal(String minNotional) {
    this.minVal = minNotional;
  }

  @Override
  public String toString() {
    return "Filter{"
        + "maxPrice='"
        + maxPrice
        + '\''
        + ", filterType='"
        + filterType
        + '\''
        + ", tickSize='"
        + priceScale
        + '\''
        + ", minPrice='"
        + minPrice
        + '\''
        + ", minQty='"
        + minQty
        + '\''
        + ", maxQty='"
        + maxQty
        + '\''
        + ", stepSize='"
        + volumeScale
        + '\''
        + ", minVal='"
        + minVal
        + '\''
        + '}';
  }
}
