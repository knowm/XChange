package org.knowm.xchange.acx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class AcxTrade {
  /** Unique ID */
  public final String id;
  /** trade price */
  public final BigDecimal price;
  /** trade volume */
  public final BigDecimal volume;

  public final BigDecimal funds;
  /** the market trade belongs to, like ‘btcaud’ */
  public final String market;
  /** trade time */
  public final Date createdAt;

  public final String trend;
  public final String side;
  public final String orderId;

  public AcxTrade(
      @JsonProperty("id") String id,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("funds") BigDecimal funds,
      @JsonProperty("market") String market,
      @JsonProperty("created_at") Date createdAt,
      @JsonProperty("trend") String trend,
      @JsonProperty("side") String side,
      @JsonProperty("order_id") String orderId) {
    this.id = id;
    this.price = price;
    this.volume = volume;
    this.funds = funds;
    this.market = market;
    this.createdAt = createdAt;
    this.trend = trend;
    this.side = side;
    this.orderId = orderId;
  }
}
