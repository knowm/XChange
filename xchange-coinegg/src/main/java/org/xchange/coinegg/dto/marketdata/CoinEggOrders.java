package org.xchange.coinegg.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinEggOrders {
  
  @JsonFormat(shape = JsonFormat.Shape.ARRAY)
  public static class CoinEggOrder {
    
    @JsonProperty() private BigDecimal price;
    @JsonProperty() private BigDecimal quantity;
    
    public final BigDecimal getPrice() {
      return price;
    }

    public final BigDecimal getQuantity() {
      return quantity;
    }
  }
  
  private final CoinEggOrder[] asks;
  private final CoinEggOrder[] bids;
  
  public CoinEggOrders(@JsonProperty("asks") CoinEggOrder[] asks, @JsonProperty("bids") CoinEggOrder[] bids) {
    this.asks = asks;
    this.bids = bids;
  }

  public CoinEggOrder[] getAsks() {
    return asks;
  }
  
  public CoinEggOrder[] getBids() {
    return bids;
  }
}
