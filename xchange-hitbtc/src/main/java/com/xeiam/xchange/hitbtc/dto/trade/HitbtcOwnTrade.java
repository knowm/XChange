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
package com.xeiam.xchange.hitbtc.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcOwnTrade {

  private final long tradeId;
  private final BigDecimal execPrice;
  private final long timestamp;
  private final long originalOrderId;
  private final BigDecimal fee;
  private final String clientOrderId;
  private final String symbol;
  private final String side;
  private final BigDecimal execQuantity;

  public HitbtcOwnTrade(@JsonProperty("tradeId") long tradeId, @JsonProperty("execPrice") BigDecimal execPrice, @JsonProperty("timestamp") long timestamp,
      @JsonProperty("originalOrderId") long originalOrderId, @JsonProperty("fee") BigDecimal fee, @JsonProperty("clientOrderId") String clientOrderId, @JsonProperty("symbol") String symbol,
      @JsonProperty("side") String side, @JsonProperty("execQuantity") BigDecimal execQuantity) {

    super();
    this.tradeId = tradeId;
    this.execPrice = execPrice;
    this.timestamp = timestamp;
    this.originalOrderId = originalOrderId;
    this.fee = fee;
    this.clientOrderId = clientOrderId;
    this.symbol = symbol;
    this.side = side;
    this.execQuantity = execQuantity;
  }

  public long getTradeId() {

    return tradeId;
  }

  public BigDecimal getExecPrice() {

    return execPrice;
  }

  public long getTimestamp() {

    return timestamp;
  }

  public long getOriginalOrderId() {

    return originalOrderId;
  }

  public BigDecimal getFee() {

    return fee;
  }

  public String getClientOrderId() {

    return clientOrderId;
  }

  public String getSymbol() {

    return symbol;
  }

  public String getSide() {

    return side;
  }

  public BigDecimal getExecQuantity() {

    return execQuantity;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("HitbtcTrade [tradeId=");
    builder.append(tradeId);
    builder.append(", execPrice=");
    builder.append(execPrice);
    builder.append(", timestamp=");
    builder.append(timestamp);
    builder.append(", originalOrderId=");
    builder.append(originalOrderId);
    builder.append(", fee=");
    builder.append(fee);
    builder.append(", clientOrderId=");
    builder.append(clientOrderId);
    builder.append(", symbol=");
    builder.append(symbol);
    builder.append(", side=");
    builder.append(side);
    builder.append(", execQuantity=");
    builder.append(execQuantity);
    builder.append("]");
    return builder.toString();
  }
}
