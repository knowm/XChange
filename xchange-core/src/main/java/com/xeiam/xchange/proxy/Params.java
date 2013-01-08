/**
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.proxy;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class provides support for various types of HTTP params, especially in the context of RESTful web services,
 * but may be also used to construct urls in other contexts.
 * <p/>
 * Eg. this can be used to produce a URL query string:
 * <p>Params.of("username", "john", "score", 2, "answer", "yes/no").asQueryString()</p>
 * will produce:
 * <p>username=john&score=2&answer=yes%2Fno</p>
 * @author Matija Mazi <br/>
 */
public class Params implements Serializable {

  private Map<String, Object> data = new LinkedHashMap<String, Object>();
  private AllParams allParams;

  /**
   * private Constructor to prevent instantiation
   */
  private Params() {

  }

  public static Params of() {

    return new Params();
  }

  public static Params of(String param, Object value) {

    return of().add(param, value);
  }

  public static Params of(String p1, Object v1, String p2, Object v2) {

    return of(p1, v1).add(p2, v2);
  }

  public static Params of(String p1, Object v1, String p2, Object v2, String p3, Object v3) {

    return of(p1, v1, p2, v2).add(p3, v3);
  }

  public static Params of(String p1, Object v1, String p2, Object v2, String p3, Object v3, String p4, Object v4) {

    return of(p1, v1, p2, v2, p3, v3).add(p4, v4);
  }

  public Params add(String param, Object value) {

    data.put(param, value);
    return this;
  }

  private String toQueryString(boolean encode) {

    StringBuilder b = new StringBuilder();
    for (String param : data.keySet()) {
      if (isParamSet(param)) {
        if (b.length() > 0) {
          b.append('&');
        }
        b.append(param).append('=').append(encode(getParamValue(param), encode));
      }
    }
    return b.toString();
  }

  private String encode(String data, boolean encode) {

    try {
      return encode ? URLEncoder.encode(data, "UTF-8") : data;
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Illegal encoding, fix the code.", e); // should not happen
    }
  }

  void setAllParams(AllParams allParams) {

    this.allParams = allParams;
  }

  public String asQueryString() {

    return toQueryString(true);
  }

  public String asFormEncodedPostBody() {

    return toQueryString(false);
  }

  public String applyToPath(String path) {

    for (String paramName : data.keySet()) {
      if (isParamSet(paramName)) {
        path = path.replace("{" + paramName + "}", getParamValue(paramName));
      }
    }
    return path;
  }

  public Map<String, String> asHttpHeaders() {

    Map<String, String> stringMap = new LinkedHashMap<String, String>();
    for (String key : data.keySet()) {
      if (isParamSet(key)) {
        stringMap.put(key, getParamValue(key));
      }
    }
    return stringMap;
  }

  private String getParamValue(String key) {

    Object paramValue = data.get(key);
    if (paramValue instanceof ParamsDigest) {
      return ((ParamsDigest) paramValue).digestParams(allParams);
    }
    return paramValue.toString();
  }

  private boolean isParamSet(String key) {
    return data.containsKey(key) && data.get(key) != null;
  }

  @Override
  public String toString() {

    return toQueryString(false);
  }
}
