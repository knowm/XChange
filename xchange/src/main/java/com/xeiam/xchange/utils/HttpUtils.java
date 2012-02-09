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

import com.xeiam.xchange.HttpException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Various HTTP utility methods
 */
public class HttpUtils {

  public static final String CHARSET_UTF_8 = "UTF-8";

  /**
   * Provides logging for this class
   */
  private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

  /**
   * Default request header fields
   */
  private static final Map<String, String> defaultHttpHeaders = new HashMap<String, String>();

  /**
   * Always use UTF8
   * Assume form encoding by default (typically becomes application/json or application/xml)
   * Accept text/plain by default (typically becomes application/json or application/xml)
   * User agent provides statistics for servers, but some use it for content negotiation so fake good agents 
   */
  static {
    defaultHttpHeaders.put("Accept-Charset", CHARSET_UTF_8);
    defaultHttpHeaders.put("Content-Type", "application/x-www-form-urlencoded");
    defaultHttpHeaders.put("Accept", "text/plain");
    defaultHttpHeaders.put("User-Agent", "XChange/0.0.1 JDK/6 AppleWebKit/535.7 Chrome/16.0.912.36 Safari/535.7"); // custom User-Agent
  }

  /**
   * Requests JSON via an HTTP GET and unmarshals it into an object graph
   *
   * @param urlString    A string representation of a URL
   * @param returnType   The required return type
   * @param objectMapper The Jackson ObjectMapper to use
   * @param httpHeaders  Any custom header values (application/json is provided automatically)
   *
   * @return The contents of the response body as the given type mapped through Jackson
   */
  public static <T> T getForJsonObject(String urlString, Class<T> returnType, ObjectMapper objectMapper, Map<String, String> httpHeaders) {

    Assert.notNull(urlString, "urlString cannot be null");
    Assert.notNull(objectMapper, "objectMapper cannot be null");
    Assert.notNull(httpHeaders, "httpHeaders should not be null");
    try {
      httpHeaders.put("Accept", "application/json");
      return objectMapper.readValue(getForString(urlString, httpHeaders), returnType);
    } catch (IOException e) {
      // Rethrow as runtime exception
      throw new HttpException(e.getMessage(), e);
    }
  }

  /**
   * Requests JSON via an HTTP POST
   *
   * @param urlString    A string representation of a URL
   * @param returnType   The required return type
   * @param postBody     The contents of the request body
   * @param objectMapper The Jackson ObjectMapper to use
   * @param httpHeaders  Any custom header values (application/json is provided automatically)
   *
   * @return String - the fetched JSON String
   */
  public static <T> T postForJsonObject(String urlString, Class<T> returnType, String postBody, ObjectMapper objectMapper, Map<String, String> httpHeaders) {

    Assert.notNull(urlString, "urlString cannot be null");
    Assert.notNull(objectMapper, "objectMapper cannot be null");
    Assert.notNull(httpHeaders, "httpHeaders should not be null");

    try {
      httpHeaders.put("Accept", "application/json");
      return objectMapper.readValue(postForString(urlString, postBody, httpHeaders), returnType);
    } catch (IOException e) {
      // Rethrow as runtime exception
      throw new HttpException(e.getMessage(), e);
    }
  }

