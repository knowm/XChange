package org.knowm.xchange.coinsph.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CoinsphUserTrade {

  private final String symbol;
  private final long id; // Trade ID
  private final long orderId;
  private final BigDecimal price;
  private final BigDecimal qty;
  private final BigDecimal quoteQty;
  private final BigDecimal commission;
  private final String commissionAsset;
  private final long time;
  private final boolean isBuyer;
  private final boolean isMaker;
  private final boolean isBestMatch;

  public CoinsphUserTrade(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("id") long id,
      @JsonProperty("orderId") long orderId,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("qty") BigDecimal qty,
      @JsonProperty("quoteQty") BigDecimal quoteQty,
      @JsonProperty("commission") BigDecimal commission,
      @JsonProperty("commissionAsset") String commissionAsset,
      @JsonProperty("time") long time,
      @JsonProperty("isBuyer") boolean isBuyer,
      @JsonProperty("isMaker") boolean isMaker,
      @JsonProperty("isBestMatch") boolean isBestMatch) {
    this.symbol = symbol;
    this.id = id;
    this.orderId = orderId;
    this.price = price;
    this.qty = qty;
    this.quoteQty = quoteQty;
    this.commission = commission;
    this.commissionAsset = commissionAsset;
    this.time = time;
    this.isBuyer = isBuyer;
    this.isMaker = isMaker;
    this.isBestMatch = isBestMatch;
  }
}
