package org.knowm.xchange.btcchina.dto.trade;

import java.math.BigDecimal;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author David Yam
 */
public class BTCChinaOrder {

  private final int id;
  private final String type;
  private final BigDecimal price;
  private final String currency;
  private final BigDecimal amount;
  private final BigDecimal amountOriginal;
  private final long date;
  private final String status;
  private final BTCChinaOrderDetail[] details;

  /**
   * Constructor
   *
   * @param id
   * @param type
   * @param price
   * @param currency
   * @param amount
   * @param amountOriginal
   * @param date
   * @param status
   */
  public BTCChinaOrder(@JsonProperty("id") int id, @JsonProperty("type") String type, @JsonProperty("price") BigDecimal price,
      @JsonProperty("currency") String currency, @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("amount_original") BigDecimal amountOriginal, @JsonProperty("date") long date, @JsonProperty("status") String status,
      @JsonProperty("details") BTCChinaOrderDetail[] details) {

    this.id = id;
    this.type = type;
    this.price = price;
    this.currency = currency;
    this.amount = amount;
    this.amountOriginal = amountOriginal;
    this.date = date;
    this.status = status;
    this.details = details;
  }

  public int getId() {

    return id;
  }

  public String getType() {

    return type;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public String getCurrency() {

    return currency;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getAmountOriginal() {

    return amountOriginal;
  }

  public long getDate() {

    return date;
  }

  public String getStatus() {

    return status;
  }

  public BTCChinaOrderDetail[] getDetails() {

    return details;
  }

  @Override
  public String toString() {

    return String.format("BTCChinaOrder{id=%d, type=%s, price=%s, currency=%s, amount=%s, amountOriginal=%s, date=%d, status=%s, details=%s}", id,
        type, price, currency, amount, amountOriginal, date, status, Arrays.toString(details));
  }

}
