/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.btccentral.dto.marketdata;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kpysniak
 */
public class BTCCentralTrade {

  private final UUID uuid;
  private final BigDecimal traded_btc;
  private final BigDecimal traded_currency;
  private final String created_at;
  private final String currency;
  private final BigDecimal price;
  private final long created_at_int;

  public BTCCentralTrade(@JsonProperty("uuid") UUID uuid, @JsonProperty("traded_btc") BigDecimal traded_btc, @JsonProperty("traded_currency") BigDecimal traded_currency,
      @JsonProperty("created_at") String created_at, @JsonProperty("currency") String currency, @JsonProperty("price") BigDecimal price, @JsonProperty("created_at_int") long created_at_int) {

    this.uuid = uuid;
    this.traded_btc = traded_btc;
    this.traded_currency = traded_currency;
    this.created_at = created_at;
    this.currency = currency;
    this.price = price;
    this.created_at_int = created_at_int;
  }

  public UUID getUuid() {

    return uuid;
  }

  public BigDecimal getTraded_btc() {

    return traded_btc;
  }

  public BigDecimal getTraded_currency() {

    return traded_currency;
  }

  public String getCreated_at() {

    return created_at;
  }

  public String getCurrency() {

    return currency;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public long getCreated_at_int() {

    return created_at_int;
  }

  @Override
  public String toString() {

    return "BTCCentralTrade{" + "uuid=" + uuid + ", traded_btc=" + traded_btc + ", traded_currency=" + traded_currency + ", created_at='" + created_at + '\'' + ", currency='" + currency + '\''
        + ", price=" + price + ", created_at_int=" + created_at_int + '}';
  }
}