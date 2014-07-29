package com.xeiam.xchange.mintpal.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.utils.DateUtils;

/**
 * @author jamespedwards42
 */
public class MintPalPublicTrade {

  private final String type;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final BigDecimal total;
  private final Date time;

  public MintPalPublicTrade(@JsonProperty("type") String type, @JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount, @JsonProperty("total") BigDecimal total,
      @JsonProperty("time") double time) {

    this.type = type;
    this.price = price;
    this.amount = amount;
    this.total = total;
    this.time = DateUtils.fromMillisUtc((long)time*1000);
    
  }

  public String getType() {
  
    return type;
  }

  
  public BigDecimal getPrice() {
  
    return price;
  }

  
  public BigDecimal getAmount() {
  
    return amount;
  }

  
  public BigDecimal getTotal() {
  
    return total;
  }

  
  public Date getTime() {
  
    return time;
  }

  @Override
  public String toString() {

    return "MintPalPublicTrade [type=" + type + ", price=" + price + ", amount=" + amount + ", total=" + total + ", time=" + time + "]";
  }
}
