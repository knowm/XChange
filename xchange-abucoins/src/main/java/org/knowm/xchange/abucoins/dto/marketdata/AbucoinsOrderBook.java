package org.knowm.xchange.abucoins.dto.marketdata;

import java.math.BigDecimal;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>POJO representing the output JSON for the Abucoins
 * <code>GET /products/&lt;product-id&gt;/book</code> endpoint.</p>
 *
 * Example:
 * <code><pre>
 * {
 *     "asks": [
 *       [ price, size, num-orders ],
 *       ["14160.13374000", "0.15994651", 1]
 *       ...
 *     ],
 *     "bids": [
 *        [ price, size, num-orders ],
 *        ["14140.00000000", "0.19970339", 1]
 *        ...
 *     ],
 *     "sequence": 1431
}
 * </pre></code>
 */
public class AbucoinsOrderBook {
  AbucoinsOrderBook.LimitOrder[] asks;
  AbucoinsOrderBook.LimitOrder[] bids;
  long sequence;


  public AbucoinsOrderBook(@JsonProperty("asks") AbucoinsOrderBook.LimitOrder[] asks,
			               @JsonProperty("bids") AbucoinsOrderBook.LimitOrder[] bids,
			               @JsonProperty("sequence") long sequence) {
    this.asks = asks;
    this.bids = bids;
    this.sequence = sequence;
  }

  public AbucoinsOrderBook.LimitOrder[] getAsks() {
    return asks;
  }

  public AbucoinsOrderBook.LimitOrder[] getBids() {
    return bids;
  }

  public long getSequence() {
    return sequence;
  }

  @Override
  public String toString() {
    return "AbucoinsOrderBook [asks=" + Arrays.toString(asks) + ", bids=" + Arrays.toString(bids) + ", sequence="
	+ sequence + "]";
  }


  @JsonFormat(shape=JsonFormat.Shape.ARRAY)
  public static class LimitOrder {
    BigDecimal price;
    BigDecimal size;
    long numOrders;
    public BigDecimal getPrice() {
      return price;
    }
    public BigDecimal getSize() {
      return size;
    }
    public long getNumOrders() {
      return numOrders;
    }
    @Override
    public String toString() {
      return "Book [price=" + price + ", size=" + size + ", numOrders=" + numOrders + "]";
    }
  }
}
