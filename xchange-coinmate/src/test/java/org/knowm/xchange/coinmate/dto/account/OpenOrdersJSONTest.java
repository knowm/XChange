/*
 * The MIT License
 *
 * Copyright 2015 Coinmate.
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
package org.knowm.xchange.coinmate.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.coinmate.dto.marketdata.OrderBookJSONTest;
import org.knowm.xchange.coinmate.dto.trade.CoinmateOpenOrders;

/** @author Martin Stachon */
public class OpenOrdersJSONTest {

  @Test
  public void testUnmarshall() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        OrderBookJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinmate/dto/account/example-open-orders.json");

    ObjectMapper mapper = new ObjectMapper();
    CoinmateOpenOrders coinmateOrders = mapper.readValue(is, CoinmateOpenOrders.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(coinmateOrders.getData().get(0).getId()).isEqualTo(32780L);
    assertThat(coinmateOrders.getData().get(0).getTimestamp()).isEqualTo(1404383652640L);
    assertThat(coinmateOrders.getData().get(0).getType()).isEqualTo("BUY");
    assertThat(coinmateOrders.getData().get(0).getPrice()).isEqualTo(new BigDecimal("1"));
    assertThat(coinmateOrders.getData().get(0).getAmount()).isEqualTo(new BigDecimal("1"));
  }
}
