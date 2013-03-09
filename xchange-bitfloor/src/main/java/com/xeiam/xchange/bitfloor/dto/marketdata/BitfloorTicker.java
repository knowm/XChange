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

/**
 * @author Matija Mazi
 */
public final class BitfloorTicker {

  // {"size":"5.15358000","price":"27.40000000","timestamp":1361036134.183366,"seq":3061020}
  private final BigDecimal size;
  private final BigDecimal price;
  private final Long seq;
  private Date timestamp;

  public BitfloorTicker(@JsonProperty("size") BigDecimal size, @JsonProperty("price") BigDecimal price, @JsonProperty("timestamp") Double timestamp, @JsonProperty("seq") Long seq) {

    this.size = size;
    this.price = price;
    this.seq = seq;
    this.timestamp = new Date((long) (timestamp * 1000));
  }

  public Date getTimestamp() {

    return timestamp;
  }

  public BigDecimal getSize() {

    return size;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public Long getSeq() {

    return seq;
  }

  @Override
  public String toString() {

    return String.format("BitfloorTicker{size=%s, price=%s, timestamp=%s, seq=%d}", size, price, timestamp, seq);
  }
}
