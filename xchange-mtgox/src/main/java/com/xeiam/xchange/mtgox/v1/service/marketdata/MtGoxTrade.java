package com.xeiam.xchange.mtgox.v1.service.marketdata;

/**
 * <p>
 * Data object representing a Trade from Mt Gox
 * </p>
 * Auto-generated using the simplest types possible with conversion delegated to the adapter
 * 
 * @see <a href="http://jsongen.byingtondesign.com/">The JSONGen service</a>
 */
public class MtGoxTrade {

  private double amount;
  private long amount_int;
  private Long date;
  private String item;
  private double price;
  private String price_currency;
  private long price_int;
  private String primary;
  private String properties;
  private long tid;
  private String trade_type;

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public long getAmount_int() {
    return amount_int;
  }

  public void setAmount_int(long amount_int) {
    this.amount_int = amount_int;
  }

  public Long getDate() {
    return date;
  }

  public void setDate(Long date) {
    this.date = date;
  }

  public String getItem() {
    return item;
  }

  public void setItem(String item) {
    this.item = item;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getPrice_currency() {
    return price_currency;
  }

  public void setPrice_currency(String price_currency) {
    this.price_currency = price_currency;
  }

  public long getPrice_int() {
    return price_int;
  }

  public void setPrice_int(long price_int) {
    this.price_int = price_int;
  }

  public String getPrimary() {
    return primary;
  }

  public void setPrimary(String primary) {
    this.primary = primary;
  }

  public String getProperties() {
    return properties;
  }

  public void setProperties(String properties) {
    this.properties = properties;
  }

  public long getTid() {
    return tid;
  }

  public void setTid(long tid) {
    this.tid = tid;
  }

  public String getTrade_type() {
    return trade_type;
  }

  public void setTrade_type(String trade_type) {
    this.trade_type = trade_type;
  }

  @Override
  public String toString() {
    return "MtGoxTrades [amount=" + amount + ", amount_int=" + amount_int + ", date=" + date + ", item=" + item + ", price=" + price + ", price_currency=" + price_currency + ", price_int=" + price_int + ", primary="
        + primary + ", properties=" + properties + ", tid=" + tid + ", trade_type=" + trade_type + "]";
  }

}
