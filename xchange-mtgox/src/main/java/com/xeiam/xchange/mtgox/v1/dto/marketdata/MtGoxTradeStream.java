package com.xeiam.xchange.mtgox.v1.dto.marketdata;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * <p>
 * Data object representing a Trade from Mt Gox
 * </p>
 * Auto-generated using the simplest types possible with conversion delegated to the adapter
 */
public final class MtGoxTradeStream {

  private final double amount;
  private final long amountInt;
  private final long date;
  private final String item;
  private final double price;
  private final String priceCurrency;
  private final long priceInt;
  private final String primary;
  private final String properties;
  private final long tid;
  private final String tradeType;
  private final String type;


  /**
   * Constructor
   *
   * @param type
   * @param amount
   * @param amountInt
   * @param date
   * @param item
   * @param price
   * @param priceCurrency
   * @param priceInt
   * @param primary
   * @param properties
   * @param tid
   * @param tradeType
   */
  public MtGoxTradeStream(@JsonProperty("type") String type, @JsonProperty("date") long date, @JsonProperty("amount") double amount, @JsonProperty("price") double price, @JsonProperty("tid") long tid, @JsonProperty("amount_int") long amountInt, @JsonProperty("price_int") long priceInt, @JsonProperty("item") String item, @JsonProperty("price_currency") String priceCurrency,  @JsonProperty("trade_type") String tradeType, @JsonProperty("primary") String primary, @JsonProperty("properties") String properties) {

//SEVERE: Error unmarshalling from json: {"type":"trade","date":1359712127,"amount":5,"price":64.5,"tid":"1359712127456878","amount_int":"500000000","price_int":"6450000","item":"BTC","price_currency":"PLN","trade_type":"bid","primary":"Y","properties":"limit"}

      this.type = type;
    this.amount = amount;
    this.amountInt = amountInt;
    this.date = date;
    this.item = item;
    this.price = price;
    this.priceCurrency = priceCurrency;
    this.priceInt = priceInt;
    this.primary = "Y";
    this.properties = properties;
    this.tid = tid;
    this.tradeType = tradeType;
  }

  public double getAmount() {

    return amount;
  }

  public long getAmountInt() {

    return amountInt;
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

  public String getPriceCurrency() {

    return priceCurrency;
  }

  public long getPriceInt() {

    return priceInt;
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

  public String getTradeType() {

    return tradeType;
  }

  @Override
  public String toString() {

    return "MtGoxTrade [amount=" + amount + ", amountInt=" + amountInt + ", date=" + date + ", item=" + item + ", price=" + price + ", priceCurrency=" + priceCurrency + ", priceInt=" + priceInt
        + ", primary=" + primary + ", properties=" + properties + ", tid=" + tid + ", tradeType=" + tradeType + "]";
  }

}
