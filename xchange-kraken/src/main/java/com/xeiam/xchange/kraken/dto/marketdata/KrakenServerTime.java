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
package com.xeiam.xchange.kraken.dto.marketdata;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.utils.jackson.Rfc1123DateDeserializer;

public class KrakenServerTime {

  private final long unixTime;
  private final Date rfc1123Time;

  /**
   * Constructor
   * 
   * @param unixTime
   * @param rfc1123Time
   */
  public KrakenServerTime(@JsonProperty("unixtime") long unixTime, @JsonProperty("rfc1123") @JsonDeserialize(using = Rfc1123DateDeserializer.class) Date rfc1123Time) {

    this.unixTime = unixTime;
    this.rfc1123Time = rfc1123Time;
  }

  public long getUnixTime() {

    return unixTime;
  }

  public Date getRfc1123Time() {

    return rfc1123Time;
  }

  @Override
  public String toString() {

    return "KrakenServerTime [unixTime=" + unixTime + ", rfc1123Time=" + rfc1123Time + "]";
  }

}
