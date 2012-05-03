/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.xeiam.xchange.dto.trade.AccountInfo;

/**
 * Test class for testing HttpTemplate methods
 */
public class HttpTemplateTest {

  @Test
  public void testGetForJsonObject() throws Exception {

    // Configure to use the example JSON objects
    final HttpURLConnection mockHttpURLConnection = configureMockHttpURLConnectionForGet("/marketdata/example-ticker.json");

    // Provide a mocked out HttpURLConnection
    HttpTemplate testObject = new HttpTemplate() {
      @Override
      HttpURLConnection getHttpURLConnection(String urlString) throws IOException {
        return mockHttpURLConnection;
      }
    };

    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, String> httpHeaders = new HashMap<String, String>();

    // Perform the test
    testObject.getForJsonObject("http://example.com/ticker", DummyTicker.class, objectMapper, httpHeaders);

    // Verify the results

  }

  @Test
  public void testReadInputStreamAsEncodedString() throws Exception {

    HttpTemplate testObject = new HttpTemplate();

    InputStream inputStream = HttpTemplateTest.class.getResourceAsStream("/text/example-httpdata.txt");
    assertEquals("Test data", testObject.readInputStreamAsEncodedString(inputStream, "UTF-8"));

  }

  @Test
  public void testPostForJsonObject() throws Exception {

    // Configure to use the example JSON objects
    final HttpURLConnection mockHttpURLConnection = configureMockHttpURLConnectionForPost("/trade/example-accountinfo-data.json");

    // Configure the test object (overridden methods are tested elsewhere)
    HttpTemplate testObject = new HttpTemplate() {
      @Override
      HttpURLConnection getHttpURLConnection(String urlString) throws IOException {
        return mockHttpURLConnection;
      }
    };

    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, String> httpHeaders = new HashMap<String, String>();

    AccountInfo accountInfo = testObject.postForJsonObject("http://example.org/accountinfo", AccountInfo.class, "Example", objectMapper, httpHeaders);

    assertEquals("test", accountInfo.getUsername());

  }

  /**
   * Mocking HttpURLConnection through JMockit leads to problems with URL constructors that introduce very complex workarounds. In the interests of simplicity an implementation approach is used.
   * 
   * @param resourcePath A classpath resource for the input stream to use in the response
   * @return A mock HttpURLConnection
   * @throws MalformedURLException If something goes wrong
   */
  private HttpURLConnection configureMockHttpURLConnectionForPost(final String resourcePath) throws MalformedURLException {
    return new HttpURLConnection(new URL("http://example.org")) {
      @Override
      public void disconnect() {
      }

      @Override
      public boolean usingProxy() {
        return false;
      }

      @Override
      public void connect() throws IOException {
      }

      @Override
      public InputStream getInputStream() throws IOException {
        return HttpTemplateTest.class.getResourceAsStream(resourcePath);
      }

      @Override
      public OutputStream getOutputStream() throws IOException {
        return new ByteArrayOutputStream();
      }

      @Override
      public String getHeaderField(String s) {
        if ("Content-Type".equalsIgnoreCase(s)) {
          // Provide a Windows charset
          return "application/json; charset=cp1252";
        }
        return null;
      }

    };

  }

  /**
   * Mocking HttpURLConnection through JMockit leads to problems with URL constructors that introduce very complex workarounds. In the interests of simplicity an implementation approach is used.
   * 
   * @param resourcePath A classpath resource for the input stream to use in the response
   * @return A mock HttpURLConnection
   * @throws MalformedURLException If something goes wrong
   */
  private HttpURLConnection configureMockHttpURLConnectionForGet(final String resourcePath) throws MalformedURLException {
    return new HttpURLConnection(new URL("http://example.org")) {
      @Override
      public void disconnect() {
      }

      @Override
      public boolean usingProxy() {
        return false;
      }

      @Override
      public void connect() throws IOException {
      }

      @Override
      public InputStream getInputStream() throws IOException {
        return HttpTemplateTest.class.getResourceAsStream(resourcePath);
      }

      @Override
      public String getHeaderField(String s) {
        return null;
      }

    };

  }

}
