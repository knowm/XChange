package com.xeiam.xchange.cryptsy.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ObsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptsySellOrder {

  private final BigDecimal sellPrice;
  private final BigDecimal quantity;
  private final BigDecimal total;

  @JsonCreator
  public CryptsySellOrder(@JsonProperty("sellprice") BigDecimal sellPrice, @JsonProperty("quantity") BigDecimal quantity, @JsonProperty("total") BigDecimal total) {

    this.sellPrice = sellPrice;
    this.quantity = quantity;
    this.total = total;
  }

  /**
   * @return the sellPrice
   */
  public BigDecimal getSellPrice() {

    return sellPrice;
  }

  /**
   * @return the quantity
   */
  public BigDecimal getQuantity() {

    return quantity;
  }

  /**
   * @return the total
   */
  public BigDecimal getTotal() {

    return total;
  }

  /**
   * @return a string representation of this object
   */
  @Override
  public String toString() {

    return "CryptsySellOrder [" + "'Sell Price=" + sellPrice + "'Quantity=" + quantity + "'Total=" + total + "']";
  }
}
