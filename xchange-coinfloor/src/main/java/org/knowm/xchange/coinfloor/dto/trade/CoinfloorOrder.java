package org.knowm.xchange.coinfloor.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;

public class CoinfloorOrder {
  private final long id;
  private final String datetime;

  // 0 - buy, 1 - sell
  private final int type;

  private final BigDecimal price;

  private final BigDecimal amount;

  // this is used by the CoinfloorTradeService to temporarily store details of the currency pair
  private CurrencyPair pair = null;

  public CoinfloorOrder(
      @JsonProperty("status") String status,
      @JsonProperty("reason") Object reason,
      @JsonProperty("id") int id,
      @JsonProperty("datetime") String datetime,
      @JsonProperty("type") int type,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount) {

    this.id = id;
    this.datetime = datetime;
    this.type = type;
    this.price = price;
    this.amount = amount;
  }

  public String getDatetime() {
    return datetime;
  }

  public long getId() {
    return id;
  }

  public int getType() {
    return type;
  }

  public OrderType getSide() {
    switch (type) {
      case 0:
        return OrderType.BID;
      case 1:
        return OrderType.ASK;
      default:
        return null;
    }
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public CurrencyPair getCurrencyPair() {
    return pair;
  }

  public void setCurrencyPair(CurrencyPair value) {
    pair = value;
  }

  @Override
  public String toString() {
    return String.format(
        "Order{id=%s, datetime=%s, side=%s, price=%s, amount=%s}",
        id, datetime, getSide(), price, amount);
  }
}