  /**
   * Send an HTTP GET request, and receive server response
   *
   * @param urlString   A string representation of a URL
   * @param httpHeaders Any custom header values
   *
   * @return The contents of the response body as a String
   *
   * @see <a href="http://stackoverflow.com/questions/2793150/how-to-use-java-net-urlconnection-to-fire-and-handle-http-requests">Stack Overflow on URLConnection</a>
   */
  private static String getForString(String urlString, Map<String, String> httpHeaders) {

    Assert.notNull(httpHeaders, "httpHeaders should not be null");

    String responseString = "";
    HttpURLConnection connection = null;

    try {

      URL url = new URL(urlString);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");

      // Copy default HTTP headers
      Map<String, String> headerKeyValues = new HashMap<String, String>(defaultHttpHeaders);

      // Merge defaultHttpHeaders with httpHeaders
      headerKeyValues.putAll(httpHeaders);

      // Add HTTP headers to the request
      for (Map.Entry<String, String> entry : headerKeyValues.entrySet()) {
        connection.setRequestProperty(entry.getKey(), entry.getValue());
        log.trace("Header request property: key='{}', value='{}'", entry.getKey(), entry.getValue());
      }

      responseString = getResponseString(connection);

    } catch (MalformedURLException e) {
      throw new HttpException("Problem GETing (malformed URL)", e);
    } catch (IOException e) {
      throw new HttpException("Problem GETing (IO)", e);
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
    return responseString;
  }

  /**
   * Send an HTTP POST request, and receive server response
   *
   * @param urlString   A string representation of a URL
   * @param postBody    The contents of the request body
   * @param httpHeaders Any custom header values
   *
   * @return The contents of the response body as a String
   */
  private static String postForString(String urlString, String postBody, Map<String, String> httpHeaders) {

    String responseString = "";
    HttpURLConnection connection = null;
    try {

      URL url = new URL(urlString);
      connection = (HttpURLConnection) url.openConnection();
      connection.setDoOutput(true);
      connection.setDoInput(true);
      connection.setRequestMethod("POST");

      // Copy default HTTP headers
      Map<String, String> headerKeyValues = new HashMap<String, String>(defaultHttpHeaders);

      // Merge defaultHttpHeaders with httpHeaders
      headerKeyValues.putAll(httpHeaders);

      // Add HTTP headers to the request
      for (Map.Entry<String, String> entry : headerKeyValues.entrySet()) {
        connection.setRequestProperty(entry.getKey(), entry.getValue());
        log.trace("Header request property: key='{}', value='{}'", entry.getKey(), entry.getValue());
      }

      // add content length to header
      connection.setRequestProperty("Content-Length", Integer.toString(postBody.length()));

      log.trace("postBody= " + postBody);
      connection.getOutputStream().write(postBody.getBytes(CHARSET_UTF_8));

      responseString = getResponseString(connection);

    } catch (MalformedURLException e) {
      throw new HttpException("Problem POSTing (malformed URL)", e);
    } catch (IOException e) {
      throw new HttpException("Problem POSTing (IO)", e);
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }

    return responseString;
  }

  /**
   * Gets the response String from an HTTP request
   *
   * @param connection The HTTP connection
   *
   * @return The response body as a String
   *
   * @throws IOException If something goes wrong
   */
  private static String getResponseString(HttpURLConnection connection) throws IOException {

    String responseString;

    String responseEncoding = getResponseEncoding(connection);

    // if the server specified an encoding, use a Buffered Reader
    if (responseEncoding != null) {

      StringBuilder sb = new StringBuilder();
      BufferedReader reader;
      reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), responseEncoding));
      for (String line; (line = reader.readLine()) != null; ) {
        log.trace(line);
        sb.append(line);
      }
      responseString = sb.toString();

      // no encoding specified, use a BufferedInputStream
    } else {

      BufferedInputStream bis;
      bis = new BufferedInputStream(connection.getInputStream());
      byte[] byteContents = new byte[1024];

      int bytesRead;
      String strContents = null;
      while ((bytesRead = bis.read(byteContents)) != -1) {
        strContents = new String(byteContents, 0, bytesRead);
        log.trace(strContents);
      }
      responseString = strContents;

    }
    return responseString;
  }

  /**
   * Determine the response encoding if specified
   *
   * @param connection The HTTP connection
   *
   * @return The response encoding as a string (taken from "Content-Type")
   */
  private static String getResponseEncoding(HttpURLConnection connection) {

    String contentType = connection.getHeaderField("Content-Type");
    String charset = null;
    for (String param : contentType.replace(" ", "").split(";")) {
      if (param.startsWith("charset=")) {
        charset = param.split("=", 2)[1];
        break;
      }
    }
    return charset;
  }

}
