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
package com.xeiam.xchange.cryptotrade.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;


public class CryptoTradeTradeJsonTests {

  @Test
  public void testDeserializePlacedOrderData() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoTradeTradeJsonTests.class.getResourceAsStream("/trade/example-placed-order-return-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoTradePlaceOrderReturn placedOrder = mapper.readValue(is, CryptoTradePlaceOrderReturn.class);

    assertThat(placedOrder.getBought()).isEqualTo("0");
    assertThat(placedOrder.getRemaining()).isEqualTo("1");
    assertThat(placedOrder.getOrderId()).isEqualTo(13);
    
    Map<String, BigDecimal> funds = placedOrder.getFunds();
    assertThat(funds.size()).isEqualTo(12);
    assertThat(funds.get("usd")).isEqualTo("1535.78614365");
    
    assertThat(placedOrder.getStatus()).isEqualTo("success");
    assertThat(placedOrder.getError()).isNull();
  }
  
  @Test
  public void testDeserializeCancelledOrderData() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoTradeTradeJsonTests.class.getResourceAsStream("/trade/example-cancel-order-return-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoTradeCancelOrderReturn cancelledOrder = mapper.readValue(is, CryptoTradeCancelOrderReturn.class);

    assertThat(cancelledOrder.getOrderStatus()).isEqualTo("Cancelled");
    assertThat(cancelledOrder.getOrderId()).isEqualTo(10);
    
    Map<String, BigDecimal> funds = cancelledOrder.getFunds();
    assertThat(funds.size()).isEqualTo(11);
    assertThat(funds.get("ltc")).isEqualTo("1000");
    
    assertThat(cancelledOrder.getStatus()).isEqualTo("success");
    assertThat(cancelledOrder.getError()).isNull();
  }
}
