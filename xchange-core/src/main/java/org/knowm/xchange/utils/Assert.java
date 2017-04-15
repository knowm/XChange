package org.knowm.xchange.utils;

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
   * @param condition The condition under test
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
    if (input.trim().length() != length) {
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
