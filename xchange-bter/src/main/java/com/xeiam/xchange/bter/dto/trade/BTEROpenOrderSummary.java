package com.xeiam.xchange.bter.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by David Henry on 2/19/14.
 */
public class BTEROpenOrderSummary {
  private final String id;
  private final String sellCode;
  private final String buyCode;
  private final BigDecimal sellAmount;
  private final BigDecimal buyAmount;

  /**
   * Constructor
   *
   * @param id orderId
   * @param sellCode sell currency code String representation
   * @param buyCode buy currency code String representation
   * @param sellAmount amount to sell
   * @param buyAmount amount to buy
   */
  public BTEROpenOrderSummary(@JsonProperty("id") String id, @JsonProperty("sell_type") String sellCode, @JsonProperty("buy_type") String buyCode,
                              @JsonProperty("sell_amount") BigDecimal sellAmount, @JsonProperty("buy_amount") BigDecimal buyAmount) {
    this.id = id;
    this.sellCode = sellCode;
    this.buyCode = buyCode;
    this.sellAmount = sellAmount;
    this.buyAmount = buyAmount;
  }

  public String getId() {
    return id;
  }

  public String getSellCode() {
    return sellCode;
  }

  public String getBuyCode() {
    return buyCode;
  }

  public BigDecimal getSellAmount() {
    return sellAmount;
  }

  public BigDecimal getBuyAmount() {
    return buyAmount;
  }

  @Override
  public String toString() {
    return "BTEROpenOrderSummary{" +
            "id='" + id + '\'' +
            ", sellCode='" + sellCode + '\'' +
            ", buyCode='" + buyCode + '\'' +
            ", sellAmount=" + sellAmount +
            ", buyAmount=" + buyAmount +
            '}';
  }
}
