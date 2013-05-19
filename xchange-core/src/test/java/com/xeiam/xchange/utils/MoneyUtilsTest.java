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
package com.xeiam.xchange.utils;

import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.MoneyUtils;
import org.joda.money.BigMoney;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Fail.fail;

/**
 * Test class for testing MoneyUtils methods
 */
public class MoneyUtilsTest {

  /**
   * Tests the constructors
   */
  @Test
  public void testConstructors() {

    // String
    // create a monetary value
    BigMoney testObject1 = MoneyUtils.parse("USD 23.87");
    assertThat(testObject1.getCurrencyUnit().getCurrencyCode()).isEqualTo("USD");
    BigMoney testObject2 = MoneyUtils.parseBitcoin("BTC 1");
    assertThat(testObject2.getCurrencyUnit().getCurrencyCode()).isEqualTo("BTC");
  }

  /**
   * Tests incompatible currencies
   */
  @Test
  public void testIncompatibleCurrencies() {

    BigMoney testObject1 = MoneyUtils.parse("USD 10.50");
    BigMoney testObject2 = MoneyUtils.parse("GBP 10.50");
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

    BigMoney testObject1 = MoneyUtils.parse("USD 10.50");
    BigMoney testObject2 = MoneyUtils.parse("USD 10.50");
    BigMoney testObject3 = MoneyUtils.parse("USD 10.51");
    // GT
    assertThat(testObject1.isGreaterThan(testObject2)).isFalse();
    assertThat(testObject2.isGreaterThan(testObject1)).isFalse();
    assertThat(testObject3.isGreaterThan(testObject1)).isTrue();
    // LT
    assertThat(testObject1.isLessThan(testObject2)).isFalse();
    assertThat(testObject2.isLessThan(testObject1)).isFalse();
    assertThat(testObject1.isLessThan(testObject3));
    // Compare to
    assertThat(testObject1.compareTo(testObject3)).isEqualTo(-1);
    assertThat(testObject1.compareTo(testObject2)).isEqualTo(0);
    assertThat(testObject3.compareTo(testObject1)).isEqualTo(1);
  }

  /**
   * Tests addition of simple numbers over several iterations
   */
  @Test
  public void testSimpleAdd() {

    String[] column = new String[] { "1.0", "2.0", "3.0", "4.5" };

    BigMoney testObject = MoneyUtils.parse("USD 0.00");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.plus(MoneyUtils.parse("USD " + column[i]));
    }

