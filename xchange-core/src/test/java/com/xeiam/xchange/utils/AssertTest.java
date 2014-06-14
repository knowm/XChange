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
import static org.fest.assertions.api.Fail.fail;

import java.util.Arrays;

import org.junit.Test;

/**
 * Test class for testing various Assert methods
 */
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
