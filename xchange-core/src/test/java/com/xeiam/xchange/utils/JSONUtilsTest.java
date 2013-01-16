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
package com.xeiam.xchange.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.xeiam.xchange.rest.JSONUtils;

/**
 * Test class for testing JSONUtils methods
 */
public class JSONUtilsTest {

  /**
   * Tests the getJsonGenericMap() and getJSONString()
   * 
   * @throws IOException
   */
  @Test
  public void testJsonObject() throws IOException {

    ObjectMapper tickerObjectMapper = new ObjectMapper();

    // Read in the JSON from the example resources
    InputStream is = JSONUtilsTest.class.getResourceAsStream("/utils/example-ticker-streaming-data.json");

    // get raw JSON
    Map<String, Object> rawJSON = JSONUtils.getJsonGenericMap(getStringFromInputStream(is), tickerObjectMapper);

    assertFalse(rawJSON.get("ticker") == null);

    // Get MtGoxTicker from JSON String
    String jsonString = JSONUtils.getJSONString(rawJSON.get("ticker"), tickerObjectMapper);
    // System.out.println(jsonString);

    assertTrue(jsonString.contains("{\"vol\":{\"value_int\":\"1547500497509\""));

  }

  @Test
  public void testGetJsonObject() throws IOException {

    ObjectMapper objectMapper = new ObjectMapper();

    // Read in the JSON from the example resources
    InputStream is = JSONUtilsTest.class.getResourceAsStream("/marketdata/example-ticker.json");

    // Perform the test
    DummyTicker ticker = JSONUtils.getJsonObject(getStringFromInputStream(is), DummyTicker.class, objectMapper);

    // Verify the results
    assertEquals(34567L, ticker.getVolume());

  }

  private String getStringFromInputStream(InputStream is) throws IOException {

    // get String from InputStream
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = br.readLine()) != null) {
      sb.append(line.trim());
    }
    // System.out.println(sb.toString());
    br.close();
    return sb.toString();

  }

  // @Test
  public void testSerialization1() {

    ObjectMapper objectMapper = new ObjectMapper();

    String event = "blah";
    Object[] args = new Object[] { "Bar", "Foo" };
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("name", event);
    map.put("args", args);
    String jsonString = JSONUtils.getJSONString(map, objectMapper);
    // System.out.println(jsonString);

  }

  @Test
  public void testSerialization2() {

    ObjectMapper objectMapper = new ObjectMapper();

    Object[] args = new Object[] { "Bar", "Foo", null };
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("args", args);
    String jsonString = JSONUtils.getJSONString(args, objectMapper);
    System.out.println(jsonString);

  }
}
