package org.knowm.xchange.abucoins.dto.marketdata;

import java.math.BigDecimal;

import javax.ws.rs.HeaderParam;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: bryant_harris
 */
public class AbucoinsTrade {

  String time;
  String tradeID;
  BigDecimal price;
  BigDecimal size;
  String side;

  public AbucoinsTrade(@JsonProperty("time") String time, @JsonProperty("trade_id") String tradeID, @JsonProperty("price") BigDecimal price,
                       @JsonProperty("size") BigDecimal size, @JsonProperty("side") String side) {

    this.time = time;
    this.tradeID = tradeID;
    this.price = price;
    this.size = size;
    this.side = side;
  }

  public String getTime() {
    return time;
  }

  public String getTradeID() {
    return tradeID;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getSize() {
    return size;
  }

  public String getSide() {
    return side;
  }

  @Override
  public String toString() {
    return "AbucoinsTrade [time=" + time + ", tradeID=" + tradeID + ", price=" + price + ", size=" + size + ", side="
        + side + "]";
  }
}
