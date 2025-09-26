package org.knowm.xchange.coinsph.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CoinsphOrderBookEntry {
  private final BigDecimal price;
  private final BigDecimal quantity;

  /**
   * Constructor
   *
   * @param price
   * @param quantity
   */
  @JsonCreator
  public CoinsphOrderBookEntry(List<BigDecimal> entry) {
    if (entry == null || entry.size() != 2) {
      throw new IllegalArgumentException(
          "Order book entry must be a list of two BigDecimals (price, quantity)");
    }
    this.price = entry.get(0);
    this.quantity = entry.get(1);
  }

  // Optional: If API returns named fields instead of an array
  // public CoinsphOrderBookEntry(
  //     @JsonProperty("price") BigDecimal price,
  //     @JsonProperty("quantity") BigDecimal quantity) {
  //   this.price = price;
  //   this.quantity = quantity;
  // }
}
