/*
 * The MIT License
 *
 * Copyright 2019 Knowm Inc..
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.knowm.xchange.client.resilience;

import com.google.common.annotations.Beta;

/**
 * This is an early sample of some functionality we to add to the resilience4j library.
 *
 * <p>In time this will be removed from the Xchange library and we will refactor our code to use the
 * annotations from the resilience4j library.
 *
 * @author walec51
 */
@Beta
public @interface RateLimiter {

  static String FIXED_WEIGHT = "__FIXED_WEIGHT__";

  String name();

  int permits() default 1;

  String permitsMethodName() default FIXED_WEIGHT;
}
