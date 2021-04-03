package org.knowm.xchange.btcmarkets.dto.v3.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.utils.jackson.ISO8601DateDeserializer;

public class BTCMarketsTrade {

  /* Sample message:
  "id": "4107372347",
  "price": "0.265",
  "amount": "11.25",
  "timestamp": "2019-09-02T12:49:42.874000Z",
  "side": "Ask"
  */

  private final Long id;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final Date timestamp;
  private final String side;

  public BTCMarketsTrade(
      @JsonProperty("id") Long id,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("timestamp") @JsonDeserialize(using = ISO8601DateDeserializer.class)
          Date timestamp,
      @JsonProperty("side") String side) {
    super();
    this.id = id;
    this.price = price;
    this.amount = amount;
    this.timestamp = timestamp;
    this.side = side;
  }

  public Long getId() {
    return id;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getSide() {
    return side;
  }

  @Override
  public String toString() {
    return String.format(
        "BTCMarketsTrade{id=%s, price=%s, amount=%s, timestamp='%s', side='%s'} ",
        id, price, amount, timestamp, side);
  }
}
