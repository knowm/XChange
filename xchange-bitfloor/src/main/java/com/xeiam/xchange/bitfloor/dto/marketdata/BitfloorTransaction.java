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
package com.xeiam.xchange.bitfloor.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.bitfloor.dto.Product;
import com.xeiam.xchange.utils.jackson.FloatingTimestampDeserializer;

/**
 * @author Matija Mazi
 */
public final class BitfloorTransaction {

  private final String id;
  private final Date timestamp;
  private final int providerSide;
  private final long seq;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final Product product;

  public BitfloorTransaction(@JsonProperty("id") String id, @JsonProperty("timestamp") @JsonDeserialize(using = FloatingTimestampDeserializer.class) Date timestamp,
      @JsonProperty("provider_side") int providerSide, @JsonProperty("seq") long seq, @JsonProperty("price") BigDecimal price, @JsonProperty("size") BigDecimal amount,
      @JsonProperty("product_id") Product product) {

    this.id = id;
    this.timestamp = timestamp;
    this.providerSide = providerSide;
    this.seq = seq;
    this.price = price;
    this.amount = amount;
    this.product = product;
  }

  public String getId() {

    return id;
  }

  public Date getTimestamp() {

    return timestamp;
  }

  public int getProviderSide() {

    return providerSide;
  }

  public long getSeq() {

    return seq;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public Product getProduct() {

    return product;
  }

  @Override
  public String toString() {

    return String.format("BitfloorTransaction{id='%s', timestamp=%s, providerSide=%d, seq=%d, price=%s, size=%s, productId=%s}", id, timestamp, providerSide, seq, price, amount, product);
  }
}
