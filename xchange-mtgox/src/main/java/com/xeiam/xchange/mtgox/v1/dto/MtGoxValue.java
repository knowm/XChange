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
package com.xeiam.xchange.mtgox.v1.dto;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Data object representing a Value from Mt Gox
 * 

 */
public final class MtGoxValue {

  private final BigDecimal value;
  private final long valueInt;
  private final String display;
  private final String displayShort;
  private final String currency;

  /**
   * Constructor
   * 
   * @param value
   * @param valueInt
   * @param display
   * @param displayShort
   * @param currency
   */
  public MtGoxValue(@JsonProperty("value") BigDecimal value, @JsonProperty("value_int") long valueInt, @JsonProperty("display") String display, @JsonProperty("display_short") String displayShort,
      @JsonProperty("currency") String currency) {

    this.value = value;
    this.valueInt = valueInt;
    this.display = display;
    this.displayShort = displayShort;
    this.currency = currency;
  }

  public BigDecimal getValue() {

    return value;
  }

  public long getValueInt() {

    return valueInt;
  }

  public String getDisplay() {

    return display;
  }

  public String getDisplayShort() {

    return displayShort;
  }

  public String getCurrency() {

    return currency;
  }

  @Override
  public String toString() {

    return "MtGoxValue [value=" + value + ", valueInt=" + valueInt + ", display=" + display + ", displayShort=" + displayShort + ", currency=" + currency + "]";
  }

}
