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
package com.xeiam.xchange.itbit.v1.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItBitOrder {

  private final String id;
  private final String walletId;
  private final String side;
  private final String instrument;
  private final String currency;
  private final String type;
  private final String status;
  private final String createdTime;
  private final BigDecimal amountFilled;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final BigDecimal volumeWeightedAveragePrice;

  public ItBitOrder(@JsonProperty("id") String id, @JsonProperty("walletId") String walletId, @JsonProperty("side") String side, @JsonProperty("instrument") String instrument,
      @JsonProperty("currency") String currency, @JsonProperty("type") String type, @JsonProperty("amount") BigDecimal amount, @JsonProperty("price") BigDecimal price,
      @JsonProperty("amountFilled") BigDecimal amountFilled, @JsonProperty("volumeWeightedAveragePrice") BigDecimal volumeWeightedAveragePrice, @JsonProperty("createdTime") String createdTime, @JsonProperty("status") String status) {

	  
    this.id = id;
    this.walletId = walletId;
    this.side = side;
    this.instrument = instrument;
    this.currency = currency;
    this.type = type;
    this.amount = amount;
    this.price = price;
    this.amountFilled = amountFilled;
    this.createdTime = createdTime;
    this.status = status;
    this.volumeWeightedAveragePrice = volumeWeightedAveragePrice;
  }

  public String getId() {

    return id;
  }

  public String getWalletId() {

    return walletId;
  }

  public String getSide() {

    return side;
  }

  public String getInstrument() {

    return instrument;
  }

  public String getCurrency() {

    return currency;
  }

  public String getType() {

    return type;
  }

  public String getStatus() {

    return status;
  }

  public String getCreatedTime() {

    return createdTime;
  }

  public BigDecimal getAmountFilled() {

    return amountFilled;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getVolumeWeightedAveragePrice() {

    return volumeWeightedAveragePrice;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("ItBitAccountOrder [id=");
    builder.append(id);
    builder.append(", walletId=");
    builder.append(walletId);
    builder.append(", side=");
    builder.append(side);
    builder.append(", instrument=");
    builder.append(instrument);
    builder.append(", currency=");
    builder.append(currency);
    builder.append(", type=");
    builder.append(type);
    builder.append(", status=");
    builder.append(status);
    builder.append(", createdTime=");
    builder.append(createdTime);
    builder.append(", amountFilled=");
    builder.append(amountFilled);
    builder.append(", price=");
    builder.append(price);
    builder.append(", amount=");
    builder.append(amount);
    builder.append(", volumeWeightedAveragePrice=");
    builder.append(volumeWeightedAveragePrice);
    builder.append("]");
    return builder.toString();
  }

}
