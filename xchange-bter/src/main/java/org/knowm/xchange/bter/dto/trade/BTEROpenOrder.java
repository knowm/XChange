package org.knowm.xchange.bter.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by David Henry on 2/19/14.
 */
public class BTEROpenOrder {

  private final String id;
  private final String sellCurrency;
  private final String buyCurrency;
  private final BigDecimal sellAmount;
  private final BigDecimal buyAmount;

  /**
   * Constructor
   *
   * @param id orderId
   * @param sellAmount amount to sell
   * @param buyAmount amount to buy
   */
  private BTEROpenOrder(@JsonProperty("id") String id, @JsonProperty("sell_type") String sellCurrency, @JsonProperty("buy_type") String buyCurrency,
      @JsonProperty("sell_amount") BigDecimal sellAmount, @JsonProperty("buy_amount") BigDecimal buyAmount) {

    this.id = id;
    this.sellCurrency = sellCurrency;
    this.buyCurrency = buyCurrency;
    this.sellAmount = sellAmount;
    this.buyAmount = buyAmount;
  }

  public String getId() {

    return id;
  }

  public String getSellCurrency() {

    return sellCurrency;
  }

  public String getBuyCurrency() {

    return buyCurrency;
  }

  public BigDecimal getSellAmount() {

    return sellAmount;
  }

  public BigDecimal getBuyAmount() {

    return buyAmount;
  }

  @Override
  public String toString() {

    return "BTEROpenOrder [id=" + id + ", sellCurrency=" + sellCurrency + ", buyCurrency=" + buyCurrency + ", sellAmount=" + sellAmount
        + ", buyAmount=" + buyAmount + "]";
  }
}
