package com.xeiam.xchange.virtex.dto.marketdata;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.money.BigMoney;

/**
 * <p>
 * Data object representing a Trade from Mt Gox
 * </p>
 * Auto-generated using the simplest types possible with conversion delegated to the adapter
 * 
 * @see <a href="http://jsongen.byingtondesign.com/">The JSONGen service</a>
 */
public class VirtExTrade {

  private float amount;
  private float date;
  private float price;
  private long tid;
  
  
  /**
   * Constructor
   * 
   * @param amount
   * @param amount_int
   * @param date
   * @param item
   * @param price
   * @param price_currency
   * @param price_int
   * @param primary
   * @param properties
   * @param tid
   * @param trade_type
   */
  public VirtExTrade(@JsonProperty("amount") float amount, @JsonProperty("date") float date, @JsonProperty("price") float price,
      @JsonProperty("tid") long tid) {

    this.amount = amount;
    this.date = date;
    this.price = price;
    this.tid = tid;
  }
  

  public float getAmount() {

    return amount;
  }

  public void setAmount(float amount) {

    this.amount = amount;
  }

  public void setDate(float date) {

    this.date = date;
  }
  
  public float getDate() {

	    return date;
	  }

  public void setPrice(float price) {

    this.price = price;
  }
  

  public float getPrice() {

    return price;
  }

  public long getTid() {

    return tid;
  }

  public void setTid(long tid) {

    this.tid = tid;
  }

  @Override
  public String toString() {

    return "VirtExTrades [amount=" + amount + ", date=" + date + ", price=" + price + ", tid=" + tid + "]";
  }

}
