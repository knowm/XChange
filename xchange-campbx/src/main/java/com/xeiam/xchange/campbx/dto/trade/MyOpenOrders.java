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
package com.xeiam.xchange.campbx.dto.trade;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.campbx.dto.CampBXOrder;
import com.xeiam.xchange.campbx.dto.CampBXResponse;

/**
 * @author Matija Mazi
 */
public final class MyOpenOrders extends CampBXResponse {

  @JsonProperty("Buy")
  private List<CampBXOrder> buy = new ArrayList<CampBXOrder>();
  @JsonProperty("Sell")
  private List<CampBXOrder> sell = new ArrayList<CampBXOrder>();

  public List<CampBXOrder> getBuy() {

    return buy;
  }

  public void setBuy(List<CampBXOrder> Buy) {

    this.buy = Buy;
  }

  public List<CampBXOrder> getSell() {

    return sell;
  }

  public void setSell(List<CampBXOrder> Sell) {

    this.sell = Sell;
  }

  @Override
  public String toString() {

    return "MyOpenOrders [buy=" + buy + ", sell=" + sell + ", getSuccess()=" + getSuccess() + ", getInfo()=" + getInfo() + ", getError()=" + getError() + "]";
  }

}
