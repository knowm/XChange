package com.xeiam.xchange.mtgox.v1.dto.marketdata;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * <p>
 * Data object representing a Trade from Mt Gox
 * </p>
 * Auto-generated using the simplest types possible with conversion delegated to the adapter
 * 
 * @immutable
 */
public final class MtGoxTrade {

  private final double amount;
  private final long amount_int;
  private final Long date;
  private final String item;
  private final double price;
  private final String price_currency;
  private final long price_int;
  private final String primary;
  private final String properties;
  private final long tid;
  private final String trade_type;

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
  public MtGoxTrade(@JsonProperty("amount") double amount, @JsonProperty("amount_int") long amount_int, @JsonProperty("date") Long date, @JsonProperty("item") String item, @JsonProperty("price") double price,
      @JsonProperty("price_currency") String price_currency, @JsonProperty("price_int") long price_int, @JsonProperty("primary") String primary, @JsonProperty("properties") String properties,
      @JsonProperty("tid") long tid, @JsonProperty("trade_type") String trade_type) {

    this.amount = amount;
    this.amount_int = amount_int;
    this.date = date;
    this.item = item;
    this.price = price;
    this.price_currency = price_currency;
    this.price_int = price_int;
    this.primary = primary;
    this.properties = properties;
    this.tid = tid;
    this.trade_type = trade_type;
  }

  public double getAmount() {

    return amount;
  }

  public long getAmount_int() {

    return amount_int;
  }

  public Long getDate() {

    return date;
  }

  public String getItem() {

    return item;
  }

  public double getPrice() {

    return price;
  }

  public String getPrice_currency() {

    return price_currency;
  }

  public long getPrice_int() {

    return price_int;
  }

  public String getPrimary() {

    return primary;
  }

  public String getProperties() {

    return properties;
  }

  public long getTid() {

    return tid;
  }

  public String getTrade_type() {

    return trade_type;
  }

  @Override
  public String toString() {

    return "MtGoxTrades [amount=" + amount + ", amount_int=" + amount_int + ", date=" + date + ", item=" + item + ", price=" + price + ", price_currency=" + price_currency + ", price_int=" + price_int + ", primary="
        + primary + ", properties=" + properties + ", tid=" + tid + ", trade_type=" + trade_type + "]";
  }

}
