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
package com.xeiam.xchange.bter.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bter.dto.BTEROrderType;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;

public class BTERTradeJsonTests {

  @Test
  public void testDeserializeOrderList() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTERTradeJsonTests.class.getResourceAsStream("/trade/example-order-list-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTEROpenOrders openOrders = mapper.readValue(is, BTEROpenOrders.class);

    assertThat(openOrders.isResult()).isTrue();
    assertThat(openOrders.getMessage()).isEqualTo("Success");

    List<BTEROpenOrder> openOrderList = openOrders.getOrders();
    assertThat(openOrderList).hasSize(1);

    BTEROpenOrder openOrder = openOrderList.get(0);
    assertThat(openOrder.getId()).isEqualTo("12941907");
    assertThat(openOrder.getSellCurrency()).isEqualTo(Currencies.LTC);
    assertThat(openOrder.getBuyCurrency()).isEqualTo(Currencies.BTC);
    assertThat(openOrder.getSellAmount()).isEqualTo("0.384");
    assertThat(openOrder.getBuyAmount()).isEqualTo("0.010176");
  }

  @Test
  public void testDeserializeOrderResult() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTERTradeJsonTests.class.getResourceAsStream("/trade/example-order-result-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTERPlaceOrderReturn orderReturn = mapper.readValue(is, BTERPlaceOrderReturn.class);

    assertThat(orderReturn.isResult()).isTrue();
    assertThat(orderReturn.getMessage()).isEqualTo("Success");
    assertThat(orderReturn.getOrderId()).isEqualTo("123456");
  }

  @Test
  public void testDeserializeOrderStatus() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTERTradeJsonTests.class.getResourceAsStream("/trade/example-order-status-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTEROrderStatus orderStatus = mapper.readValue(is, BTEROrderStatus.class);

    assertThat(orderStatus.isResult()).isTrue();
    assertThat(orderStatus.getMessage()).isEqualTo("Success");
    assertThat(orderStatus.getId()).isEqualTo("12942570");
    assertThat(orderStatus.getStatus()).isEqualTo("open");
    assertThat(orderStatus.getCurrencyPair()).isEqualTo(CurrencyPair.LTC_BTC);
    assertThat(orderStatus.getType()).isEqualTo(BTEROrderType.SELL);
    assertThat(orderStatus.getRate()).isEqualTo("0.0265");
    assertThat(orderStatus.getAmount()).isEqualTo("0.384");
    assertThat(orderStatus.getInitialRate()).isEqualTo("0.0265");
    assertThat(orderStatus.getInitialAmount()).isEqualTo("0.384");
  }
}
