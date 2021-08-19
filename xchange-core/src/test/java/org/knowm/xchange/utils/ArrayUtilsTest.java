package org.knowm.xchange.utils;

import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.exceptions.ExchangeException;

public class ArrayUtilsTest {
  private final Object[] array = {1, "Test", '4'};

  @Test
  public void test() {
    Assert.assertNull(ArrayUtils.getElement(-1, null, Integer.class));
    Assert.assertNull(ArrayUtils.getElement(3, array, Integer.class));
    Assert.assertEquals((Integer) 1, ArrayUtils.getElement(0, array, Integer.class));
    Assert.assertEquals("Test", ArrayUtils.getElement(1, array, String.class));
    Assert.assertEquals("default", ArrayUtils.getElement(3, array, String.class, "default"));
    Assert.assertEquals((Character) '4', ArrayUtils.getElement(2, array, Character.class));
  }

  @Test(expected = ExchangeException.class)
  public void testFailedType() {
    ArrayUtils.getElement(0, array, String.class);
  }

  @Test(expected = ExchangeException.class)
  public void testFailedMandatory() {
    ArrayUtils.getElement(3, array, String.class, true);
  }
}
