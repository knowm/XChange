package org.known.xchange.acx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class AcxOrder {
  /** Unique order ID */
  public final String id;
  /** Buy/Sell */
  public final String side;

  public final String ordType;
  public final BigDecimal price;
  /** Average execution price */
  public final BigDecimal avgPrice;
  /**
   * wait, done or cancel. - ‘wait’ represents the order is active, it may be a new order or partial
   * complete order; - ‘done’ means the order has been fulfilled completely; - ‘cancel’ means the
   * order has been cancelled.
   */
  public final String state;
  /** Order created time */
  public final Date createdAt;
  /** Volume to buy/sell */
  public final BigDecimal volume;
  /** remaining_volume is always less or equal than volume */
  public final BigDecimal remainingVolume;
  /** volume = remaining_volume + executed_volume */
  public final BigDecimal executedVolume;

  public final int tradesCount;
  /** the market the order belongs to, like ‘btcaud’ */
  private final String marker;

  public AcxOrder(
      @JsonProperty("id") String id,
      @JsonProperty("side") String side,
      @JsonProperty("ord_type") String ordType,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("avg_price") BigDecimal avgPrice,
      @JsonProperty("state") String state,
      @JsonProperty("market") String market,
      @JsonProperty("created_at") Date createdAt,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("remaining_volume") BigDecimal remainingVolume,
      @JsonProperty("executed_volume") BigDecimal executedVolume,
      @JsonProperty("trades_count") int tradesCount) {
    this.id = id;
    this.side = side;
    this.ordType = ordType;
    this.price = price;
    this.avgPrice = avgPrice;
    this.state = state;
    this.marker = market;
    this.createdAt = createdAt;
    this.volume = volume;
    this.remainingVolume = remainingVolume;
    this.executedVolume = executedVolume;
    this.tradesCount = tradesCount;
  }
}
