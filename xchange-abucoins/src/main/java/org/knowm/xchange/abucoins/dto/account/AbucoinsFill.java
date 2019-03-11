package org.knowm.xchange.abucoins.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder.Side;

/**
 * POJO representing the output JSON for the Abucoins <code>GET /fills</code> endpoint. Example:
 * <code><pre>
 * [
 *   {
 *     "trade_id":"785705",
 *     "product_id":"BTC-PLN",
 *     "price":"14734.55000000",
 *     "size":"100.00000000",
 *     "order_id":"4196245",
 *     "created_at":"2017-09-28T13:08:43Z",
 *     "liquidity":"T",
 *     "side":"sell"
 *   },
 *   {
 *     "trade_id":"785704",
 *     "product_id":"BTC-PLN",
 *     "price":"14734.55000000",
 *     "size":"0.01000000",
 *     "order_id":"4196245",
 *     "created_at":"2017-09-28T13:08:43Z",
 *     "liquidity":"T",
 *     "side":"sell"
 *   }
 * ]
 * </pre></code>
 *
 * @author bryant_harris
 */
public class AbucoinsFill {
  /** identifier of the last trade */
  String tradeID;

  /** product identifier */
  String productID;

  /** trade price */
  BigDecimal price;

  /** trade size */
  BigDecimal size;

  /** Identifier of order */
  String orderID;

  /** time in UTC */
  String createdAt;

  /**
   * indicates if the fill was the result of a liquidity provider or liquidity taker. M indicates
   * Maker and T indicates Taker
   */
  Liquidity liquidity;

  /** user side(buy or sell) */
  AbucoinsOrder.Side side;

  public AbucoinsFill(
      @JsonProperty("trade_id") String tradeID,
      @JsonProperty("product_id") String productID,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("order_id") String orderID,
      @JsonProperty("created_at") String createdAt,
      @JsonProperty("liquidity") Liquidity liquidity,
      @JsonProperty("side") Side side) {
    this.tradeID = tradeID;
    this.productID = productID;
    this.price = price;
    this.size = size;
    this.orderID = orderID;
    this.createdAt = createdAt;
    this.liquidity = liquidity;
    this.side = side;
  }

  public String getTradeID() {
    return tradeID;
  }

  public String getProductID() {
    return productID;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getSize() {
    return size;
  }

  public String getOrderID() {
    return orderID;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public Liquidity getLiquidity() {
    return liquidity;
  }

  public AbucoinsOrder.Side getSide() {
    return side;
  }

  @Override
  public String toString() {
    return "AbucoinsFill [tradeID="
        + tradeID
        + ", productID="
        + productID
        + ", price="
        + price
        + ", size="
        + size
        + ", orderID="
        + orderID
        + ", createdAt="
        + createdAt
        + ", liquidity="
        + liquidity
        + ", side="
        + side
        + "]";
  }

  public enum Liquidity {
    T,
    M;

    public String toDescription() {
      switch (this) {
        case T:
          return "indicates that this fill was the result of a liquidity taker (Taker).";
        case M:
          return "indicates that this fill was the result of a liquidity provider (Maker).";

        default:
          return "";
      }
    }
  }
}
