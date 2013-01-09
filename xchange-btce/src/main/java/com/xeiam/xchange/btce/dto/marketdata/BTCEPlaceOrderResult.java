/*
 * Copyright (C) 2012 - 2013 Matija Mazi
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.btce.dto.marketdata;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Matija Mazi <br/>
 *         {funds={usd=0, rur=0, eur=0, btc=0.1, ltc=0, nmc=0}, rights={info=1, trade=1, withdraw=1}, transaction_count=1, open_orders=0, server_time=1357678428}
 */
public class BTCEPlaceOrderResult {

  private final long orderId;
  private final BigDecimal received;
  private final BigDecimal remains;
  private final Map<String, BigDecimal> funds;

  public BTCEPlaceOrderResult(
      @JsonProperty("order_id") long orderId,
      @JsonProperty("received") BigDecimal received,
      @JsonProperty("remains") BigDecimal remains,
      @JsonProperty("funds") Map<String, BigDecimal> funds) {

    this.orderId = orderId;
    this.received = received;
    this.remains = remains;
    this.funds = funds;
  }

  public long getOrderId() {

    return orderId;
  }

  public Map<String, BigDecimal> getFunds() {

    return funds;
  }

  public BigDecimal getReceived() {

    return received;
  }

  public BigDecimal getRemains() {

    return remains;
  }

  @Override
  public String toString() {

    return MessageFormat.format("BTCEPlaceOrderResult[orderId={0}, received={1}, remains={2}, funds={3}]", orderId, received, remains, funds);
  }
}
