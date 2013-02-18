/*
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.bitfloor.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Matija Mazi
 */
public final class BitfloorDayInfo {

  private final BigDecimal low;
  private final BigDecimal high;
  private final BigDecimal volume;

  public BitfloorDayInfo(@JsonProperty("low") BigDecimal low, @JsonProperty("high") BigDecimal high, @JsonProperty("volume") BigDecimal volume) {

    this.low = low;
    this.high = high;
    this.volume = volume;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  @Override
  public String toString() {

    return String.format("BitfloorDayInfo{low=%s, high=%s, volume=%s}", low, high, volume);
  }
}
