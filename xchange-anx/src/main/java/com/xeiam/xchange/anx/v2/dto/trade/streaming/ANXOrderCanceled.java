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
package com.xeiam.xchange.anx.v2.dto.trade.streaming;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author timmolter
 */
public class ANXOrderCanceled {

  private final String oid;
  private final String qid;

  /**
   * Constructor
   * 
   * @param oid
   * @param qid
   */
  public ANXOrderCanceled(@JsonProperty("oid") String oid, @JsonProperty("qid") String qid) {

    this.oid = oid;
    this.qid = qid;
  }

  public String getOid() {

    return oid;
  }

  public String getQid() {

    return qid;
  }

  @Override
  public String toString() {

    return "ANXOrderCanceled{" + "oid='" + oid + '\'' + ", qid='" + qid + '\'' + '}';
  }
}
