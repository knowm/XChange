/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.anx.v2.marketdata.polling;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXTrade;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXTradesWrapper;

public class TradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TickerJSONTest.class.getResourceAsStream("/v2/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    ANXTradesWrapper anxTradesWrapper = mapper.readValue(is, ANXTradesWrapper.class);

    List<ANXTrade> anxTrades = anxTradesWrapper.getANXTrades();

    assertThat(anxTrades).hasSize(2);

    ANXTrade anxTrade = anxTrades.get(0);
    assertThat(anxTrade.getAmount()).isEqualTo("0.25");
    assertThat(anxTrade.getAmountInt()).isEqualTo(25000000);
    assertThat(anxTrade.getItem()).isEqualTo("BTC");
    assertThat(anxTrade.getPrice()).isEqualTo("655");
    assertThat(anxTrade.getPriceInt()).isEqualTo(65500000);
    assertThat(anxTrade.getPriceCurrency()).isEqualTo("USD");
    assertThat(anxTrade.getPrimary()).isEqualTo("true");
    assertThat(anxTrade.getProperties()).isEqualTo("Not Supported");
    assertThat(anxTrade.getTid()).isEqualTo(1402189342525L);
    assertThat(anxTrade.getTradeType()).isEqualTo("bid");
  }
}
