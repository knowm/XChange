package org.knowm.xchange.bitflyer.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "product_code",
  "timestamp",
  "tick_id",
  "best_bid",
  "best_ask",
  "best_bid_size",
  "best_ask_size",
  "total_bid_depth",
  "total_ask_depth",
  "ltp",
  "volume",
  "volume_by_product"
})
public class BitflyerTicker {
  @JsonProperty("product_code")
  private String productCode;

  @JsonProperty("timestamp")
  private String timestamp;

  @JsonProperty("tick_id")
  private Integer tickId;

  @JsonProperty("best_bid")
  private BigDecimal bestBid;

  @JsonProperty("best_ask")
  private BigDecimal bestAsk;

  @JsonProperty("best_bid_size")
  private BigDecimal bestBidSize;

  @JsonProperty("best_ask_size")
  private BigDecimal bestAskSize;

  @JsonProperty("total_bid_depth")
  private BigDecimal totalBidDepth;

  @JsonProperty("total_ask_depth")
  private BigDecimal totalAskDepth;

  @JsonProperty("ltp")
  private BigDecimal ltp;

  @JsonProperty("volume")
  private BigDecimal volume;

  @JsonProperty("volume_by_product")
  private BigDecimal volumeByProduct;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<>();

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public Integer getTickId() {
    return tickId;
  }

  public void setTickId(Integer tickId) {
    this.tickId = tickId;
  }

  public BigDecimal getBestBid() {
    return bestBid;
  }

  public void setBestBid(BigDecimal bestBid) {
    this.bestBid = bestBid;
  }

  public BigDecimal getBestAsk() {
    return bestAsk;
  }

  public void setBestAsk(BigDecimal bestAsk) {
    this.bestAsk = bestAsk;
  }

  public BigDecimal getBestBidSize() {
    return bestBidSize;
  }

  public void setBestBidSize(BigDecimal bestBidSize) {
    this.bestBidSize = bestBidSize;
  }

  public BigDecimal getBestAskSize() {
    return bestAskSize;
  }

  public void setBestAskSize(BigDecimal bestAskSize) {
    this.bestAskSize = bestAskSize;
  }

  public BigDecimal getTotalBidDepth() {
    return totalBidDepth;
  }

  public void setTotalBidDepth(BigDecimal totalBidDepth) {
    this.totalBidDepth = totalBidDepth;
  }

  public BigDecimal getTotalAskDepth() {
    return totalAskDepth;
  }

  public void setTotalAskDepth(BigDecimal totalAskDepth) {
    this.totalAskDepth = totalAskDepth;
  }

  public BigDecimal getLtp() {
    return ltp;
  }

  public void setLtp(BigDecimal ltp) {
    this.ltp = ltp;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public void setVolume(BigDecimal volume) {
    this.volume = volume;
  }

  public BigDecimal getVolumeByProduct() {
    return volumeByProduct;
  }

  public void setVolumeByProduct(BigDecimal volumeByProduct) {
    this.volumeByProduct = volumeByProduct;
  }

  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }

  public void setAdditionalProperties(Map<String, Object> additionalProperties) {
    this.additionalProperties = additionalProperties;
  }

  @Override
  public String toString() {
    return "BitflyerTicker{"
        + "productCode='"
        + productCode
        + '\''
        + ", timestamp='"
        + timestamp
        + '\''
        + ", tickId="
        + tickId
        + ", bestBid="
        + bestBid
        + ", bestAsk="
        + bestAsk
        + ", bestBidSize="
        + bestBidSize
        + ", bestAskSize="
        + bestAskSize
        + ", totalBidDepth="
        + totalBidDepth
        + ", totalAskDepth="
        + totalAskDepth
        + ", ltp="
        + ltp
        + ", volume="
        + volume
        + ", volumeByProduct="
        + volumeByProduct
        + ", additionalProperties="
        + additionalProperties
        + '}';
  }
}
