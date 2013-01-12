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
package com.xeiam.xchange.mtgox.v1.service.marketdata;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxTicker;

/**
 * Test MtGoxTicker JSON parsing
 */
public class TickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    MtGoxTicker mtGoxTicker = mapper.readValue(is, MtGoxTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat("Unexpected Return Buy value", mtGoxTicker.getBuy().getValue().doubleValue(), equalTo(4.89002));
    assertThat("Unexpected Return Last value", mtGoxTicker.getLast().getValue().doubleValue(), equalTo(4.89000));
    assertThat("Unexpected Return Bid value", mtGoxTicker.getBuy().getValue().doubleValue(), equalTo(4.89002));
    assertThat("Unexpected Return Ask value", mtGoxTicker.getSell().getValue().doubleValue(), equalTo(4.91227));
    assertThat("Unexpected Return High value", mtGoxTicker.getHigh().getValue().doubleValue(), equalTo(4.98000));
    assertThat("Unexpected Return Low value", mtGoxTicker.getLow().getValue().doubleValue(), equalTo(4.84272));
    assertThat("Unexpected Return Volume value", mtGoxTicker.getVol().getValue().doubleValue(), equalTo(57759.66891627));
  }

  @Test
  public void testStreamingUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-streaming-data.json");

    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> userInMap = mapper.readValue(is, new TypeReference<Map<String, Object>>() {
    });
    System.out.println(userInMap.get("ticker").toString());
    System.out.println(mapper.writeValueAsString(userInMap.get("ticker")));

    // Use Jackson to parse it
    mapper = new ObjectMapper();
    MtGoxTicker mtGoxTicker = mapper.readValue(mapper.writeValueAsString(userInMap.get("ticker")), MtGoxTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat("Unexpected Return Buy value", mtGoxTicker.getBuy().getValue().doubleValue(), equalTo(5.10991));
    assertThat("Unexpected Return Last value", mtGoxTicker.getLast().getValue().doubleValue(), equalTo(5.10991));
    assertThat("Unexpected Return Bid value", mtGoxTicker.getBuy().getValue().doubleValue(), equalTo(5.10991));
    assertThat("Unexpected Return Ask value", mtGoxTicker.getSell().getValue().doubleValue(), equalTo(5.11000));
    assertThat("Unexpected Return High value", mtGoxTicker.getHigh().getValue().doubleValue(), equalTo(5.12500));
    assertThat("Unexpected Return Low value", mtGoxTicker.getLow().getValue().doubleValue(), equalTo(5.07000));
    assertThat("Unexpected Return Volume value", mtGoxTicker.getVol().getValue().doubleValue(), equalTo(15475.00497509));

  }
}
