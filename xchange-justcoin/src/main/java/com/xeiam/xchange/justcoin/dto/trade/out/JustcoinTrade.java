/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.justcoin.dto.trade.out;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.utils.jackson.ISODateDeserializer;

/**
 * @author jamespedwards42
 */
public class JustcoinTrade {

  private final String id;
  private final String market;
  private final String type;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final BigDecimal remaining;
  private final BigDecimal matched;
  private final BigDecimal cancelled;
  private final Date createdAt;
  private final BigDecimal averagePrice;

  /**
   * Constructor
   * 
   * @param id
   * @param market
   * @param type
   * @param price
   * @param amount
   * @param remaining
   * @param matched
   * @param cancelled
   * @param createdAt
   * @param averagePrice
   */
  public JustcoinTrade(final @JsonProperty("id") String id, final @JsonProperty("market") String market, final @JsonProperty("type") String type, final @JsonProperty("price") BigDecimal price,
      final @JsonProperty("amount") BigDecimal amount, final @JsonProperty("remaining") BigDecimal remaining, final @JsonProperty("matched") BigDecimal matched,
      final @JsonProperty("cancelled") BigDecimal cancelled, final @JsonProperty("createdAt") @JsonDeserialize(using = ISODateDeserializer.class) Date createdAt,
      final @JsonProperty("averagePrice") BigDecimal averagePrice) {

    this.id = id;
    this.market = market;
    this.type = type;
    this.price = price;
    this.amount = amount;
    this.remaining = remaining;
    this.matched = matched;
    this.cancelled = cancelled;
    this.createdAt = createdAt;
    this.averagePrice = averagePrice;
  }

  public String getId() {

    return id;
  }

  public String getMarket() {

    return market;
  }

  public String getType() {

    return type;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getRemaining() {

    return remaining;
  }

  public BigDecimal getMatched() {

    return matched;
  }

  public BigDecimal getCancelled() {

    return cancelled;
  }

  public Date getCreatedAt() {

    return createdAt;
  }

  public BigDecimal getAveragePrice() {

    return averagePrice;
  }

  @Override
  public String toString() {

    return "JustcoinTrade [id=" + id + ", market=" + market + ", type=" + type + ", price=" + price + ", amount=" + amount + ", remaining=" + remaining + ", matched=" + matched + ", cancelled="
        + cancelled + ", createdAt=" + createdAt + ", averagePrice=" + averagePrice + "]";
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    JustcoinTrade other = (JustcoinTrade) obj;
    if (amount == null) {
      if (other.amount != null) {
        return false;
      }
    }
    else if (amount.compareTo(other.amount) != 0) {
      return false;
    }
    if (cancelled == null) {
      if (other.cancelled != null) {
        return false;
      }
    }
    else if (cancelled.compareTo(other.cancelled) != 0) {
      return false;
    }
    if (createdAt == null) {
      if (other.createdAt != null) {
        return false;
      }
    }
    else if (!createdAt.equals(other.createdAt)) {
      return false;
    }
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    }
    else if (!id.equals(other.id)) {
      return false;
    }
    if (market == null) {
      if (other.market != null) {
        return false;
      }
    }
    else if (!market.equals(other.market)) {
      return false;
    }
    if (matched == null) {
      if (other.matched != null) {
        return false;
      }
    }
    else if (matched.compareTo(other.matched) != 0) {
      return false;
    }
    if (price == null) {
      if (other.price != null) {
        return false;
      }
    }
    else if (price.compareTo(other.price) != 0) {
      return false;
    }
    if (remaining == null) {
      if (other.remaining != null) {
        return false;
      }
    }
    else if (remaining.compareTo(other.remaining) != 0) {
      return false;
    }
    if (type == null) {
      if (other.type != null) {
        return false;
      }
    }
    else if (!type.equals(other.type)) {
      return false;
    }
    if (averagePrice == null) {
      if (other.averagePrice != null) {
        return false;
      }
    }
    else if (averagePrice.compareTo(other.averagePrice) != 0) {
      return false;
    }
    return true;
  }

}
