package org.knowm.xchange.bitso.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class BitsoTrade {

  private String book;
  private Date createdAt;
  private BigDecimal amount;
  private String makerSide;
  private BigDecimal price;
  private long tid;

  public BitsoTrade(
      @JsonProperty("book") String book,
      @JsonProperty("created_at") Date createdAt,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("maker_side") String makerSide,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("tid") long tid) {
    this.book = book;
    this.createdAt = createdAt;
    this.amount = amount;
    this.makerSide = makerSide;
    this.price = price;
    this.tid = tid;
  }

  public String getBook() {
    return book;
  }

  public void setBook(String book) {
    this.book = book;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getMakerSide() {
    return makerSide;
  }

  public void setMakerSide(String makerSide) {
    this.makerSide = makerSide;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public long getTid() {
    return tid;
  }

  public void setTid(long tid) {
    this.tid = tid;
  }

  @Override
  public String toString() {
    return "BitsoTrade [book="
        + book
        + ", createdAt="
        + createdAt
        + ", amount="
        + amount
        + ", makerSide="
        + makerSide
        + ", price="
        + price
        + ", tid="
        + tid
        + "]";
  }
}
