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

public class HitbtcExecutionReport {

  private final String orderId;
  private final String clientOrderId;
  private final String execReportType;
  private final String symbol;
  private final String side;
  private final long timestamp;
  private final BigDecimal price;
  private final BigDecimal quantity;
  private final String type;
  private final String timeInForce;
  private final BigDecimal lastQuantity;
  private final BigDecimal lastPrice;
  private final BigDecimal leavesQuantity;
  private final BigDecimal cumQuantity;
  private final BigDecimal averagePrice;

  public HitbtcExecutionReport(@JsonProperty("orderId") String orderId, @JsonProperty("clientOrderId") String clientOrderId, @JsonProperty("execReportType") String execReportType,
      @JsonProperty("symbol") String symbol, @JsonProperty("side") String side, @JsonProperty("timestamp") long timestamp, @JsonProperty("price") BigDecimal price,
      @JsonProperty("quantity") BigDecimal quantity, @JsonProperty("type") String type, @JsonProperty("timeInForce") String timeInForce, @JsonProperty("lastQuantity") BigDecimal lastQuantity,
      @JsonProperty("lastPrice") BigDecimal lastPrice, @JsonProperty("leavesQuantity") BigDecimal leavesQuantity, @JsonProperty("cumQuantity") BigDecimal cumQuantity,
      @JsonProperty("averagePrice") BigDecimal averagePrice) {

    super();
    this.orderId = orderId;
    this.clientOrderId = clientOrderId;
    this.execReportType = execReportType;
    this.symbol = symbol;
    this.side = side;
    this.timestamp = timestamp;
    this.price = price;
    this.quantity = quantity;
    this.type = type;
    this.timeInForce = timeInForce;
    this.lastQuantity = lastQuantity;
    this.lastPrice = lastPrice;
    this.leavesQuantity = leavesQuantity;
    this.cumQuantity = cumQuantity;
    this.averagePrice = averagePrice;
  }

  public String getOrderId() {

    return orderId;
  }

  public String getClientOrderId() {

    return clientOrderId;
  }

  public String getExecReportType() {

    return execReportType;
  }

  public String getSymbol() {

    return symbol;
  }

  public String getSide() {

    return side;
  }

  public long getTimestamp() {

    return timestamp;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getQuantity() {

    return quantity;
  }

  public String getType() {

    return type;
  }

  public String getTimeInForce() {

    return timeInForce;
  }

  public BigDecimal getLastQuantity() {

    return lastQuantity;
  }

  public BigDecimal getLastPrice() {

    return lastPrice;
  }

  public BigDecimal getLeavesQuantity() {

    return leavesQuantity;
  }

  public BigDecimal getCumQuantity() {

    return cumQuantity;
  }

  public BigDecimal getAveragePrice() {

    return averagePrice;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("HitbtcExecutionReport [orderId=");
    builder.append(orderId);
    builder.append(", clientOrderId=");
    builder.append(clientOrderId);
    builder.append(", execReportType=");
    builder.append(execReportType);
    builder.append(", symbol=");
    builder.append(symbol);
    builder.append(", side=");
    builder.append(side);
    builder.append(", timestamp=");
    builder.append(timestamp);
    builder.append(", price=");
    builder.append(price);
    builder.append(", quantity=");
    builder.append(quantity);
    builder.append(", type=");
    builder.append(type);
    builder.append(", timeInForce=");
    builder.append(timeInForce);
    builder.append(", lastQuantity=");
    builder.append(lastQuantity);
    builder.append(", lastPrice=");
    builder.append(lastPrice);
    builder.append(", leavesQuantity=");
    builder.append(leavesQuantity);
    builder.append(", cumQuantity=");
    builder.append(cumQuantity);
    builder.append(", averagePrice=");
    builder.append(averagePrice);
    builder.append("]");
    return builder.toString();
  }
}
