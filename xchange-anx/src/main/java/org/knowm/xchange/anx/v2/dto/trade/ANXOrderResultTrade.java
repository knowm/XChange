package org.knowm.xchange.anx.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.anx.v2.dto.ANXValue;

/** Data object representing Open Orders from ANX */
public final class ANXOrderResultTrade {

  private final ANXValue amount;
  private final String currency;
  private final String date;
  private final String item;
  private final ANXValue price;
  private final String primary;
  private final String properties;
  private final String tradeId;
  private final String type;

  /**
   * Constructor
   *
   * @param amount
   * @param currency
   * @param date
   * @param item
   * @param price
   * @param primary
   * @param properties
   * @param tradeId
   * @param type
   */
  public ANXOrderResultTrade(
      @JsonProperty("amount") ANXValue amount,
      @JsonProperty("currency") String currency,
      @JsonProperty("date") String date,
      @JsonProperty("item") String item,
      @JsonProperty("price") ANXValue price,
      @JsonProperty("primary") String primary,
      @JsonProperty("properties") String properties,
      @JsonProperty("trade_id") String tradeId,
      @JsonProperty("type") String type) {

    this.amount = amount;
    this.currency = currency;
    this.date = date;
    this.item = item;
    this.price = price;
    this.primary = primary;
    this.properties = properties;
    this.tradeId = tradeId;
    this.type = type;
  }

  public ANXValue getAmount() {

    return amount;
  }

  public String getCurrency() {

    return currency;
  }

  public String getDate() {

    return date;
  }

  public String getItem() {

    return item;
  }

  public ANXValue getPrice() {

    return price;
  }

  public String getPrimary() {

    return primary;
  }

  public String getProperties() {

    return properties;
  }

  public String getTradeId() {

    return tradeId;
  }

  public String getType() {

    return type;
  }

  @Override
  public String toString() {

    return "ANXOrderResultTrade [amount="
        + amount
        + ", currency="
        + currency
        + ", date="
        + date
        + ", item="
        + item
        + ", price="
        + price
        + ", primary="
        + primary
        + ", price="
        + price
        + ", properties="
        + properties
        + ", tradeId="
        + tradeId
        + ", type="
        + type
        + "]";
  }
}
