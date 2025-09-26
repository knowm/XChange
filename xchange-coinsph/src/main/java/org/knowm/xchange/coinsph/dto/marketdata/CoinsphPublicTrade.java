package org.knowm.xchange.coinsph.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CoinsphPublicTrade {

  private final long id; // Trade ID
  private final BigDecimal price;
  private final BigDecimal qty; // Base asset quantity
  private final BigDecimal quoteQty; // Quote asset quantity (price * qty)
  private final long time;
  private final boolean isBuyerMaker;

  // private final boolean isBestMatch; // Not in Coins.ph docs for public trades

  public CoinsphPublicTrade(
      @JsonProperty("id") long id,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("qty") BigDecimal qty,
      @JsonProperty("quoteQty") BigDecimal quoteQty,
      @JsonProperty("time") long time,
      @JsonProperty("isBuyerMaker") boolean isBuyerMaker
      // @JsonProperty("isBestMatch") boolean isBestMatch
      ) {
    this.id = id;
    this.price = price;
    this.qty = qty;
    this.quoteQty = quoteQty;
    this.time = time;
    this.isBuyerMaker = isBuyerMaker;
    // this.isBestMatch = isBestMatch;
  }
}
