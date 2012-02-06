package com.xeiam.xchange.utils;

/**
 * <p>Abstract class to provide the following to framework:</p>
 * <ul>
 * <li>Provision of useful assertions to trap programmer errors early</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public abstract class Assert {

  /**
   * Asserts that an object is not null
   * @param object The object under test
   * @param message The message for any exception
   */
  public static void notNull(Object object, String message) {
    if (object == null) {
      throw new IllegalArgumentException(message);
    }
  }

}
