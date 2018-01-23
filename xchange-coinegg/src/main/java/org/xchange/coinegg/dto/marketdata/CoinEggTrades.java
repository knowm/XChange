package org.xchange.coinegg.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinEggTrades {
  
  @JsonFormat(shape = JsonFormat.Shape.ARRAY)
  public static class CoinEggTrade {
    
    @JsonProperty() private BigDecimal price;
    @JsonProperty() private BigDecimal quantity;
    
    public final BigDecimal getPrice() {
      return price;
    }

    public final BigDecimal getQuantity() {
      return quantity;
    }
  }
  
  private final CoinEggTrade[] asks;
  private final CoinEggTrade[] bids;
  
  public CoinEggTrades(@JsonProperty("asks") CoinEggTrade[] asks, @JsonProperty("bids") CoinEggTrade[] bids) {
    this.asks = asks;
    this.bids = bids;
  }

  public CoinEggTrade[] getAsks() {
    return asks;
  }
  
  public CoinEggTrade[] getBids() {
    return bids;
  }
}
