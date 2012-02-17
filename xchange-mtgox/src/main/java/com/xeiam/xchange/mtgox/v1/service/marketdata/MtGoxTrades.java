
package com.xeiam.xchange.mtgox.v1.service.marketdata;

/**
 * <p>Data object representing trades from Mt Gox</p>
 *
 * Auto-generated using the simplest types possible with conversion delegated to the adapter
 *
 * @see <a href="http://jsongen.byingtondesign.com/">The JSONGen service</a>
 *
 */
public class MtGoxTrades{
  private String amount;
  private String amount_int;
  private Number date;
  private String item;
  private String price;
  private String price_currency;
  private String price_int;
  private String primary;
  private String properties;
  private String tid;
  private String trade_type;

  public String getAmount(){
    return this.amount;
  }
  public void setAmount(String amount){
    this.amount = amount;
  }
  public String getAmount_int(){
    return this.amount_int;
  }
  public void setAmount_int(String amount_int){
    this.amount_int = amount_int;
  }
  public Number getDate(){
    return this.date;
  }
  public void setDate(Number date){
    this.date = date;
  }
  public String getItem(){
    return this.item;
  }
  public void setItem(String item){
    this.item = item;
  }
  public String getPrice(){
    return this.price;
  }
  public void setPrice(String price){
    this.price = price;
  }
  public String getPrice_currency(){
    return this.price_currency;
  }
  public void setPrice_currency(String price_currency){
    this.price_currency = price_currency;
  }
  public String getPrice_int(){
    return this.price_int;
  }
  public void setPrice_int(String price_int){
    this.price_int = price_int;
  }
  public String getPrimary(){
    return this.primary;
  }
  public void setPrimary(String primary){
    this.primary = primary;
  }
  public String getProperties(){
    return this.properties;
  }
  public void setProperties(String properties){
    this.properties = properties;
  }
  public String getTid(){
    return this.tid;
  }
  public void setTid(String tid){
    this.tid = tid;
  }
  public String getTrade_type(){
    return this.trade_type;
  }
  public void setTrade_type(String trade_type){
    this.trade_type = trade_type;
  }
}
