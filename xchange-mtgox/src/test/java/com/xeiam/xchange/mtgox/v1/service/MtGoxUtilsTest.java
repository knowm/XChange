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
package com.xeiam.xchange.mtgox.v1.service;

import static org.junit.Assert.assertEquals;

import org.joda.money.BigMoney;
import org.junit.Test;

import com.xeiam.xchange.mtgox.v1.MtGoxUtils;
import com.xeiam.xchange.utils.MoneyUtils;

/**
 * Test class for MtGoxUtils class
 */
public class MtGoxUtilsTest {

  @Test
  public void testJPYScaling() {

    BigMoney priceJPY = MoneyUtils.parseFiat("JPY 544.44");
    String mtGoxRequestStringJPY = MtGoxUtils.getPriceString(priceJPY);
    // System.out.println(mtGoxRequestStringJPY);

    BigMoney priceUSD = MoneyUtils.parseFiat("USD 5.4444");
    String mtGoxRequestStringUSD = MtGoxUtils.getPriceString(priceUSD);
    // System.out.println(mtGoxRequestStringUSD);

    assertEquals("Unexpected value", mtGoxRequestStringJPY, mtGoxRequestStringUSD);

  }
}
