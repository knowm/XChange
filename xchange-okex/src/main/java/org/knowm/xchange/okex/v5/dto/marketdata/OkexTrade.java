package org.knowm.xchange.okex.v5.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkexTrade {

  private final String id;

  private final boolean liquidation;

  private final BigDecimal price;

  private final String side;

  private final BigDecimal size;

  private final Date time;

  public OkexTrade(
      @JsonProperty("id") String id,
      @JsonProperty("liquidation") boolean liquidation,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("side") String side,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("time") Date time) {
    this.id = id;
    this.liquidation = liquidation;
    this.price = price;
    this.side = side;
    this.size = size;
    this.time = time;
  }

  public String getId() {
    return id;
  }

  public boolean isLiquidation() {
    return liquidation;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public String getSide() {
    return side;
  }

  public BigDecimal getSize() {
    return size;
  }

  public Date getTime() {
    return time;
  }

  @Override
  public String toString() {
    return "FtxTradeDto{"
        + "id='"
        + id
        + '\''
        + ", liquidation="
        + liquidation
        + ", price="
        + price
        + ", side="
        + side
        + ", size="
        + size
        + ", time="
        + time
        + '}';
  }
}
