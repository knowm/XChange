package org.knowm.xchange.bitbay.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author Z. Dolezal */
public class BitbayOrder {

  private final long id;
  private final String currency;
  private final String date;
  private final String paymentCurrency;
  private final String type;
  private final String status;
  private final BigDecimal amount;
  private final BigDecimal startAmount;
  private final BigDecimal currentPrice;
  private final BigDecimal startPrice;

  /**
   * order_id : id of offer order_currency : main currency (e.g. “LTC”) order_date : time, when
   * offer was changed recently payment_currency : shortcut of currency used to pay for offer type :
   * bid/ask status : “active” if order is active, “inactive” if order is unactive units : current
   * amount of main currency in order start_units : amount of main currency when order was added
   * current_price : price for whole amount of main currency start_price : starting price for whole
   * amount when offer was added
   */
  public BitbayOrder(
      @JsonProperty("order_id") long id,
      @JsonProperty("order_currency") String currency,
      @JsonProperty("order_date") String date,
      @JsonProperty("payment_currency") String paymentCurrency,
      @JsonProperty("type") String type,
      @JsonProperty("status") String status,
      @JsonProperty("units") BigDecimal amount,
      @JsonProperty("start_units") BigDecimal startAmount,
      @JsonProperty("current_price") BigDecimal currentPrice,
      @JsonProperty("start_price") BigDecimal startPrice) {

    this.id = id;
    this.currency = currency;

    this.date = date;
    this.paymentCurrency = paymentCurrency;
    this.type = type;
    this.status = status;
    this.amount = amount;
    this.startAmount = startAmount;
    this.currentPrice = currentPrice;
    this.startPrice = startPrice;
  }

  public long getId() {
    return id;
  }

  public String getCurrency() {
    return currency;
  }

  public String getDate() {
    return date;
  }

  public String getPaymentCurrency() {
    return paymentCurrency;
  }

  public String getType() {
    return type;
  }

  public String getStatus() {
    return status;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getStartAmount() {
    return startAmount;
  }

  public BigDecimal getCurrentPrice() {
    return currentPrice;
  }

  public BigDecimal getStartPrice() {
    return startPrice;
  }
}
