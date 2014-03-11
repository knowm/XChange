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
package com.xeiam.xchange.vircurex.dto.trade;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by David Henry on 2/20/14.
 */
@JsonDeserialize(using = VircurexOpenOrdersDeserializer.class)
public class VircurexOpenOrdersReturn {

  private final int orderCount;
  private final String userName;
  private final String timeStamp;
  private final String token;
  private final String function;
  private final int status;
  private List<VircurexOpenOrder> openOrders;

  public VircurexOpenOrdersReturn(@JsonProperty("numberorders") int orderCount, @JsonProperty("account") String userName, @JsonProperty("timestamp") String timestamp,
      @JsonProperty("token") String token, @JsonProperty("status") int status, @JsonProperty("function") String function) {

    this.orderCount = orderCount;
    this.userName = userName;
    this.timeStamp = timestamp;
    this.token = token;
    this.function = function;
    this.status = status;
  }

  public int getOrderCount() {

    return orderCount;
  }

  public int getStatus() {

    return status;
  }

  public String getUserName() {

    return userName;
  }

  public String getTimeStamp() {

    return timeStamp;
  }

  public String getToken() {

    return token;
  }

  public String getFunction() {

    return function;
  }

  public List<VircurexOpenOrder> getOpenOrders() {

    return openOrders;
  }

  public void setOpenOrders(List<VircurexOpenOrder> openOrders) {

    this.openOrders = openOrders;
  }
}
