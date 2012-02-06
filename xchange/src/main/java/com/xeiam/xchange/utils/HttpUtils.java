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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.exceptions.HttpException;

public class HttpUtils {

  private static final String charset = "UTF-8";

  /**
   * Provides logging for this class
   */
  private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

  /**
   * default request header fields
   */
  private static final Map<String, String> defaultHeaderKeyValues = new HashMap<String, String>();
  static {
    defaultHeaderKeyValues.put("Accept-Charset", charset);
    defaultHeaderKeyValues.put("Accept", "text/plain");
    defaultHeaderKeyValues.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.36 Safari/535.7");

  }

  /**
   * @reference http://stackoverflow.com/questions/2793150/how-to-use-java-net-urlconnection-to-fire-and-handle-http-requests
   * @param urlString
   * @param params
   * @return String - the fetched Response String
   */
  private static String getHttpResponse(String urlString, Map<String, String> customHeaderKeyValues) throws HttpException {

    String responseString = "";
    HttpURLConnection conn = null;
    try {

      URL url = new URL(urlString);
      conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");

      // header key values
      Map<String, String> headerKeyValues = new HashMap<String, String>(defaultHeaderKeyValues);
      // add/override defaultHeaderKeyValues with customHeaderKeyValues
      headerKeyValues.putAll(customHeaderKeyValues);
      for (String key : headerKeyValues.keySet()) {
        conn.setRequestProperty(key, headerKeyValues.get(key));
        log.debug("header request property: key= " + key + ", value= " + headerKeyValues.get(key));
      }

      if (conn.getResponseCode() != 200) {
        throw new HttpException("Problem getting JSON (response code: " + conn.getResponseCode() + ")");
      }

      // for (Entry<String, List<String>> header : conn.getHeaderFields().entrySet()) {
      // log.debug(header.getKey() + "=" + header.getValue());
      // }

      InputStream response = conn.getInputStream();
      String resonseEncoding = getResponseEncoding(conn);

      if (resonseEncoding != null) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(response, resonseEncoding));
        for (String line; (line = reader.readLine()) != null;) {
          // log.debug(line);
          sb.append(line);
        }
        responseString = sb.toString();

      } else {

        // Guava
        // json = new String(ByteStreams.toByteArray(response));

        // Standard Java Library
        BufferedInputStream bis = null;
        bis = new BufferedInputStream(response);
        byte[] contents = new byte[1024];

        int bytesRead = 0;
        String strFileContents = null;
        while ((bytesRead = bis.read(contents)) != -1) {
          strFileContents = new String(contents, 0, bytesRead);
        }
        responseString = strFileContents;

      }

    } catch (MalformedURLException e) {
      throw new HttpException("Problem getting JSON (malformed URL)", e);
    } catch (IOException e) {
      throw new HttpException("Problem getting JSON (IO)", e);
    } finally {
      if (conn != null) {
        conn.disconnect();
      }
    }
    return responseString;
  }

  /**
   * @reference http://stackoverflow.com/questions/2793150/how-to-use-java-net-urlconnection-to-fire-and-handle-http-requests
   * @param urlString
   * @param params
   * @return String - the fetched JSON String
   */
  public static String getJSON(String urlString) throws HttpException {

    String json = "";
    HttpURLConnection conn = null;
    try {

      URL url = new URL(urlString);
      conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Accept-Charset", charset);
      conn.setRequestProperty("Accept", "application/json");
      conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.36 Safari/535.7");

      if (conn.getResponseCode() != 200) {
        throw new HttpException("Problem getting JSON (response code: " + conn.getResponseCode() + ")");
      }

      // for (Entry<String, List<String>> header : conn.getHeaderFields().entrySet()) {
      // log.debug(header.getKey() + "=" + header.getValue());
      // }

      InputStream response = conn.getInputStream();
      String resonseEncoding = getResponseEncoding(conn);

      if (resonseEncoding != null) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(response, resonseEncoding));
        for (String line; (line = reader.readLine()) != null;) {
          // log.debug(line);
          sb.append(line);
        }
        json = sb.toString();

      } else {

        // Guava
        // json = new String(ByteStreams.toByteArray(response));

        // Standard Java Library
        BufferedInputStream bis = null;
        bis = new BufferedInputStream(response);
        byte[] contents = new byte[1024];

        int bytesRead = 0;
        String strFileContents = null;
        while ((bytesRead = bis.read(contents)) != -1) {
          strFileContents = new String(contents, 0, bytesRead);
        }
        json = strFileContents;

      }

    } catch (MalformedURLException e) {
      throw new HttpException("Problem getting JSON (malformed URL)", e);
    } catch (IOException e) {
      throw new HttpException("Problem getting JSON (IO)", e);
    } finally {
      if (conn != null) {
        conn.disconnect();
      }
    }
    return json;
  }

  /**
   * Determine the response encoding if specified
   * 
   * @param conn
   * @return
   */
  private static String getResponseEncoding(HttpURLConnection conn) {

    String contentType = conn.getHeaderField("Content-Type");
    String charset = null;
    for (String param : contentType.replace(" ", "").split(";")) {
      // log.debug(param);
      if (param.startsWith("charset=")) {
        charset = param.split("=", 2)[1];
        break;
      }
    }
    return charset;
  }

  /**
   * @param params
   * @param charset
   * @return
   */
  public static String getQuery(Map<String, String> params, String charset) throws UnsupportedEncodingException {

    StringBuilder sb = new StringBuilder();

    for (String key : params.keySet()) {
      sb.append(key);
      sb.append("=");
      sb.append(params.get(key));
      sb.append("&");
    }
    String query = sb.toString();

    return URLEncoder.encode(query.substring(0, query.length() - 1), charset);
  }

  // /**
  // * @param urlString
  // * @return
  // */
  // public static boolean ping(String urlString) {
  //
  // HttpURLConnection connection = null;
  // try {
  // connection = (HttpURLConnection) new URL(urlString).openConnection();
  // connection.setRequestMethod("HEAD");
  // connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.36 Safari/535.7");
  // int responseCode = connection.getResponseCode();
  // if (responseCode != 200) {
  // return false;
  // } else {
  // return true;
  // }
  // } catch (Exception e) {
  // log.warn("Exception Pinging! url= " + urlString, e);
  // return false;
  // } finally {
  // if (connection != null) {
  // connection.disconnect();
  // }
  // }
  // }

}
