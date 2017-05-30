package org.knowm.xchange.dsx.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public final class DSXOrderbook {

  private final List<BigDecimal[]> asks;
  private final List<BigDecimal[]> bids;
  private final BigDecimal marketBuyPrice;
  private final BigDecimal marketSellPrice;

  public DSXOrderbook(@JsonProperty("asks") List<BigDecimal[]> asks, @JsonProperty("bids") List<BigDecimal[]> bids,
                      @JsonProperty("market_buy_price") BigDecimal marketBuyPrice,
                      @JsonProperty("market_sell_price") BigDecimal marketSellPrice) {

      this.asks = asks;
      this.bids = bids;
      this.marketBuyPrice = marketBuyPrice;
      this.marketSellPrice = marketSellPrice;
  }

  public List<BigDecimal[]> getAsks() {

      return asks;
  }

  public List<BigDecimal[]> getBids() {

      return bids;
  }

  public BigDecimal getMarketBuyPrice() {

      return marketBuyPrice;
  }

  public BigDecimal getMarketSellPrice() {

      return marketSellPrice;
  }

  @Override
  public String toString() {

      StringBuilder sb = new StringBuilder("DSXOrderbook [asks=");
      for (BigDecimal[] a : asks) {
          sb.append("[").append(a[0].toString()).append(",").append(a[1].toString()).append("],");
      }
      sb.append(" bids=");
      for (BigDecimal[] b : bids) {
          sb.append("[").append(b[0].toString()).append(",").append(b[1].toString()).append("],");
      }
      sb.append(", marketBuyPrice=");
      sb.append(marketBuyPrice);
      sb.append(", marketSellPrice=");
      sb.append(marketSellPrice);
      sb.append("]");
      return sb.toString();
  }

}
