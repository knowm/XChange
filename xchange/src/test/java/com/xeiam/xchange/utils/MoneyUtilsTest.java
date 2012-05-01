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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.joda.money.BigMoney;
import org.junit.Test;

public class MoneyUtilsTest {

  /**
   * Tests the constructors
   */
  @Test
  public void testConstructors() {
    // String
    // create a monetary value
    BigMoney testObject1 = MoneyUtils.parseFiat("USD 23.87");
    assertEquals("USD", testObject1.getCurrencyUnit().getCurrencyCode());
    BigMoney testObject2 = MoneyUtils.parseBitcoin("BTC 1");
    assertEquals("BTC", testObject2.getCurrencyUnit().getCurrencyCode());
  }

  /**
   * Tests incompatible currencies
   */
  @Test
  public void testIncompatibleCurrencies() {
    BigMoney testObject1 = MoneyUtils.parseFiat("USD 10.50");
    BigMoney testObject2 = MoneyUtils.parseFiat("GBP 10.50");
    try {
      testObject1.plus(testObject2);
      fail("Expected exception");
    } catch (IllegalArgumentException e) {
      // Do nothing
    }
  }

  /**
   * Tests the various comparison operators
   */
  @Test
  public void testComparisonOperators() {
    BigMoney testObject1 = MoneyUtils.parseFiat("USD 10.50");
    BigMoney testObject2 = MoneyUtils.parseFiat("USD 10.50");
    BigMoney testObject3 = MoneyUtils.parseFiat("USD 10.51");
    // GT
    assertFalse(testObject1.isGreaterThan(testObject2));
    assertFalse(testObject2.isGreaterThan(testObject1));
    assertTrue(testObject3.isGreaterThan(testObject1));
    // LT
    assertFalse(testObject1.isLessThan(testObject2));
    assertFalse(testObject2.isLessThan(testObject1));
    assertTrue(testObject1.isLessThan(testObject3));
    // Compare to
    assertEquals(-1, testObject1.compareTo(testObject3));
    assertEquals(0, testObject1.compareTo(testObject2));
    assertEquals(1, testObject3.compareTo(testObject1));
  }

  /**
   * Tests addition of simple numbers over several iterations
   */
  @Test
  public void testSimpleAdd() {
    String[] column = new String[] { "1.0", "2.0", "3.0", "4.5" };

    BigMoney testObject = MoneyUtils.parseFiat("USD 0.00");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.plus(MoneyUtils.parseFiat("USD " + column[i]));
    }

