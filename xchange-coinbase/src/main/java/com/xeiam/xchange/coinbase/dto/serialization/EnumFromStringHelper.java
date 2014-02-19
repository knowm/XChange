/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.coinbase.dto.serialization;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jamespedwards42
 */
public class EnumFromStringHelper<T extends Enum<T>> {

  private final Map<String, T> fromString = new HashMap<String, T>();

  public EnumFromStringHelper(Class<T> enumClass) {

    for (final T enumVal : enumClass.getEnumConstants())
      fromString.put(enumVal.toString().toLowerCase(), enumVal);
  }

  public EnumFromStringHelper<T> addJsonStringMapping(final String jsonString, final T enumVal) {

    fromString.put(jsonString, enumVal);
    return this;
  }
  
  public T fromJsonString(final String jsonString) {
    
    return fromString.get(jsonString.toLowerCase());
  }
}
