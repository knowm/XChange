package org.knowm.xchange.cexio.dto.account;

import java.math.BigDecimal;
import java.text.MessageFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: brox Since: 2/7/14
 */
public class CexIOBalance {

  private final BigDecimal available;
  private final BigDecimal orders;
  private final BigDecimal bonus;

  /**
   * Constructor
   *
   * @param available Available balance
   * @param orders Balance in pending orders
   * @param bonus Referral program bonus
   */
  public CexIOBalance(@JsonProperty("available") BigDecimal available, @JsonProperty("orders") BigDecimal orders,
      @JsonProperty("bonus") BigDecimal bonus) {

    this.available = available;
    this.orders = orders;
    this.bonus = bonus;
  }

  public BigDecimal getAvailable() {

    return available;
  }

  public BigDecimal getOrders() {

    return orders;
  }

  public BigDecimal getBonus() {

    return bonus;
  }

  @Override
  public String toString() {

    return MessageFormat.format("CexIOBalance[available={0}, orders={1}, bonus={2}]", available, orders, bonus);
  }

}
