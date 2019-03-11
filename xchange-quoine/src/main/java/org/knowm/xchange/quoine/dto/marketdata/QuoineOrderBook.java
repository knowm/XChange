package org.knowm.xchange.quoine.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

/** @author timmolter */
public final class QuoineOrderBook {

  private final List<BigDecimal[]> buyPriceLevels;

  private final List<BigDecimal[]> sellPriceLevels;

  /**
   * Constructor
   *
   * @param buyPriceLevels
   * @param sellPriceLevels
   */
  public QuoineOrderBook(
      @JsonProperty("buy_price_levels") List<BigDecimal[]> buyPriceLevels,
      @JsonProperty("sell_price_levels") List<BigDecimal[]> sellPriceLevels) {

    this.buyPriceLevels = buyPriceLevels;
    this.sellPriceLevels = sellPriceLevels;
  }

  public List<BigDecimal[]> getBuyPriceLevels() {
    return buyPriceLevels;
  }

  public List<BigDecimal[]> getSellPriceLevels() {
    return sellPriceLevels;
  }

  @Override
  public String toString() {
    return "QuoineOrderBook [buyPriceLevels="
        + buyPriceLevels
        + ", sellPriceLevels="
        + sellPriceLevels
        + "]";
  }
}
