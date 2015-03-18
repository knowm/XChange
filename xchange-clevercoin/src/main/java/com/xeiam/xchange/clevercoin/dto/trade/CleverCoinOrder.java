package com.xeiam.xchange.clevercoin.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Karsten Nilsen
 */
public final class CleverCoinOrder {

  private final int id;
  /** 0 - buy (bid); 1 - sell (ask) */
  private final int type;
  private final double datetime;
  private final int isOpen;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final BigDecimal remainingAmount;
  private final String errorMessage;

  /**
   * Constructor
   * 
   * @param id
   * @param type
   * @param price
   * @param amount
   */
  public CleverCoinOrder(@JsonProperty("orderID") int id,  @JsonProperty("type") String type, @JsonProperty("isOpen") boolean isOpen, @JsonProperty("created") double datetime,
      @JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount, @JsonProperty("remainingAmount") BigDecimal remainingAmount, 
      @JsonProperty("error") @JsonDeserialize(using = CleverCoinErrorDeserializer.class) String errorMessage) {

    this.id = id;
    this.type = (type.equals("bid") ? 0 : 1);
    this.price = price;
    this.datetime = datetime;
    this.isOpen = isOpen ? 1 : 0;
    this.amount = amount;
    this.remainingAmount = remainingAmount;
    this.errorMessage = errorMessage;
  }

  public int getId() {

    return id;
  }

  public int getType() {

    return type;
  }

  public BigDecimal getPrice() {

    return price;
  }
  
  public double getDatetime() {

	    return datetime;
  }

  public BigDecimal getAmount() {

    return amount;
  }
  
  public BigDecimal getRemainingAmount() {

    return remainingAmount;
  }

  @JsonIgnore
  public Date getTime() {
	  
    return new Date((long) (this.getDatetime() * 1000L));
  }

  @JsonIgnore
  public String getErrorMessage() {

    return errorMessage;
  }

  @Override
  public String toString() {

    return errorMessage != null ? errorMessage : String.format("Order{id=%s, created=%s, type=%s, price=%s, amount=%s, remainingamount=%s, isopen=%s}", id, datetime, type, price, amount, remainingAmount, isOpen);
  }
}
