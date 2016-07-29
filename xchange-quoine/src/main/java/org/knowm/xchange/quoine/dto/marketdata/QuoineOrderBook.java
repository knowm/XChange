package org.knowm.xchange.quoine.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author timmolter
 */
public final class QuoineOrderBook {

  private final List<List<BigDecimal>> buyPriceLevels;

  private final List<List<BigDecimal>> sellPriceLevels;

  /**
   * Constructor
   *
   * @param buyPriceLevels
   * @param sellPriceLevels
   */
  public QuoineOrderBook(@JsonProperty("buy_price_levels") List<List<BigDecimal>> buyPriceLevels,
      @JsonProperty("sell_price_levels") List<List<BigDecimal>> sellPriceLevels) {

    this.buyPriceLevels = buyPriceLevels;
    this.sellPriceLevels = sellPriceLevels;
  }

  public List<List<BigDecimal>> getBuyPriceLevels() {
    return buyPriceLevels;
  }

  public List<List<BigDecimal>> getSellPriceLevels() {
    return sellPriceLevels;
  }

  @Override
  public String toString() {
    return "QuoineOrderBook [buyPriceLevels=" + buyPriceLevels + ", sellPriceLevels=" + sellPriceLevels + "]";
  }

}
