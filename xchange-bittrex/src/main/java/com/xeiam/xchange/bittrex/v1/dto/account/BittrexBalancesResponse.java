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
package com.xeiam.xchange.bittrex.v1.dto.account;

import java.util.List;

public class BittrexBalancesResponse {

  private String message;
  private List<BittrexBalance> result;
  private boolean success;

  public String getMessage() {

    return this.message;
  }

  public void setMessage(String message) {

    this.message = message;
  }

  public List<BittrexBalance> getResult() {

    return this.result;
  }

  public void setResult(List<BittrexBalance> result) {

    this.result = result;
  }

  public boolean getSuccess() {

    return this.success;
  }

  public void setSuccess(boolean success) {

    this.success = success;
  }

  @Override
  public String toString() {

    return "BittrexBalancesResponse [message=" + message + ", result=" + result + ", success=" + success + "]";
  }

}