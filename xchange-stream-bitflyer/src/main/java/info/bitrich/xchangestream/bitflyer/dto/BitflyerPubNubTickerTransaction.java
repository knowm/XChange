package info.bitrich.xchangestream.bitflyer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** Created by Lukas Zaoralek on 15.11.17. */
public class BitflyerPubNubTickerTransaction {
  private final String productCode;
  private final String timestamp;
  private final String tickId;
  private final BigDecimal bestBid;
  private final BigDecimal bestAsk;
  private final BigDecimal bestBidSize;
  private final BigDecimal bestAskSize;
  private final BigDecimal totalBidDepth;
  private final BigDecimal totalAskDepth;
  private final String ltp;
  private final BigDecimal volume;
  private final BigDecimal volumeByProduct;

  public BitflyerPubNubTickerTransaction(
      @JsonProperty("product_code") String productCode,
      @JsonProperty("timestamp") String timestamp,
      @JsonProperty("tick_id") String tickId,
      @JsonProperty("best_bid") BigDecimal bestBid,
      @JsonProperty("best_ask") BigDecimal bestAsk,
      @JsonProperty("best_bid_size") BigDecimal bestBidSize,
      @JsonProperty("best_ask_size") BigDecimal bestAskSize,
      @JsonProperty("total_bid_depth") BigDecimal totalBidDepth,
      @JsonProperty("total_ask_depth") BigDecimal totalAskDepth,
      @JsonProperty("ltp") String ltp,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("volume_by_product") BigDecimal volumeByProduct) {
    this.productCode = productCode;
    this.timestamp = timestamp;
    this.tickId = tickId;
    this.bestBid = bestBid;
    this.bestAsk = bestAsk;
    this.bestBidSize = bestBidSize;
    this.bestAskSize = bestAskSize;
    this.totalBidDepth = totalBidDepth;
    this.totalAskDepth = totalAskDepth;
    this.ltp = ltp;
    this.volume = volume;
    this.volumeByProduct = volumeByProduct;
  }

  public BitflyerTicker toBitflyerTicker() {
    return new BitflyerTicker(
        productCode,
        timestamp,
        tickId,
        bestBid,
        bestAsk,
        bestBidSize,
        bestAskSize,
        totalBidDepth,
        totalAskDepth,
        ltp,
        volume,
        volumeByProduct);
  }

  public String getProductCode() {
    return productCode;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getTickId() {
    return tickId;
  }

  public BigDecimal getBestBid() {
    return bestBid;
  }

  public BigDecimal getBestAsk() {
    return bestAsk;
  }

  public BigDecimal getBestBidSize() {
    return bestBidSize;
  }

  public BigDecimal getBestAskSize() {
    return bestAskSize;
  }

  public BigDecimal getTotalBidDepth() {
    return totalBidDepth;
  }

  public BigDecimal getTotalAskDepth() {
    return totalAskDepth;
  }

  public String getLtp() {
    return ltp;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getVolumeByProduct() {
    return volumeByProduct;
  }
}
