package com.xeiam.xchange.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * <p>
 * Test utility to provide the following to test classes:
 * </p>
 * <ul>
 * <li>Handy tools for working with mock HTTP URL connections</li>
 * </ul>
 */

public class HttpURLConnectionUtils {

  /**
   * Mocking HttpURLConnection through JMockit leads to problems with URL constructors that introduce very complex workarounds. In the interests of simplicity an implementation approach is used.
   * 
   * @param resourcePath A classpath resource for the input stream to use in the response
   * @return A mock HttpURLConnection
   * @throws java.net.MalformedURLException If something goes wrong
   */
  public static HttpURLConnection configureMockHttpURLConnectionForGet(final String resourcePath) throws MalformedURLException {

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
