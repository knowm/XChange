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
package com.xeiam.xchange.campbx.dto;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.campbx.dto.trade.MyOpenOrders;

/**
 * Test BitStamp Full Depth JSON parsing
 */
public class MyOrdersJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    MyOpenOrders orderBook = new ObjectMapper().readValue(MyOrdersJSONTest.class.getResourceAsStream("/trade/open-orders.json"), MyOpenOrders.class);

    // Verify that the example data was unmarshalled correctly
    List<CampBXOrder> buy = orderBook.getBuy();
    assertThat(buy.size()).isEqualTo(1);
    assertThat(buy.get(0).getInfo()).isNotNull();
    assertThat(buy.get(0).getPrice()).isNull();

    List<CampBXOrder> sell = orderBook.getSell();
    CampBXOrder order = sell.get(0);
    assertThat(order.getPrice()).isEqualTo(new BigDecimal("110.00"));
    assertThat(order.getOrderID()).isEqualTo("599254");
    assertThat(order.getQuantity()).isEqualTo(new BigDecimal("0.10000000"));
    assertThat(order.getDarkPool()).isFalse();
    assertThat(order.getOrderType()).isEqualTo("Quick Sell");
    assertThat(order.getOrderExpiry().toString()).contains("2013");
    assertThat(order.getOrderExpiry().toString()).contains("29");
  }
}
