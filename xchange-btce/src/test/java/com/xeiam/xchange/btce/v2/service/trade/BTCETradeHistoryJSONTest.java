/**
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
package com.xeiam.xchange.btce.v2.service.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.btce.v2.dto.trade.BTCETradeHistoryResult;
import com.xeiam.xchange.btce.v2.dto.trade.BTCETradeHistoryReturn;

/**
 * @author Benedikt Bünz
 *         Test BTCETradeHistoryReturn JSON parsing
 */
@Deprecated
@Ignore
public class BTCETradeHistoryJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCETradeHistoryJSONTest.class.getResourceAsStream("/v2/trade/example-trade-history-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCETradeHistoryReturn transactions = mapper.readValue(is, BTCETradeHistoryReturn.class);
    Map<Long, BTCETradeHistoryResult> result = transactions.getReturnValue();
    assertThat(result.size()).isEqualTo(32);
    Entry<Long, BTCETradeHistoryResult> firstEntry = result.entrySet().iterator().next();
    // Verify that the example data was unmarshalled correctly
    assertThat(firstEntry.getKey()).isEqualTo(7258275L);
    assertThat(firstEntry.getValue().getAmount()).isEqualTo(new BigDecimal("0.1"));
    assertThat(firstEntry.getValue().getOrderId()).isEqualTo(34870919L);
    assertThat(firstEntry.getValue().getPair()).isEqualTo("btc_usd");
    assertThat(firstEntry.getValue().getRate()).isEqualTo(new BigDecimal("125.75"));
    assertThat(firstEntry.getValue().getTimestamp()).isEqualTo(1378194574L);
    assertThat(firstEntry.getValue().getType()).isEqualTo(BTCETradeHistoryResult.Type.sell);
    assertThat(firstEntry.getValue().isYourOrder()).isEqualTo(false);

  }
}
