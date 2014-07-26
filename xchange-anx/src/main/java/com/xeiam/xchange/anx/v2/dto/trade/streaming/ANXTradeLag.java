/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.anx.v2.dto.trade.streaming;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author timmolter
 */
public class ANXTradeLag {

  private final String qid;
  private final String stamp;
  private final Integer age;

  /**
   * Constructor
   * 
   * @param qid
   * @param stamp
   * @param age
   */
  public ANXTradeLag(@JsonProperty("qid") String qid, @JsonProperty("stamp") String stamp, @JsonProperty("age") Integer age) {

    this.qid = qid;
    this.stamp = stamp;
    this.age = age;
  }

  public String getQid() {

    return qid;
  }

  public String getStamp() {

    return stamp;
  }

  public Integer getAge() {

    return age;
  }

  @Override
  public String toString() {

    return "ANXTradeLag{" + "qid='" + qid + '\'' + ", stamp='" + stamp + '\'' + ", age=" + age + '}';
  }

  public String toStringShort() {

    return "ANXTradeLag= " + (double) age / 1000000 + " s";
  }
}
