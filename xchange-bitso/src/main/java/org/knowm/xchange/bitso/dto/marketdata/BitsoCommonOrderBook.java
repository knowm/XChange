package org.knowm.xchange.bitso.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitsoCommonOrderBook {

  private String book;
  private String price;
  private String amount;

  public BitsoCommonOrderBook(
      @JsonProperty("book") String book,
      @JsonProperty("price") String price,
      @JsonProperty("amount") String amount) {
    this.book = book;
    this.price = price;
    this.amount = amount;
  }

  public String getBook() {
    return book;
  }

  public void setBook(String book) {
    this.book = book;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  @Override
  public String toString() {
    return "BitsoCommonOrderBook [book=" + book + ", price=" + price + ", amount=" + amount + "]";
  }
}
