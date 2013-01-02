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
package com.xeiam.xchange.bitstamp.polling;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Matija Mazi <br/>
 * @created 1/2/13 5:52 PM
 */
public class QueryStringBuilder {
  private Map<String, Object> data = new LinkedHashMap<String, Object>();

  public QueryStringBuilder add(String param, Object value) {

    data.put(param, value);
    return this;
  }

  public static QueryStringBuilder of() {
    return new QueryStringBuilder();
  }

  public static QueryStringBuilder of(String param, Object value) {
    return new QueryStringBuilder().add(param, value);
  }

  public static QueryStringBuilder of(String p1, Object v1, String p2, Object v2) {
    return of(p1, v1).add(p2, v2);
  }

  public static QueryStringBuilder of(String p1, Object v1, String p2, Object v2, String p3, Object v3) {
    return of(p1, v1, p2, v2).add(p3, v3);
  }

  @Override
  public String toString() {

    return toString(false);
  }

  public String toString(boolean encode) {
    StringBuilder b = new StringBuilder();
    for (String param : data.keySet()) {
      if (b.length() > 0) {
        b.append('&');
      }
        b.append(param).append('=').append(encode(data.get(param), encode));
    }
    return b.toString();
  }

  private String encode(Object data, boolean encode)  {
    try {
      return encode ? URLEncoder.encode(data.toString(), "UTF-8") : data.toString();
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Illegal encoding, fix the code.", e); // should not happen
    }
  }
}
