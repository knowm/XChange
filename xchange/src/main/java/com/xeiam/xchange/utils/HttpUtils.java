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
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.HttpException;

public class HttpUtils {

  public static final String CHARSET_UTF_8 = "UTF-8";

  /**
   * Provides logging for this class
   */
  private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

  /**
   * default request header fields
   */
  private static final Map<String, String> defaultHeaderKeyValues = new HashMap<String, String>();
  static {
    defaultHeaderKeyValues.put("Accept-Charset", CHARSET_UTF_8);
    defaultHeaderKeyValues.put("Content-Type", "application/x-www-form-urlencoded");
    defaultHeaderKeyValues.put("Accept", "text/plain");
    defaultHeaderKeyValues.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.36 Safari/535.7");

  }

  /**
   * Send an HTTP GET request, and recieve server response
   * 
   * @reference http://stackoverflow.com/questions/2793150/how-to-use-java-net-urlconnection-to-fire-and-handle-http-requests
   * @param urlString
   * @param customHeaderKeyValues
   * @return String - the fetched Response String
   */
  private static String httpGET(String urlString, Map<String, String> customHeaderKeyValues) throws HttpException {

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
        // log.debug("header request property: key= " + key + ", value= " + headerKeyValues.get(key));
      }

      // for (Entry<String, List<String>> header : conn.getHeaderFields().entrySet()) {
      // log.debug(header.getKey() + "=" + header.getValue());
      // }

      responseString = getReponseString(conn);

    } catch (MalformedURLException e) {
      throw new HttpException("Problem GETing (malformed URL)", e);
    } catch (IOException e) {
      throw new HttpException("Problem GETing (IO)", e);
    } finally {
      if (conn != null) {
        conn.disconnect();
      }
    }
    return responseString;
  }

  /**
   * Send an HTTP POST request, and recieve server response
   * 
   * @param urlString
   * @param postBody
   * @param customHeaderKeyValues
   * @return
   */
  private static String httpPOST(String urlString, String postBody, Map<String, String> customHeaderKeyValues) {

    String responseString = "";
    HttpURLConnection conn = null;
    try {

      URL url = new URL(urlString);
      conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput(true);
      conn.setDoInput(true);
      conn.setRequestMethod("POST");

      // header key values
      Map<String, String> headerKeyValues = new HashMap<String, String>(defaultHeaderKeyValues);
      // add/override defaultHeaderKeyValues with customHeaderKeyValues
      headerKeyValues.putAll(customHeaderKeyValues);
      for (String key : headerKeyValues.keySet()) {
        conn.setRequestProperty(key, headerKeyValues.get(key));
        log.debug("header request property: key= " + key + ", value= " + headerKeyValues.get(key));
      }
      conn.setRequestProperty("Content-Length", Integer.toString(postBody.length()));

      log.debug("postBody= " + postBody);
      conn.getOutputStream().write(postBody.getBytes(CHARSET_UTF_8));

      // OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
      // out.write(postBody);
      // out.flush();
      // out.close();
      responseString = getReponseString(conn);

    } catch (MalformedURLException e) {
      throw new HttpException("Problem POSTing (malformed URL)", e);
    } catch (IOException e) {
      throw new HttpException("Problem POSTing (IO)", e);
    } finally {
      if (conn != null) {
        conn.disconnect();
      }
    }

    return responseString;

  }

  private static String getReponseString(HttpURLConnection conn) throws IOException {

    String responseString = "";

    String resonseEncoding = getResponseEncoding(conn);

    if (resonseEncoding != null) {

      StringBuilder sb = new StringBuilder();
      BufferedReader reader = null;
      reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), resonseEncoding));
      for (String line; (line = reader.readLine()) != null;) {
        log.debug(line);
        sb.append(line);
      }
      responseString = sb.toString();

    } else {

      BufferedInputStream bis = null;
      bis = new BufferedInputStream(conn.getInputStream());
      byte[] contents = new byte[1024];

      int bytesRead = 0;
      String strFileContents = null;
      while ((bytesRead = bis.read(contents)) != -1) {
        strFileContents = new String(contents, 0, bytesRead);
        log.debug(strFileContents);
      }
      responseString = strFileContents;

    }

    return responseString;
  }

  /**
   * @param urlString
   * @return String - the fetched JSON String
   */
  public static String getJSON(String urlString) throws HttpException {

    Map<String, String> headerKeyValueMap = new HashMap<String, String>();
    headerKeyValueMap.put("Accept", "application/json");
    return httpGET(urlString, headerKeyValueMap);
  }

  /**
   * @param urlString
   * @param postBody
   * @param customHeaderKeyValues
   * @return String - the fetched JSON String
   * @throws HttpException
   */
  public static String getJSON(String urlString, String postBody, Map<String, String> customHeaderKeyValues) throws HttpException {

    Map<String, String> headerKeyValueMap = new HashMap<String, String>();
    headerKeyValueMap.put("Accept", "application/json");
    headerKeyValueMap.putAll(customHeaderKeyValues);
    return httpPOST(urlString, postBody, headerKeyValueMap);
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

  // /**
  // * @param params
  // * @param charset
  // * @return
  // */
  // public static String getQuery(Map<String, String> params, String charset) throws UnsupportedEncodingException {
  //
  // StringBuilder sb = new StringBuilder();
  //
  // for (String key : params.keySet()) {
  // sb.append(key);
  // sb.append("=");
  // sb.append(params.get(key));
  // sb.append("&");
  // }
  // String query = sb.toString();
  //
  // return URLEncoder.encode(query.substring(0, query.length() - 1), charset);
  // }

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
