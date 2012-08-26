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
package com.xeiam.xchange.mtgox.v1.dto;

/**
 * Data object representing a Value from Mt Gox
 */
public class MtGoxValue {

  private double value;
  private long value_int;
  private String display;
  private String display_short;
  private String currency;

  public double getValue() {

    return value;
  }

  public void setValue(double value) {

    this.value = value;
  }

  public long getValue_int() {

    return value_int;
  }

  public void setValue_int(long value_int) {

    this.value_int = value_int;
  }

  public String getDisplay() {

    return display;
  }

  public void setDisplay(String display) {

    this.display = display;
  }

  public String getDisplay_short() {

    return display_short;
  }

  public void setDisplay_short(String display_short) {

    this.display_short = display_short;
  }

  public String getCurrency() {

    return currency;
  }

  public void setCurrency(String currency) {

    this.currency = currency;
  }

  @Override
  public String toString() {

    return "MtGoxValue [value=" + value + ", value_int=" + value_int + ", display=" + display + ", display_short=" + display_short + ", currency=" + currency + "]";
  }

}
