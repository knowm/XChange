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
package com.xeiam.xchange.kraken.service.trading;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrder;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderDescription;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderStatus;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderType;
import com.xeiam.xchange.kraken.dto.trade.KrakenType;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenOpenOrdersResult;

public class KrakenOpenOrdersTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenOpenOrdersTest.class.getResourceAsStream("/trading/example-openorders-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenOpenOrdersResult krakenResult = mapper.readValue(is, KrakenOpenOrdersResult.class);
    Entry<String, KrakenOrder> openOrderEntry = krakenResult.getResult().getOrders().entrySet().iterator().next();
    KrakenOrder order = openOrderEntry.getValue();
    
    // Verify that the example data was unmarshalled correctly
    assertThat(openOrderEntry.getKey()).isEqualTo("OR6QMM-BCKM4-Q6YHIN");
    assertThat(order.getOpenTimestamp()).isEqualTo(1380586080.222);
    assertThat(order.getPrice()).isEqualTo("0.00000");
    assertThat(order.getVolume()).isEqualTo("0.01000000");
    assertThat(order.getVolumeExecuted()).isEqualTo("0.00000000");
    assertThat(order.getStatus()).isEqualTo(KrakenOrderStatus.OPEN);
    KrakenOrderDescription orderDescription = order.getOrderDescription();
    assertThat(orderDescription.getAssetPair()).isEqualTo("LTCEUR");
    assertThat(orderDescription.getLeverage()).isEqualTo("none");
    assertThat(orderDescription.getOrderDescription()).isEqualTo("buy 0.01000000 LTCEUR @ limit 13.00000");
    assertThat(orderDescription.getOrderType()).isEqualTo(KrakenOrderType.LIMIT);
    assertThat(orderDescription.getType()).isEqualTo(KrakenType.BUY);
    assertThat(orderDescription.getPrice()).isEqualTo("13.00000");
    assertThat(orderDescription.getSecondaryPrice()).isEqualTo("0");

  }
}
