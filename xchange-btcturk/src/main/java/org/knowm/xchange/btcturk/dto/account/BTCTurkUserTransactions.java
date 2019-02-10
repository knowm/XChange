package org.knowm.xchange.btcturk.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.btcturk.dto.BTCTurkOperations;
import org.knowm.xchange.currency.Currency;

/** @author mertguner */
public class BTCTurkUserTransactions {

  private final String id;

  private final Date date;

  private final BTCTurkOperations operation;
  private final Currency currency;
  private final BigDecimal funds;
  private final BigDecimal amount;
  private final BigDecimal fee;
  private final BigDecimal tax;
  private final BigDecimal price;

  public BTCTurkUserTransactions(
      @JsonProperty("id") String id,
      @JsonProperty("date") Date date,
      @JsonProperty("operation") BTCTurkOperations operation,
      @JsonProperty("currency") Currency currency,
      @JsonProperty("funds") BigDecimal funds,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("tax") BigDecimal tax,
      @JsonProperty("price") BigDecimal price) {
    this.id = id;
    this.date = date;
    this.operation = operation;
    this.currency = currency;
    this.funds = funds;
    this.amount = amount;
    this.fee = fee;
    this.tax = tax;
    this.price = price;
  }

  public String getId() {
    return id;
  }

  public Date getDate() {
    return date;
  }

  public BTCTurkOperations getOperation() {
    return operation;
  }

  public Currency getCurrency() {
    return currency;
  }

  public BigDecimal getFunds() {
    return funds;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public BigDecimal getTax() {
    return tax;
  }

  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public String toString() {
    return "BTCTurkUserTransactions [id="
        + id
        + ", date="
        + date
        + ", operation="
        + operation
        + ", currency="
        + currency
        + ", funds="
        + funds
        + ", amount="
        + amount
        + ", fee="
        + fee
        + ", tax="
        + tax
        + ", price="
        + price
        + "]";
  }
}
