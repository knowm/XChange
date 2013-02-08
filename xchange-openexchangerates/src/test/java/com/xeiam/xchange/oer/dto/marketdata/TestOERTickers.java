/**
 * Copyright 2012 Xeiam LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xeiam.xchange.oer.dto.marketdata;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author timmolter
 */
public class TestOERTickers {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TestOERTickers.class.getResourceAsStream("/example-latest-rates.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    OERTickers oERTickers = mapper.readValue(is, OERTickers.class);

    // Verify that the example data was unmarshalled correctly
    System.out.println(oERTickers.getTimestamp().toString());
    assertThat("Unexpected Return Timestamp value", oERTickers.getTimestamp(), equalTo(1354687208L));

    System.out.println(oERTickers.getRates().getAED());
    assertThat("Unexpected Return AED value", oERTickers.getRates().getAED(), equalTo(3.672989));
  }
}
