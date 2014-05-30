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
package com.xeiam.xchange.cryptsy.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ObsessiveOrange
 */
public class CryptsyGenericReturn<V> {

  private final boolean success;
  private final V returnValue;
  private final String error;

  @JsonCreator
  public CryptsyGenericReturn(@JsonProperty("success") int success, @JsonProperty("return") V returnValue, @JsonProperty("error") String error) {

    this.success = (success == 1 ? true : false);
    this.returnValue = returnValue;
    this.error = error;
  }

  public boolean isSuccess() {

    return success;
  }

  public V getReturnValue() {

    return returnValue;
  }

  public String getError() {

    return error;
  }

  @Override
  public String toString() {

    return String.format("CryptsyReturn[%s: %s]", success ? "OK" : "error", success ? returnValue.toString() : error);
  }
}
