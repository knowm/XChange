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

import java.util.Collection;

/**
 * <p>
 * Abstract class to provide the following to framework:
 * </p>
 * <ul>
 * <li>Provision of useful assertions to trap programmer errors early</li>
 * </ul>
 */
public abstract class Assert {

  /**
   * <p>
   * Asserts that a condition is true
   * </p>
   * 
   * @param boolean The condition under test
   * @param message The message for any exception
   */
  public static void isTrue(boolean condition, String message) {
    if (!condition) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * <p>
   * Asserts that an object is not null
   * </p>
   * 
   * @param object The object under test
   * @param message The message for any exception
   */
  public static void notNull(Object object, String message) {
    if (object == null) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Asserts that a String is not null and of a certain length
   * 
   * @param input The input under test
   * @param message The message for any exception
   */
  public static void hasLength(String input, int length, String message) {
    notNull(input, message);
    if (input.length() != length) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Asserts that a Collection is not null and of a certain size
   * 
   * @param input The input under test
   * @param message The message for any exception
   */
  public static void hasSize(Collection input, int length, String message) {
    notNull(input, message);
    if (input.size() != length) {
      throw new IllegalArgumentException(message);
    }
  }

}
