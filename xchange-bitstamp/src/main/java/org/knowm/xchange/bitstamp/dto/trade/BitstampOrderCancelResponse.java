package org.knowm.xchange.bitstamp.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BitstampOrderCancelResponse {

  private long id;
  /** 0 - buy (bid); 1 - sell (ask) */
  private int type;

  private BigDecimal price;
  private BigDecimal amount;

  private final String error;

  /**
   * @param id Order id
   * @param amount Order amount
   * @param price Order price
   * @param type Order type
   * @param error Order error
   */
  public BitstampOrderCancelResponse(
      @JsonProperty("id") long id,
      @JsonProperty("type") int type,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("error") String error) {

    this.id = id;
    this.type = type;
    this.price = price;
    this.amount = amount;
    this.error = error;
  }

  public long getId() {
    return id;
  }

  public int getType() {
    return type;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getError() {
    return error;
  }
}
