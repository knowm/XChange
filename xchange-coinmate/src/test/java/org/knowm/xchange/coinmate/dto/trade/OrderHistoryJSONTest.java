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
package org.knowm.xchange.coinmate.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** @author Martin Stachon */
public class OrderHistoryJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        OrderHistoryJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinmate/dto/trade/example-order_history.json");

    ObjectMapper mapper = new ObjectMapper();
    CoinmateOrderHistory orderHistory = mapper.readValue(is, CoinmateOrderHistory.class);

    // Verify that the example data was unmarshalled correctly
    CoinmateOrderHistoryEntry entry1 = orderHistory.getData().get(0);

    assertThat(entry1.getId()).isEqualTo(12345678);
    assertThat(entry1.getTimestamp()).isEqualTo(1634890351090L);
    assertThat(entry1.getTrailingUpdatedTimestamp()).isEqualTo(null);
    assertThat(entry1.getType()).isEqualTo("BUY");
    assertThat(entry1.getPrice()).isEqualByComparingTo(new BigDecimal(1395123));
    assertThat(entry1.getRemainingAmount()).isEqualByComparingTo(new BigDecimal("0.001"));
    assertThat(entry1.getOriginalAmount()).isEqualByComparingTo(new BigDecimal("0.001"));
    assertThat(entry1.getStopPrice()).isNull();
    assertThat(entry1.getOriginalStopPrice()).isNull();
    assertThat(entry1.getMarketPriceAtLastUpdate()).isNull();
    assertThat(entry1.getMarketPriceAtOrderCreation()).isNull();
    assertThat(entry1.getStatus()).isEqualTo("CANCELLED");
    assertThat(entry1.getOrderTradeType()).isEqualTo("LIMIT");
    assertThat(entry1.isHidden()).isEqualTo(false);
    assertThat(entry1.getAvgPrice()).isNull();
    assertThat(entry1.isTrailing()).isEqualTo(false);
    assertThat(entry1.getStopLossOrderId()).isEqualTo("44444");
    assertThat(entry1.getOriginalOrderId()).isEqualTo("55555");
  }
}
