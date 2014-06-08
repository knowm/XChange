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
package com.xeiam.xchange.cryptsy.dto.trade;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptsy.CryptsyUtils;
import com.xeiam.xchange.cryptsy.dto.CryptsyOrder.CryptsyOrderType;

/**
 * @author ObsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptsyTradeHistory {

  private final int marketId;
  private final int tradeId;
  private final CryptsyOrderType type;
  private final Date timeStamp;
  private final BigDecimal price;
  private final BigDecimal quantity;
  private final BigDecimal total;
  private final BigDecimal fee;
  private final CryptsyOrderType init_type;
  private final int orderId;

  /**
   * Constructor
   * 
   * @param timestamp
   * @param isYourOrder
   * @param orderId
   * @param rate
   * @param amount
   * @param type
   * @param pair
   * @throws ParseException
   */
  public CryptsyTradeHistory(@JsonProperty("marketid") int marketId, @JsonProperty("tradeid") int tradeId, @JsonProperty("tradetype") CryptsyOrderType type,
      @JsonProperty("datetime") String timeStamp, @JsonProperty("tradeprice") BigDecimal price, @JsonProperty("quantity") BigDecimal quantity, @JsonProperty("total") BigDecimal total,
      @JsonProperty("fee") BigDecimal fee, @JsonProperty("initiate_ordertype") CryptsyOrderType init_type, @JsonProperty("order_id") int orderId) throws ParseException {

    this.marketId = marketId;
    this.tradeId = tradeId;
    this.type = type;
    this.timeStamp = timeStamp == null ? null : CryptsyUtils.convertDateTime(timeStamp);
    this.price = price;
    this.quantity = quantity;
    this.total = total;
    this.fee = fee;
    this.init_type = init_type;
    this.orderId = orderId;
  }

  public int getMarketId() {

    return marketId;
  }

  public int getTradeId() {

    return tradeId;
  }

  public CryptsyOrderType getTradeType() {

    return type;
  }

  public Date getTimestamp() {

    return timeStamp;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getQuantity() {

    return quantity;
  }

  public BigDecimal getTotal() {

    return total;
  }

  public BigDecimal getFee() {

    return fee;
  }

  public CryptsyOrderType getInitiatingOrderType() {

    return init_type;
  }

  public int getOrderId() {

    return orderId;
  }

  @Override
  public String toString() {

    return "CryptsyTrade[" + "Market ID='" + marketId + "',Trade ID='" + tradeId + "',Type='" + type + "',Timestamp='" + timeStamp + "',Price='" + price + "',Quantity='" + quantity + "',Total='"
        + total + "',Fee='" + fee + "',InitiatingOrderType='" + init_type + "',Order ID='" + orderId + "']";
  }
}
