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
package com.xeiam.xchange.utils;

import static org.fest.assertions.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * Tests various BigMoney and BigDecimal behavior
 */
public class BigDecimalTest {

  @Test
  public void test() {

    int BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR = 100000000;

    long amount_int = 23385868;

    // FYI DO NOT divide like this
    BigDecimal testAmount = new BigDecimal((double) amount_int / BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR);
    assertThat(testAmount.toPlainString()).isEqualTo("0.2338586800000000132104815975253586657345294952392578125");

    // FYI DO divide like this
    BigDecimal testAmount2 = new BigDecimal(amount_int).divide(new BigDecimal(BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR));
    assertThat(testAmount2.toPlainString()).isEqualTo("0.23385868");

  }
}
