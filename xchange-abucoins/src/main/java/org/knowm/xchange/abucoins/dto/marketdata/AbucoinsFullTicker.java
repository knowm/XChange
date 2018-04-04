package org.knowm.xchange.abucoins.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * POJO representing the output JSON for the Abucoins <code>GET /products/ticker</code> endpoint.
 *
 * <p>This is essentially the {@link AbucoinsTicker} object plus a product ID. Example: <code><pre>
 * {
 *     "product_id":"ETH-BTC",
 *     "trade_id": "553612",
 *     "price": "14160.85",
 *     "size": "0.00053",
 *     "bid": "14140.00000000",
 *     "ask": "14181.70000000",
 *     "volume": "1.09596639",
 *     "time": "2017-09-21T10:26:58Z"
 * }
 * </pre></code>
 *
 * @author bryant_harris
 */
public class AbucoinsFullTicker extends AbucoinsTicker {
  /** identifier of the market */
  String productID;

  /**
   * Constructor
   *
   * @param productID identifier of the market
   * @param tradeID identifier of the last trade
   * @param price last price
   * @param size size of the last trade
   * @param bid the best bid
   * @param ask the best ask
   * @param volume 24 hour volume
   * @param time time in UTC
   */
  public AbucoinsFullTicker(
      @JsonProperty("product_id") String productID,
      @JsonProperty("trade_id") String tradeID,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("time") String time) {
    super(tradeID, price, size, bid, ask, volume, time);
    this.productID = productID;
  }

  /** identifier of the market */
  public String getProductID() {
    return productID;
  }

  @Override
  public String toString() {
    return "AbucoinsFullTicker [productID="
        + productID
        + ", tradeID="
        + tradeID
        + ", price="
        + price
        + ", size="
        + size
        + ", bid="
        + bid
        + ", ask="
        + ask
        + ", volume="
        + volume
        + ", time="
        + time
        + "]";
  }
}
