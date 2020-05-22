package org.knowm.xchange.utils;

import org.knowm.xchange.exceptions.ExchangeException;

public class ArrayUtils {
  public static <T> T getElement(int index, Object[] array, Class<T> expectedType) {
    return getElement(index, array, expectedType, null, false);
  }

  public static <T> T getElement(int index, Object[] array, Class<T> expectedType, T defaultValue) {
    return getElement(index, array, expectedType, defaultValue, false);
  }

  public static <T> T getElement(
      int index, Object[] array, Class<T> expectedType, boolean errorIfNull) {
    return getElement(index, array, expectedType, null, errorIfNull);
  }

  @SuppressWarnings("unchecked")
  private static <T> T getElement(
      int index, Object[] array, Class<T> expectedType, T defaultValue, boolean errorIfNull) {
    T result;

    if (array == null) {
      array = new Object[0];
    }
    if (index < 0 || index > array.length - 1) {
      result = null;
    } else {
      Object arrayElement = array[index];
      if (arrayElement != null && !expectedType.isAssignableFrom(arrayElement.getClass())) {
        throw new ExchangeException(
            String.format(
                "Array[%d] element expected type is %s but actual is %s",
                index, expectedType.getName(), arrayElement.getClass().getName()));
      }
      result = (T) arrayElement;
    }

    if (result == null) {
      if (defaultValue != null) {
        result = defaultValue;
      }
      if (errorIfNull && result == null) {
        throw new ExchangeException("Array[" + index + "] element is NULL");
      }
    }

    return result;
  }
}
