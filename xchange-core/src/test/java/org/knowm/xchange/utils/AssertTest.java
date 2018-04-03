package org.knowm.xchange.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

import java.util.Arrays;
import org.junit.Test;

/** Test class for testing various Assert methods */
public class AssertTest {

  @Test
  public void testNotNull() {

    Assert.notNull("", "Not null");

    try {
      Assert.notNull(null, "null");
      fail("Expected exception");
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("null");
    }
  }

  @Test
  public void testHasLength() {

    Assert.hasLength("Test", 4, "Wrong length");

    try {
      Assert.hasLength(null, 4, "null");
      fail("Expected exception");
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("null");
    }

    try {
      Assert.hasLength("", 4, "short");
      fail("Expected exception");
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("short");
    }
  }

  @Test
  public void testHasSize() {

    Assert.hasSize(Arrays.asList("1", "2", "3"), 3, "Wrong length");

    try {
      Assert.hasSize(null, 4, "null");
      fail("Expected exception");
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("null");
    }

    try {
      Assert.hasSize(Arrays.asList("1", "2", "3"), 4, "short");
      fail("Expected exception");
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("short");
    }
  }
}
