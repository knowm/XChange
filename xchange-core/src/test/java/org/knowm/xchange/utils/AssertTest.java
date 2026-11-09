package org.knowm.xchange.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/** Test class for testing various Assert methods */
class AssertTest {

  @Test
  void notNull() {

    Assert.notNull("", "Not null");

    try {
      Assert.notNull(null, "null");
      fail("Expected exception");
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("null");
    }
  }

  @Test
  void hasLength() {

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
  void hasSize() {

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
