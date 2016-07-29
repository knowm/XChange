package org.knowm.xchange.coinsetter.dto.pricealert.request;

import java.math.BigDecimal;

/**
 * Request for adding a price alert.
 */
public class CoinsetterPriceAlertRequest {

  private final String type;
  private final String condition;
  private final BigDecimal price;
  private final String symbol;

  /**
   * @param type The way you wish to receive the alert ("EMAIL", "TEXT", "BOTH")
   * @param condition The condition to be met that will trigger the price alert (Currently only "CROSSES" is supported)
   * @param price The price that the condition will be based off of
   * @param symbol We Currently only support "BTCUSD"
   */
  public CoinsetterPriceAlertRequest(String type, String condition, BigDecimal price, String symbol) {

    this.type = type;
    this.condition = condition;
    this.price = price;
    this.symbol = symbol;
  }

  public String getType() {

    return type;
  }

  public String getCondition() {

    return condition;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public String getSymbol() {

    return symbol;
  }

}
