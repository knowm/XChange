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
package com.xeiam.xchange.rest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.utils.Assert;

/**
 * Various HTTP utility methods
 */
public class HttpTemplate {

  public final static String CHARSET_UTF_8 = "UTF-8";

  private final Logger log = LoggerFactory.getLogger(HttpTemplate.class);

  private ObjectMapper objectMapper;

  /**
   * Default request header fields
   */
  private Map<String, String> defaultHttpHeaders = new HashMap<String, String>();

  /**
   * Constructor
   */
  public HttpTemplate() {

    objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // Always use UTF8
    defaultHttpHeaders.put("Accept-Charset", CHARSET_UTF_8);
    // Assume form encoding by default (typically becomes application/json or application/xml)
    defaultHttpHeaders.put("Content-Type", "application/x-www-form-urlencoded");
    // Accept text/plain by default (typically becomes application/json or application/xml)
    defaultHttpHeaders.put("Accept", "text/plain");
    // User agent provides statistics for servers, but some use it for content negotiation so fake good agents
    defaultHttpHeaders.put("User-Agent", "XChange/1.4.1-SNAPSHOT JDK/6 AppleWebKit/535.7 Chrome/16.0.912.36 Safari/535.7"); // custom User-Agent
  }

  /**
   * Requests JSON via an HTTP POST
   * 
   * @param urlString A string representation of a URL
   * @param returnType The required return type
   * @param requestBody The contents of the request body
   * @param httpHeaders Any custom header values (application/json is provided automatically)
   * @param method Http method (usually GET or POST)
   * @param contentType the mime type to be set as the value of the Content-Type header
   * @return String - the fetched JSON String
   */
  public <T> T executeRequest(String urlString, Class<T> returnType, String requestBody, Map<String, String> httpHeaders, HttpMethod method, String contentType) {

    Assert.notNull(httpHeaders, "httpHeaders should not be null");

    httpHeaders.put("Accept", "application/json");
    if (contentType != null) {
      httpHeaders.put("Content-Type", contentType);
    }

    String response = executeRequest(urlString, requestBody, httpHeaders, method);

    return JSONUtils.getJsonObject(response, returnType, objectMapper);
  }

  /**
   * Provides an internal convenience method to allow easy overriding by test classes
   * 
   * @param method The HTTP method (e.g. GET, POST etc)
   * @param urlString A string representation of a URL
   * @param httpHeaders The HTTP headers (will override the defaults)
   * @param contentLength
   * @return An HttpURLConnection based on the given parameters
   * @throws IOException If something goes wrong
   */
  private HttpURLConnection configureURLConnection(HttpMethod method, String urlString, Map<String, String> httpHeaders, int contentLength) throws IOException {

    Assert.notNull(method, "method cannot be null");
    Assert.notNull(method, "urlString cannot be null");
    Assert.notNull(method, "httpHeaders cannot be null");

    HttpURLConnection connection = getHttpURLConnection(urlString);
    connection.setRequestMethod(method.name());

    // Copy default HTTP headers
    Map<String, String> headerKeyValues = new HashMap<String, String>(defaultHttpHeaders);

    // Merge defaultHttpHeaders with httpHeaders
    headerKeyValues.putAll(httpHeaders);

    // Add HTTP headers to the request
    for (Map.Entry<String, String> entry : headerKeyValues.entrySet()) {
      connection.setRequestProperty(entry.getKey(), entry.getValue());
      log.trace("Header request property: key='{}', value='{}'", entry.getKey(), entry.getValue());
    }

    // Perform additional configuration for POST
    if (contentLength > 0) {
      connection.setDoOutput(true);
      connection.setDoInput(true);

      // Add content length to header
      connection.setRequestProperty("Content-Length", Integer.toString(contentLength));
    }

    return connection;
  }

  /**
   * @param urlString
   * @return a HttpURLConnection instance
   * @throws IOException
   */
  protected HttpURLConnection getHttpURLConnection(String urlString) throws IOException {

    return (HttpURLConnection) new URL(urlString).openConnection();
  }

  /**
   * Executes an HTTP request with the given parameters.
   * 
   * @param method The HTTP method (e.g. GET, POST etc)
   * @param urlString A string representation of a URL
   * @param httpHeaders The HTTP headers (will override the defaults)
   * @param requestBody The request body (typically only for POST method)
   * @return The contents of the response body as String
   */
  private String executeRequest(String urlString, String requestBody, Map<String, String> httpHeaders, HttpMethod method) {

    Assert.notNull(urlString, "urlString cannot be null");
    Assert.notNull(httpHeaders, "httpHeaders cannot be null");

    log.debug("Executing {} request at {}", method, urlString);
    log.trace("Request body = {}", requestBody);
    log.trace("Request headers = {}", httpHeaders);

    String responseString = "";
    HttpURLConnection connection = null;
    try {
      int contentLength = requestBody == null ? 0 : requestBody.length();
      connection = configureURLConnection(method, urlString, httpHeaders, contentLength);

      if (contentLength > 0) {
        // Write the request body
        connection.getOutputStream().write(requestBody.getBytes(CHARSET_UTF_8));
      }

      // Minimize the impact of the HttpURLConnection on the job of getting the data
      String responseEncoding = getResponseEncoding(connection);
      InputStream inputStream = connection.getInputStream();

      int httpStatus = connection.getResponseCode();
      log.debug("Request http status = {}", httpStatus);

      // Get the data
      responseString = readInputStreamAsEncodedString(inputStream, responseEncoding);

    } catch (MalformedURLException e) {
      throw new HttpException("Problem " + method + "ing -- malformed URL: " + urlString, e);
    } catch (IOException e) {
      throw new HttpException("Problem " + method + "ing (IO)", e);
    } finally {
      // This is a bit messy
      if (connection != null && connection instanceof HttpURLConnection) {
        connection.disconnect();
      }
    }

    log.debug("Response body: {}", responseString);
    return responseString;
  }

  /**
   * <p>
   * Reads an InputStream as a String allowing for different encoding types
   * </p>
   * 
   * @param inputStream The input stream
   * @param responseEncoding The encoding to use when converting to a String
   * @return A String representation of the input stream
   * @throws IOException If something goes wrong
   */
  String readInputStreamAsEncodedString(InputStream inputStream, String responseEncoding) throws IOException {

    String responseString;

    if (responseEncoding != null) {
      // Have an encoding so use it
      StringBuilder sb = new StringBuilder();
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, responseEncoding));
      for (String line; (line = reader.readLine()) != null;) {
        sb.append(line);
      }

      responseString = sb.toString();

    } else {
      // No encoding specified so use a BufferedInputStream
      StringBuilder sb = new StringBuilder();
      BufferedInputStream bis = new BufferedInputStream(inputStream);
      byte[] byteContents = new byte[4096];

      int bytesRead;
      String strContents;
      while ((bytesRead = bis.read(byteContents)) != -1) {
        strContents = new String(byteContents, 0, bytesRead);
        sb.append(strContents);
      }

      responseString = sb.toString();
    }

    // System.out.println("responseString: " + responseString);

    return responseString;
  }

  /**
   * Determine the response encoding if specified
   * 
   * @param connection The HTTP connection
   * @return The response encoding as a string (taken from "Content-Type")
   */
  private String getResponseEncoding(URLConnection connection) {

    String charset = null;

    String contentType = connection.getHeaderField("Content-Type");
    if (contentType != null) {
      for (String param : contentType.replace(" ", "").split(";")) {
        if (param.startsWith("charset=")) {
          charset = param.split("=", 2)[1];
          break;
        }
      }
    }
    return charset;
  }

}
