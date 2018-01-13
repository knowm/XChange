package org.knowm.xchange.abucoins.dto.marketdata;

import java.math.BigDecimal;

import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>POJO representing the output JSON for the Abucoins
 * <code>GET /products/&lt;product-id&gt;/trades</code> endpoint.</p>
 *
 * Example:
 * <code><pre>
 * [
 *     {
 *         "time": "2017-09-21T12:33:03Z",
 *         "trade_id": "553794",
 *         "price": "14167.99328000",
 *         "size": "0.00035000",
 *         "side": "buy"
 *     },
 *     {
 *         "time": "2017-09-21T12:32:34Z",
 *         "trade_id": "553780",
 *         "price": "14163.96328000",
 *         "size": "0.00050000",
 *         "side": "buy"
 *     }
 * ]
 * </pre></code>
 * @author bryant_harris
 */
public class AbucoinsTrade {
  /** time in UTC */
  String time;
  
  /** identifier of the last trade */
  String tradeID;
  
  /** last price */
  BigDecimal price;

  /** size of the last trade */
  BigDecimal size;
  
  /** maker order side(sell or buy) */
  AbucoinsOrder.Side side;

  /**
   * 
   * @param time time in UTC
   * @param tradeID identifier of the last trade
   * @param price last price
   * @param size size of the last trade
   * @param side maker order side(sell or buy)
   */
  public AbucoinsTrade(@JsonProperty("time") String time, @JsonProperty("trade_id") String tradeID, @JsonProperty("price") BigDecimal price,
                       @JsonProperty("size") BigDecimal size, @JsonProperty("side") AbucoinsOrder.Side side) {

    this.time = time;
    this.tradeID = tradeID;
    this.price = price;
    this.size = size;
    this.side = side;
  }

  /** time in UTC */
  public String getTime() {
    return time;
  }

  /** identifier of the last trade */
  public String getTradeID() {
    return tradeID;
  }

  /** last price */
  public BigDecimal getPrice() {
    return price;
  }

  /** size of the last trade */
  public BigDecimal getSize() {
    return size;
  }

  /** maker order side(sell or buy) */
  public AbucoinsOrder.Side getSide() {
    return side;
  }

  @Override
  public String toString() {
    return "AbucoinsTrade [time=" + time + ", tradeID=" + tradeID + ", price=" + price + ", size=" + size + ", side="
        + side + "]";
  }
}
