/*
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.bitfloor.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.bitfloor.dto.Product;
import com.xeiam.xchange.utils.jackson.FloatingTimestampDeserializer;

/**
 * @author Matija Mazi
 */
public final class BitfloorOrder {

  private final String id;
  private final Date timestamp;
  private final BigDecimal price;
  private final BigDecimal size;
  private final Product product;
  private final Side side;

  public BitfloorOrder(@JsonProperty("side") Side side, @JsonProperty("product_id") Product product, @JsonProperty("size") BigDecimal size, @JsonProperty("price") BigDecimal price,
      @JsonProperty("timestamp") @JsonDeserialize(using = FloatingTimestampDeserializer.class) Date timestamp, @JsonProperty("order_id") String id) {

    this.side = side;
    this.product = product;
    this.size = size;
    this.price = price;
    this.timestamp = timestamp;
    this.id = id;
  }

  public String getId() {

    return id;
  }

  public Date getTimestamp() {

    return timestamp;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getSize() {

    return size;
  }

  public Product getProduct() {

    return product;
  }

  public Side getSide() {

    return side;
  }

  @Override
  public String toString() {

    return String.format("BitfloorOrder{id='%s', timestamp=%s, price=%s, size=%s, product=%s, side=%s}", id, timestamp, price, size, product, side);
  }

  public static enum Side {
    buy(0), sell(1);

    private int id;

    Side(int id) {

      this.id = id;
    }

    @JsonCreator
    public static Side getInstance(int id) {

      for (Side side : values()) {
        if (side.id == id) {
          return side;
        }
      }
      throw new IllegalArgumentException("Illegal side id: " + id);
    }

    @Override
    public String toString() {

      return Integer.toString(id);
    }
  }
}