    assertThat(testObject.toString()).isEqualTo("USD 10.50");
  }

  /**
   * Verifies ability to handle non-binary fractions over several iterations
   */
  @Test
  public void testComplexAdd() {

    String[] column = new String[] { "3.33", "3.33", "3.34", "3.43", "7.99", "6.53" };

    BigMoney testObject = MoneyUtils.parse("USD 0");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.plus(MoneyUtils.parse("USD " + column[i]));
    }

    assertThat(testObject.toString()).isEqualTo("USD 27.95");
  }

  /**
   * Verifies ability to handle non-binary fractions over several iterations
   */
  @Test
  public void testSimpleSubtract() {

    String[] column = new String[] { "1.0", "2.0", "3.0", "4.5" };

    BigMoney testObject = MoneyUtils.parse("USD 0.00");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.minus(MoneyUtils.parse("USD " + column[i]));
    }

    assertThat(testObject.toString()).isEqualTo("USD -10.50");
  }

  /**
   * Verifies ability to handle non-binary fractions over several iterations
   */
  @Test
  public void testComplexSubtract() {

    String[] column = new String[] { "3.33", "3.33", "3.34", "3.43", "7.99", "6.53" };

    BigMoney testObject = MoneyUtils.parse("USD 0");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.minus(MoneyUtils.parse("USD " + column[i]));
    }

    assertThat(testObject.toString()).isEqualTo("USD -27.95");
  }

  /**
   * Verifies ability to handle simple multiplications over several iterations
   */
  @Test
  public void testSimpleMultiply() {

    String[] column = new String[] { "1.0", "2.0", "3.0", "4.5" };

    BigMoney testObject = MoneyUtils.parse("USD 10.00");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.multipliedBy(new BigDecimal(column[i]));
    }

    // Exact answer is 270.0
    assertThat(testObject.toString()).isEqualTo("USD 270.00000");
  }

  /**
   * Verifies ability to handle simple integer multiplications over several iterations
   */
  @Test
  public void testSimpleIntegerMultiply() {

    String[] column = new String[] { "1", "2", "3", "4" };

    BigMoney testObject = MoneyUtils.parse("USD 10.00");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.multipliedBy(new BigDecimal(column[i]));
    }

    // Exact answer is 240
    assertThat(testObject.toString()).isEqualTo("USD 240.00");
  }

  /**
   * Verifies ability to handle non-binary fractions over several iterations
   */
  @Test
  public void testComplexMultiply() {

    String[] column = new String[] { "3.33", "3.33", "3.34", "3.43", "7.99", "6.53" };

    BigMoney testObject = MoneyUtils.parse("USD 13.33");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.multipliedBy(new BigDecimal(column[i]));
    }

    // Exact answer (as calculated) is 88352.56533784463118
    assertThat(testObject.toString()).isEqualTo("USD 88352.56533784463118");
  }

  /**
   * Verifies ability to handle simple fractions over several iterations
   */
  @Test
  public void testSimpleDivide() {

    String[] column = new String[] { "1.0", "2.0", "3.0", "4.5" };

    BigMoney testObject = MoneyUtils.parse("USD 270.00");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.dividedBy(new BigDecimal(column[i]), RoundingMode.HALF_EVEN);
    }

    // Exact answer (as calculated) is 10
    assertThat(testObject.toString()).isEqualTo("USD 10.00");
  }

  /**
   * Verifies ability to handle simple integer fractions over several iterations
   */
  @Test
  public void testSimpleIntegerDivide() {

    String[] column = new String[] { "1", "2", "3", "4" };

    BigMoney testObject = MoneyUtils.parse("USD 270.00");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.dividedBy(new BigDecimal(column[i]), RoundingMode.HALF_EVEN);
    }

    // Exact answer (as calculated) is 11.25
    assertThat(testObject.toString()).isEqualTo("USD 11.25");
  }

  /**
   * Verifies ability to handle simple integer divisions over several iterations followed by a series of integer multiplications to establish preservation of rounding
   */
  @Test
  public void testComplexIntegerDivideAndMultiply() {

    String[] column = new String[] { "1", "2", "3", "4" };

    BigMoney testObject = MoneyUtils.parse("USD 270.00");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.dividedBy(new BigDecimal(column[i]), RoundingMode.HALF_EVEN);
    }
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.multipliedBy(new BigDecimal(column[i]));
    }

    // Exact answer (as calculated) is 1) 11.25 2) 270
    assertThat(testObject.toString()).isEqualTo("USD 270.00");
  }

  /**
   * Verifies ability to handle non-binary fractions over several iterations
   */
  @Test
  public void testComplexDivide() {

    String[] column = new String[] { "3.33", "3.33", "3.34", "3.43", "7.99", "6.53" };

    BigMoney testObject = MoneyUtils.parse("USD 435700.42");
    for (int i = 0; i < column.length; i++) {
      testObject = testObject.dividedBy(new BigDecimal(column[i]), RoundingMode.HALF_EVEN);
    }

    // Exact answer (as calculated) is 65.735347653932
    // Locale representation is 2dp
    assertThat(testObject.toString()).isEqualTo("USD 65.74");
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
    assertThat(testObject.toString()).isEqualTo("BTC 3168.328924522745");

    for (int i = 0; i < column.length; i++) {
      testObject = testObject.multipliedBy(new BigDecimal(column[i]));
    }

    // Exact answer (as calculated) is 20999999.12345678
    assertThat(MoneyUtils.formatBitcoin(testObject)).isEqualTo("BTC 20999999.12345678");

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
    assertThat(testObject.toString()).isEqualTo("BTC 3168.328924522745");

    for (int i = 0; i < column.length; i++) {
      testObject = testObject.multipliedBy(new BigDecimal(column[i]));
    }

    // Exact answer (as calculated) is 20999999.12345678
    assertThat(MoneyUtils.formatBitcoin(testObject)).isEqualTo("BTC 20999999.12345678");

  }

  @Test
  public void testScientificNotation() {

    String amount = "USD 1.0E7";
    MoneyUtils.parse(amount);

  }

  @Test
  public void testParseMoney() throws Exception {

    assertThat(MoneyUtils.parseMoney(Currencies.EUR, null)).isNull();

    BigMoney eur344 = MoneyUtils.parseMoney(Currencies.EUR, new BigDecimal("3.44"));
    assertThat(eur344.getAmount()).isEqualTo(new BigDecimal("3.44"));
    assertThat(eur344.getCurrencyUnit().getCurrencyCode()).isEqualTo("EUR");
  }
}
