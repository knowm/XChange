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
package com.xeiam.xchange.mtgox.v2.service.marketdata.streaming;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxTrade;

/**
 * Test MtGoxDepthStream JSON parsing
 */
public class TradeJSONTest {

  @Test
  public void testStreamingUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TradeJSONTest.class.getResourceAsStream("/v2/marketdata/streaming/example-trade-streaming-data.json");

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    Map<String, Object> userInMap = mapper.readValue(is, new TypeReference<Map<String, Object>>() {
    });

    MtGoxTrade mtGoxTrade = mapper.readValue(mapper.writeValueAsString(userInMap.get("trade")), MtGoxTrade.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(mtGoxTrade.getPriceInt(), equalTo(9079995L));
    assertThat(mtGoxTrade.getAmountInt(), equalTo(1851242L));
    assertThat(mtGoxTrade.getTid(), equalTo(1364652424875559L));

  }
}
