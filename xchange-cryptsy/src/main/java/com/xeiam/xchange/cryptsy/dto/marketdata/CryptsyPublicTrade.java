package com.xeiam.xchange.cryptsy.dto.marketdata;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptsy.CryptsyUtils;

public class CryptsyPublicTrade {

  private final int tradeID;
  private final Date time;
  private final BigDecimal price;
  private final BigDecimal quantity;
  private final BigDecimal total;

  /**
   * Constructor
   * 
   * @throws ParseException
   */
  @JsonCreator
  public CryptsyPublicTrade(@JsonProperty("id") Integer tradeID, @JsonProperty("time") String time, @JsonProperty("price") BigDecimal price, @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("total") BigDecimal total) throws ParseException {

    this.tradeID = tradeID;
    this.time = time == null ? null : CryptsyUtils.convertDateTime(time);
    this.price = price;
    this.quantity = quantity;
    this.total = total;
  }

  public int getTradeID() {

    return tradeID;
  }

  public Date getTime() {

    return time;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getQuantity() {

    return quantity;
  }

  public BigDecimal getTotal() {

    return total;
  }

  @Override
  public String toString() {

    return "CryptsyPublicTrade [tradeID=" + tradeID + ", time=" + time + ", price=" + price + ", quantity=" + quantity + ", total=" + total + "]";
  }

}