    assertEquals("Unexpected value", "USD 10.50", testObject.toString());
  }

  /**
   * Verifies ability to handle non-binary fractions over several iterations
   */
  @Test
  public void testComplexAdd() {
    String[] column = new String[] { "3.33", "3.33", "3.34", "3.43", "7.99", "6.53" };

    BigMoney testObject = MoneyUtils.parseFiat("USD 0");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.plus(MoneyUtils.parseFiat("USD " + column[i]));
    }

    assertEquals("Unexpected value", "USD 27.95", testObject.toString());
  }

  /**
   * Verifies ability to handle non-binary fractions over several iterations
   */
  @Test
  public void testSimpleSubtract() {
    String[] column = new String[] { "1.0", "2.0", "3.0", "4.5" };

    BigMoney testObject = MoneyUtils.parseFiat("USD 0.00");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.minus(MoneyUtils.parseFiat("USD " + column[i]));
    }

    assertEquals("Unexpected value", "USD -10.50", testObject.toString());
  }

  /**
   * Verifies ability to handle non-binary fractions over several iterations
   */
  @Test
  public void testComplexSubtract() {
    String[] column = new String[] { "3.33", "3.33", "3.34", "3.43", "7.99", "6.53" };

    BigMoney testObject = MoneyUtils.parseFiat("USD 0");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.minus(MoneyUtils.parseFiat("USD " + column[i]));
    }

    assertEquals("Unexpected value", "USD -27.95", testObject.toString());
  }

  /**
   * Verifies ability to handle simple multiplications over several iterations
   */
  @Test
  public void testSimpleMultiply() {
    String[] column = new String[] { "1.0", "2.0", "3.0", "4.5" };

    BigMoney testObject = MoneyUtils.parseFiat("USD 10.00");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.multipliedBy(new BigDecimal(column[i]));
    }

    // Exact answer is 270.0
    assertEquals("Unexpected value", "USD 270.00000", testObject.toString());
  }

  /**
   * Verifies ability to handle simple integer multiplications over several iterations
   */
  @Test
  public void testSimpleIntegerMultiply() {
    String[] column = new String[] { "1", "2", "3", "4" };

    BigMoney testObject = MoneyUtils.parseFiat("USD 10.00");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.multipliedBy(new BigDecimal(column[i]));
    }

    // Exact answer is 240
    assertEquals("Unexpected value", "USD 240.00", testObject.toString());
  }

  /**
   * Verifies ability to handle non-binary fractions over several iterations
   */
  @Test
  public void testComplexMultiply() {
    String[] column = new String[] { "3.33", "3.33", "3.34", "3.43", "7.99", "6.53" };

    BigMoney testObject = MoneyUtils.parseFiat("USD 13.33");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.multipliedBy(new BigDecimal(column[i]));
    }

    // Exact answer (as calculated) is 88352.56533784463118
    assertEquals("Unexpected value", "USD 88352.56533784463118", testObject.toString());
  }

  /**
   * Verifies ability to handle simple fractions over several iterations
   */
  @Test
  public void testSimpleDivide() {
    String[] column = new String[] { "1.0", "2.0", "3.0", "4.5" };

    BigMoney testObject = MoneyUtils.parseFiat("USD 270.00");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.dividedBy(new BigDecimal(column[i]), RoundingMode.HALF_EVEN);
    }

    // Exact answer (as calculated) is 10
    assertEquals("Unexpected value", "USD 10.00", testObject.toString());
  }

  /**
   * Verifies ability to handle simple integer fractions over several iterations
   */
  @Test
  public void testSimpleIntegerDivide() {
    String[] column = new String[] { "1", "2", "3", "4" };

    BigMoney testObject = MoneyUtils.parseFiat("USD 270.00");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.dividedBy(new BigDecimal(column[i]), RoundingMode.HALF_EVEN);
    }

    // Exact answer (as calculated) is 11.25
    assertEquals("Unexpected value", "USD 11.25", testObject.toString());
  }

  /**
   * Verifies ability to handle simple integer divisions over several iterations followed by a series of integer multiplications to establish preservation of rounding
   */
  @Test
  public void testComplexIntegerDivideAndMultiply() {
    String[] column = new String[] { "1", "2", "3", "4" };

    BigMoney testObject = MoneyUtils.parseFiat("USD 270.00");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.dividedBy(new BigDecimal(column[i]), RoundingMode.HALF_EVEN);
    }
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.multipliedBy(new BigDecimal(column[i]));
    }

    // Exact answer (as calculated) is 1) 11.25 2) 270
    assertEquals("Unexpected value", "USD 270.00", testObject.toString());
  }

  /**
   * Verifies ability to handle non-binary fractions over several iterations
   */
  @Test
  public void testComplexDivide() {
    String[] column = new String[] { "3.33", "3.33", "3.34", "3.43", "7.99", "6.53" };

    BigMoney testObject = MoneyUtils.parseFiat("USD 435700.42");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.dividedBy(new BigDecimal(column[i]), RoundingMode.HALF_EVEN);
    }

    // Exact answer (as calculated) is 65.735347653932
    // Locale representation is 2dp
    assertEquals("Unexpected value", "USD 65.74", testObject.toString());
  }

  /**
   * Verifies ability to handle non-binary divisions and multiplications over several iterations to high precision
   */
  @Test
  public void testComplexDivideAndMultiplyBitcoin() {
    String[] column = new String[] { "3.33", "3.33", "3.34", "3.43", "7.99", "6.53" };

    BigMoney testObject = MoneyUtils.parseBitcoin("BTC 20999999.12345678");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.dividedBy(new BigDecimal(column[i]), RoundingMode.HALF_EVEN);
    }

    // Exact answer (as calculated) is 3168.32892452274544
    assertEquals("Unexpected value", "BTC 3168.328924522745", testObject.toString());

    for (int i = 0; i < column.length; i++) {
      testObject = testObject.multipliedBy(new BigDecimal(column[i]));
    }

    // Exact answer (as calculated) is 20999999.12345678
    assertEquals("Unexpected value", "BTC 20999999.12345678", MoneyUtils.formatBitcoin(testObject));

  }

  /**
   * Verifies ability to handle non-binary divisions and multiplications over several iterations to high precision using Satoshis as the starting point
   */
  @Test
  public void testComplexDivideAndMultiplySatoshi() {
    String[] column = new String[] { "3.33", "3.33", "3.34", "3.43", "7.99", "6.53" };

    BigMoney testObject = MoneyUtils.fromSatoshi(2099999912345678L);
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.dividedBy(new BigDecimal(column[i]), RoundingMode.HALF_EVEN);
    }

    // Exact answer (as calculated) is 3168.32892452274544
    assertEquals("Unexpected value", "BTC 3168.328924522745", testObject.toString());

    for (int i = 0; i < column.length; i++) {
      testObject = testObject.multipliedBy(new BigDecimal(column[i]));
    }

    // Exact answer (as calculated) is 20999999.12345678
    assertEquals("Unexpected value", "BTC 20999999.12345678", MoneyUtils.formatBitcoin(testObject));

  }

}
